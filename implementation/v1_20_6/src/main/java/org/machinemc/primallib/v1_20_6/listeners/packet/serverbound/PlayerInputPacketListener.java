package org.machinemc.primallib.v1_20_6.listeners.packet.serverbound;

import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import org.machinemc.primallib.event.vehicle.PlayerInputEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;

public class PlayerInputPacketListener implements PacketListener<ServerboundPlayerInputPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundPlayerInputPacket> event) {
        var packet = event.getPacket();
        PlayerInputEvent inputEvent = new PlayerInputEvent(event.getPlayer(), packet.getXxa(), packet.getZza(), packet.isJumping(), packet.isShiftKeyDown());
        inputEvent.callEvent();

        if (inputEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        packet = new ServerboundPlayerInputPacket(inputEvent.getSidewaysDelta(), inputEvent.getForwardDelta(), inputEvent.isJumping(), inputEvent.isDismounting());
        event.setPacket(packet);
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundPlayerInputPacket> packetClass() {
        return ServerboundPlayerInputPacket.class;
    }

}
