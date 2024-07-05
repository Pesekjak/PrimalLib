package org.machinemc.primallib.v1_21.listeners.packet.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.player.PlayerActionService;
import org.machinemc.primallib.v1_21.impl.player.PlayerActionServiceImpl;
import org.machinemc.primallib.v1_21.util.Converters;

public class OpenScreenPacketListener implements PacketListener<ClientboundOpenScreenPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundOpenScreenPacket> event) throws Exception {
        PlayerActionServiceImpl playerInfoService = (PlayerActionServiceImpl) PlayerActionService.get();
        Component title = playerInfoService.getNextInventoryTitle(event.getPlayer());

        if (title == null) return;

        var packet = event.getPacket();
        event.setPacket(new ClientboundOpenScreenPacket(packet.getContainerId(), packet.getType(), Converters.toMinecraft(title)));
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundOpenScreenPacket> packetClass() {
        return ClientboundOpenScreenPacket.class;
    }

}
