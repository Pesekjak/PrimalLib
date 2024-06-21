package org.machinemc.primallib.v1_21.listeners.packet.serverbound;

import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import org.machinemc.primallib.event.advancements.PlayerAdvancementsTabSelectEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.v1_21.util.Converters;

public class SeenAdvancementsPacketListener implements PacketListener<ServerboundSeenAdvancementsPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundSeenAdvancementsPacket> event) {
        var packet = event.getPacket();
        new PlayerAdvancementsTabSelectEvent(
                event.getPlayer(),
                packet.getAction() == ServerboundSeenAdvancementsPacket.Action.OPENED_TAB
                        ? PlayerAdvancementsTabSelectEvent.Action.OPENED_TAB
                        : PlayerAdvancementsTabSelectEvent.Action.CLOSED_SCREEN,
                packet.getAction() == ServerboundSeenAdvancementsPacket.Action.OPENED_TAB
                        ? Converters.fromMinecraft(packet.getTab())
                        : null
        ).callEvent();
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundSeenAdvancementsPacket> packetClass() {
        return ServerboundSeenAdvancementsPacket.class;
    }

}
