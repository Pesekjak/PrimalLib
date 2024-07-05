package org.machinemc.primallib.v1_21.impl.player;

import io.netty.buffer.Unpooled;
import io.papermc.paper.math.BlockPosition;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundCustomReportDetailsPacket;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.sign.Side;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.player.PlayerActionService;
import org.machinemc.primallib.player.SkinPart;
import org.machinemc.primallib.util.OwnerPlugin;
import org.machinemc.primallib.v1_21.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_21.util.Converters;
import org.machinemc.primallib.world.*;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class PlayerActionServiceImpl extends PlayerActionService {

    @Override
    public void openSign(Player player, BlockPosition position, Side side) {
        var packet = new ClientboundOpenSignEditorPacket(
                Converters.toMinecraft(position.toBlock()),
                side == Side.FRONT
        );
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    @Override
    public void setActiveSkinParts(Player player, SkinPart... skinParts) {
        ServerPlayer handle = ((CraftPlayer) player).getHandle();
        handle.getEntityData().set(ServerPlayer.DATA_PLAYER_MODE_CUSTOMISATION, (byte) SkinPart.createMask(skinParts), true);
    }

    @Override
    public SkinPart[] getActiveSkinParts(Player player) {
        ServerPlayer handle = ((CraftPlayer) player).getHandle();
        return SkinPart.fromMask(handle.getEntityData().get(ServerPlayer.DATA_PLAYER_MODE_CUSTOMISATION));
    }

    @Override
    public void setCamera(Player player, EntityLike target) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeVarInt(target.entityID());
        var packet = ClientboundSetCameraPacket.STREAM_CODEC.decode(buf);
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    @Override
    public void setFOVModifier(Player player, float fov) {
        var handle = ((CraftPlayer) player).getHandle();
        handle.getAbilities().walkingSpeed = fov;
        handle.onUpdateAbilities();
    }

    @Override
    public void playBlockAction(Player player, BlockPosition position, Material block, BlockAction blockAction) {
        BlockPos blockPos = Converters.toMinecraft(position);
        Block minecraftBlock = Block.byItem(Converters.toMinecraft(new ItemStack(block)).getItem());
        int type;
        int data = 0;

        switch (blockAction) {
            case BellAction bellAction -> {
                type = 1;
                data = getFaceID(bellAction.face());
            }
            case ChestAction chestAction -> {
                type = 1;
                data = chestAction.opened() ? 1 : 0;
            }
            case DecoratedPotAction decoratedPotAction -> {
                type = 1;
                data = decoratedPotAction.style().ordinal();
            }
            case EndGatewayAction endGatewayAction -> type = 1;
            case PistonAction pistonAction -> {
                type = switch (pistonAction.type()) {
                    case EXTEND -> 0;
                    case RETRACT -> 1;
                    case CANCEL_EXTENSION -> 2;
                };
                data = getFaceID(pistonAction.face());
            }
            case ShulkerBoxAction shulkerBoxAction -> {
                type = 1;
                data = shulkerBoxAction.opened() ? 1 : 0;
            }
        }

        var packet = new ClientboundBlockEventPacket(blockPos, minecraftBlock, type, data);
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    private static int getFaceID(BlockFace face) {
        return switch (face) {
            case DOWN -> 0;
            case UP -> 1;
            case NORTH -> 2;
            case SOUTH -> 3;
            case WEST -> 4;
            case EAST -> 5;
            default -> throw new IllegalArgumentException("Unexpected face type " + face);
        };
    }

    @Override
    public void refreshPlayer(Player player) {
        // This method mirrors CraftPlayer#refreshPlayer method
        ServerPlayer handle = ((CraftPlayer) player).getHandle();
        Location loc = player.getLocation();

        showLoadingScreen(player);
        handle.onUpdateAbilities();
        handle.connection.internalTeleport(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), Collections.emptySet());

        PlayerList playerList = handle.server.getPlayerList();
        playerList.sendPlayerPermissionLevel(handle, false);
        playerList.sendLevelInfo(handle, handle.serverLevel());
        playerList.sendAllPlayerInfo(handle);

        var experiencePacket = new ClientboundSetExperiencePacket(handle.experienceProgress, handle.totalExperience, handle.experienceLevel);
        PacketChannelHandlerImpl.sendPacket(player, experiencePacket, false);

        for (MobEffectInstance effect : handle.getActiveEffects()) {
            PacketChannelHandlerImpl.sendPacket(player, new ClientboundUpdateMobEffectPacket(handle.getId(), effect, false), false);
        }

        // Refresh player for others
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .forEach(p -> {
                    p.hideEntity(OwnerPlugin.get(), player);
                    p.showEntity(OwnerPlugin.get(), player);
                });
    }

    @Override
    public void showLoadingScreen(Player player) {
        ServerPlayer handle = ((CraftPlayer) player).getHandle();
        CommonPlayerSpawnInfo spawnInfo = handle.createCommonSpawnInfo(handle.serverLevel());
        ClientboundRespawnPacket packet = new ClientboundRespawnPacket(spawnInfo, ClientboundRespawnPacket.KEEP_ALL_DATA);
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    @Override
    public void setCrashProperties(Player player, Map<String, String> properties) {
        PacketChannelHandlerImpl.sendPacket(player, new ClientboundCustomReportDetailsPacket(properties), false);
    }

    private final Map<Player, Component> nextInventoryTitle = new ConcurrentHashMap<>();

    @Override
    public Inventory openNamedInventory(Player player, Component title, Consumer<Player> opener) {
        nextInventoryTitle.put(player, title);
        opener.accept(player);
        return player.getOpenInventory().getTopInventory();
    }

    public @Nullable Component getNextInventoryTitle(Player player) {
        Component title = nextInventoryTitle.get(player);
        if (title != null) nextInventoryTitle.remove(player);
        return title;
    }

}
