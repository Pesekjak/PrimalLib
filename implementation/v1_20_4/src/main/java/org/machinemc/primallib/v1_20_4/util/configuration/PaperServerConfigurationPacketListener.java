package org.machinemc.primallib.v1_20_4.util.configuration;

import com.google.common.base.Preconditions;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.configuration.ServerboundFinishConfigurationPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.player.PlayerActionService;
import org.machinemc.primallib.v1_20_4.impl.player.PlayerActionServiceImpl;

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

        PacketListener old = Preconditions.checkNotNull(data.getOldListener(), "Incorrectly initialized Paper Server Configuration");
        player.connection.connection.setListener(old);

        player.connection.connection.send(new ClientboundLoginPacket(
                player.getId(),
                true,
                this.server.levelKeys(),
                MinecraftServer.getServer().getPlayerList().getMaxPlayers(),
                player.serverLevel().getWorld().getSendViewDistance(),
                player.serverLevel().getWorld().getSimulationDistance(),
                true,
                true,
                true,
                player.createCommonSpawnInfo(player.serverLevel()))
        );
        player.connection.connection.send(new ClientboundContainerClosePacket(0));

    }

}
