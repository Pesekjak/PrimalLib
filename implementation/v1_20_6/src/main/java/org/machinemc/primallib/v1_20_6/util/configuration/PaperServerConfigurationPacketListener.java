package org.machinemc.primallib.v1_20_6.util.configuration;

import com.google.common.base.Preconditions;
import net.minecraft.network.Connection;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.configuration.ServerboundFinishConfigurationPacket;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.minecraft.world.level.GameRules;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.event.player.PlayerExitReconfigurationEvent;
import org.machinemc.primallib.player.PlayerActionService;
import org.machinemc.primallib.util.OwnerPlugin;
import org.machinemc.primallib.v1_20_6.impl.player.PlayerActionServiceImpl;

import java.util.List;

/**
 * Special implementation of configuration packet listener that has
 * custom logic on configuration finish.
 * <p>
 * Configuration state currently does not work on Paper servers, so we
 * need to handle everything ourselves. :(
 */
public class PaperServerConfigurationPacketListener extends ServerConfigurationPacketListenerImpl {

    private final PlayerActionServiceImpl playerActionService = (PlayerActionServiceImpl) PlayerActionService.get();

    public PaperServerConfigurationPacketListener(MinecraftServer server,
                                                  Connection connection,
                                                  CommonListenerCookie cookie,
                                                  ServerPlayer player) {
        super(server, connection, cookie, player);
    }

    @Override
    public void handleConfigurationFinished(@NotNull ServerboundFinishConfigurationPacket packet) {
        PlayerReconfigurationData data = playerActionService.getReconfigurationData(player.getBukkitEntity());
        data.setState(PlayerReconfigurationData.State.NONE);

        Connection connection = player.connection.connection;

        Preconditions.checkNotNull(data.getOldListener(), "Old game listener is not initialized");
        var decorator = RegistryFriendlyByteBuf.decorator(this.server.registryAccess());
        connection.setupInboundProtocol(GameProtocols.SERVERBOUND.bind(decorator), data.getOldListener());
        connection.setupOutboundProtocol(GameProtocols.CLIENTBOUND.bind(decorator));
        sendToWorld();
    }

    private void sendToWorld() {
        CraftPlayer craftPlayer = player.getBukkitEntity();
        Connection connection = player.connection.connection;
        ServerLevel world = ((CraftWorld) craftPlayer.getWorld()).getHandle();

        GameRules gamerules = world.getGameRules();
        boolean respawn = gamerules.getBoolean(GameRules.RULE_DO_IMMEDIATE_RESPAWN);
        boolean reducedDebugInfo = gamerules.getBoolean(GameRules.RULE_REDUCEDDEBUGINFO);
        boolean limitedCrafting = gamerules.getBoolean(GameRules.RULE_LIMITED_CRAFTING);

        //
        // Following sequence tries to somehow replicate PlayerList#placeNewPlayer
        //

        connection.send(new ClientboundLoginPacket(
                player.getId(),
                player.getBukkitEntity().getWorld().isHardcore(),
                this.server.levelKeys(),
                MinecraftServer.getServer().getPlayerList().getMaxPlayers(),
                player.serverLevel().getWorld().getSendViewDistance(),
                player.serverLevel().getWorld().getSimulationDistance(),
                reducedDebugInfo,
                !respawn,
                limitedCrafting,
                player.createCommonSpawnInfo(player.serverLevel()),
                server.enforceSecureProfile())
        );

        connection.send(new ClientboundChangeDifficultyPacket(world.getDifficulty(), world.levelData.isDifficultyLocked()));
        connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
        connection.send(new ClientboundSetCarriedItemPacket(player.getInventory().selected));
        connection.send(new ClientboundUpdateRecipesPacket(server.getRecipeManager().getRecipes()));

        server.getPlayerList().sendPlayerPermissionLevel(player);
        player.getStats().markAllDirty();
        player.getRecipeBook().sendInitialRecipeBook(player);
        server.getPlayerList().updateEntireScoreboard(world.getScoreboard(), player);

        ServerStatus status = this.server.getStatus();
        if (status != null) player.sendServerStatus(status);

        server.getCustomBossEvents().onPlayerConnect(player);

        var vehicle = player.getVehicle();
        if (vehicle != null) {
            player.stopRiding();
            player.startRiding(vehicle);
        }

        player.inventoryMenu.broadcastFullState();

        player.onUpdateAbilities();

        List<ServerPlayer> toDisplay = server.getPlayerList().getPlayers().stream()
                .filter(other -> player.getBukkitEntity().canSee(other.getBukkitEntity()))
                .toList();
        connection.send(ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(toDisplay, player));

        //
        // Calling event and closing inventory
        //

        new PlayerExitReconfigurationEvent(player.getBukkitEntity()).callEvent();
        connection.send(new ClientboundContainerClosePacket(0));

        //
        // Refresh and respawn take care of rest of the logic I can not be bothered with
        //

        Bukkit.getScheduler().runTask(OwnerPlugin.get(), () -> {
            playerActionService.refreshPlayer(craftPlayer);
            server.getPlayerList().respawn(player, world, true, craftPlayer.getLocation(), false, PlayerRespawnEvent.RespawnReason.PLUGIN);
        });
    }

}
