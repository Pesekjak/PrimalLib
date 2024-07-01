package org.machinemc.primallib.v1_21.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.scoreboard.ObjectiveService;
import org.machinemc.primallib.v1_21.impl.scoreboard.ObjectiveServiceImpl;

public class ResetScorePacketListener implements PacketListener<ClientboundResetScorePacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundResetScorePacket> event) throws Exception {
        ObjectiveServiceImpl objectiveService = (ObjectiveServiceImpl) ObjectiveService.get();
        objectiveService.resetScore(event.getPlayer(), event.getPacket());
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundResetScorePacket> packetClass() {
        return ClientboundResetScorePacket.class;
    }

}
