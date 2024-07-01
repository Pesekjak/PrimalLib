package org.machinemc.primallib.v1_21.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.scoreboard.ObjectiveService;
import org.machinemc.primallib.v1_21.impl.scoreboard.ObjectiveServiceImpl;

public class SetObjectivePacketListener implements PacketListener<ClientboundSetObjectivePacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundSetObjectivePacket> event) throws Exception {
        ObjectiveServiceImpl objectiveService = (ObjectiveServiceImpl) ObjectiveService.get();
        objectiveService.setObjective(event.getPlayer(), event.getPacket());
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundSetObjectivePacket> packetClass() {
        return ClientboundSetObjectivePacket.class;
    }

}
