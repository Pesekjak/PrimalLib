package org.machinemc.primallib.v1_21.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.scoreboard.TeamService;
import org.machinemc.primallib.v1_21.impl.scoreboard.TeamServiceImpl;

public class SetPlayerTeamPacketListener implements PacketListener<ClientboundSetPlayerTeamPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundSetPlayerTeamPacket> event) throws Exception {
        TeamServiceImpl teamService = (TeamServiceImpl) TeamService.get();
        teamService.setTeam(event.getPlayer(), event.getPacket());
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundSetPlayerTeamPacket> packetClass() {
        return ClientboundSetPlayerTeamPacket.class;
    }

}
