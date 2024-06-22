package org.machinemc.primallib.v1_21.listeners.packet.serverbound;

import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import org.machinemc.primallib.event.player.PlayerOpenStatisticsEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;

public class ClientCommandPacketListener implements PacketListener<ServerboundClientCommandPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundClientCommandPacket> event) throws Exception {
        if (event.getPacket().getAction() != ServerboundClientCommandPacket.Action.REQUEST_STATS) return;
        PlayerOpenStatisticsEvent openStatisticsEvent = new PlayerOpenStatisticsEvent(event.getPlayer());
        openStatisticsEvent.callEvent();
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundClientCommandPacket> packetClass() {
        return ServerboundClientCommandPacket.class;
    }

}
