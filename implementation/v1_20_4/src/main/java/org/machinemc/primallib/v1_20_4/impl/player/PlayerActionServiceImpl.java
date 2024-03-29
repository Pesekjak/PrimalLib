package org.machinemc.primallib.v1_20_4.impl.player;

import io.netty.buffer.Unpooled;
import io.papermc.paper.math.BlockPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.bukkit.block.sign.Side;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftAbstractHorse;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.ItemStack;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.player.PlayerActionService;
import org.machinemc.primallib.v1_20_4.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_20_4.util.Converters;
import org.machinemc.primallib.world.*;

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
    public void setCamera(Player player, EntityLike entityLike) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeVarInt(entityLike.entityID());
        var packet = new ClientboundSetCameraPacket(buf);
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    @Override
    public void openHorseInventory(Player player, AbstractHorseInventory inventory) {
        CraftAbstractHorse horse = ((CraftAbstractHorse) inventory.getHolder(false));
        if (horse == null) return;

        var handle = horse.getHandle();
        var container = handle.inventory;

        ((CraftPlayer) player).getHandle().openHorseInventory(handle, container);
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
            case BellAction bellAction -> type = 1;
            case ChestAction chestAction -> {
                type = 1;
                data = chestAction.opened() ? 1 : 0;
            }
            case EndGatewayAction endGatewayAction -> type = 1;
            case PistonAction pistonAction -> {
                type = switch (pistonAction.type()) {
                    case EXTEND -> 0;
                    case RETRACT -> 1;
                    case CANCEL_EXTENSION -> 2;
                };
                data = switch (pistonAction.face()) {
                    case DOWN -> 0;
                    case UP -> 1;
                    case SOUTH -> 2;
                    case WEST -> 3;
                    case NORTH -> 4;
                    case EAST -> 5;
                    default -> throw new IllegalArgumentException("Unexpected face type " + pistonAction.face());
                };
            }
            case ShulkerBoxAction shulkerBoxAction -> {
                type = 1;
                data = shulkerBoxAction.opened() ? 1 : 0;
            }
        }

        var packet = new ClientboundBlockEventPacket(blockPos, minecraftBlock, type, data);
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

}
