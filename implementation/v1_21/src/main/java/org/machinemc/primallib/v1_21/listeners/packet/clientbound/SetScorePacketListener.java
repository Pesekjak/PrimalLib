package org.machinemc.primallib.v1_21.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.scoreboard.ObjectiveService;
import org.machinemc.primallib.v1_21.impl.scoreboard.ObjectiveServiceImpl;

public class SetScorePacketListener implements PacketListener<ClientboundSetScorePacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundSetScorePacket> event) throws Exception {
        ObjectiveServiceImpl objectiveService = (ObjectiveServiceImpl) ObjectiveService.get();
        objectiveService.setScore(event.getPlayer(), event.getPacket());
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundSetScorePacket> packetClass() {
        return ClientboundSetScorePacket.class;
    }

}
