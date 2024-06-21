package org.machinemc.primallib.v1_21.listeners.packet.serverbound;

import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import org.machinemc.primallib.event.vehicle.PlayerPaddleBoatEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;

public class PaddleBoatPacketListener implements PacketListener<ServerboundPaddleBoatPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundPaddleBoatPacket> event) {
        var packet = event.getPacket();
        PlayerPaddleBoatEvent paddleBoatEvent = new PlayerPaddleBoatEvent(event.getPlayer(), packet.getLeft(), packet.getRight());
        paddleBoatEvent.callEvent();

        if (paddleBoatEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        packet = new ServerboundPaddleBoatPacket(paddleBoatEvent.isLeftTurning(), paddleBoatEvent.isRightTurning());
        event.setPacket(packet);
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundPaddleBoatPacket> packetClass() {
        return ServerboundPaddleBoatPacket.class;
    }

}
