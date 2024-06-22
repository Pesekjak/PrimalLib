package org.machinemc.primallib.v1_21.listeners.packet.serverbound;

import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import org.machinemc.primallib.event.player.PlayerAnvilRenameEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;

public class RenameItemPacketListener implements PacketListener<ServerboundRenameItemPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundRenameItemPacket> event) throws Exception {
        PlayerAnvilRenameEvent anvilRenameEvent = new PlayerAnvilRenameEvent(event.getPlayer(), event.getPacket().getName());
        anvilRenameEvent.callEvent();
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundRenameItemPacket> packetClass() {
        return ServerboundRenameItemPacket.class;
    }

}
