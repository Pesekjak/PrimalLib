package org.machinemc.primallib.v1_20_4.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import org.machinemc.primallib.event.server.PlayerServerDataEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.v1_20_4.util.Converters;

import java.util.Optional;

public class ServerDataPacketListener implements PacketListener<ClientboundServerDataPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundServerDataPacket> event) {
        var packet = event.getPacket();
        PlayerServerDataEvent serverDataEvent = new PlayerServerDataEvent(
                event.getPlayer(),
                new PlayerServerDataEvent.ServerData(
                        Converters.fromMinecraft(packet.getMotd()),
                        packet.getIconBytes().orElse(null),
                        packet.enforcesSecureChat()
                )
        );
        serverDataEvent.callEvent();
        PlayerServerDataEvent.ServerData serverData = serverDataEvent.getServerData();
        event.setPacket(new ClientboundServerDataPacket(
                Converters.toMinecraft(serverData.motd()),
                Optional.ofNullable(serverData.iconData()),
                serverData.enforcesSecureChat()
        ));
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundServerDataPacket> packetClass() {
        return ClientboundServerDataPacket.class;
    }

}
