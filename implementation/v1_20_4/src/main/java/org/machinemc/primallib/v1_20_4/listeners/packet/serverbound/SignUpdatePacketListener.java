package org.machinemc.primallib.v1_20_4.listeners.packet.serverbound;

import io.papermc.paper.math.BlockPosition;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import org.bukkit.block.sign.Side;
import org.machinemc.primallib.event.block.PlayerSignChangeEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.v1_20_4.util.Converters;

import java.util.List;

public class SignUpdatePacketListener implements PacketListener<ServerboundSignUpdatePacket> {

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onPacket(PacketEvent<ServerboundSignUpdatePacket> event) {
        var packet = event.getPacket();
        Side side = packet.isFrontText() ? Side.FRONT : Side.BACK;
        List<String> lines = List.of(packet.getLines());
        BlockPosition blockPosition = Converters.fromMinecraft(packet.getPos());
        PlayerSignChangeEvent signChangeEvent = new PlayerSignChangeEvent(event.getPlayer(), side, lines, blockPosition);
        signChangeEvent.callEvent();

        if (signChangeEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        packet = new ServerboundSignUpdatePacket(
                Converters.toMinecraft(signChangeEvent.getPosition()),
                signChangeEvent.getSide() == Side.FRONT,
                signChangeEvent.getLines().get(0),
                signChangeEvent.getLines().get(1),
                signChangeEvent.getLines().get(2),
                signChangeEvent.getLines().get(3)
        );

        event.setPacket(packet);
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundSignUpdatePacket> packetClass() {
        return ServerboundSignUpdatePacket.class;
    }

}
