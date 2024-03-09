package org.machinemc.primallib.v1_20_4.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.profile.PlayerInfoService;
import org.machinemc.primallib.v1_20_4.impl.profile.PlayerInfoServiceImpl;

public class PlayerInfoUpdatePacketListener implements PacketListener<ClientboundPlayerInfoUpdatePacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundPlayerInfoUpdatePacket> event) {
        PlayerInfoServiceImpl playerInfoService = (PlayerInfoServiceImpl) PlayerInfoService.get();
        playerInfoService.setPlayerInfos(event.getPlayer(), event.getPacket());
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundPlayerInfoUpdatePacket> packetClass() {
        return ClientboundPlayerInfoUpdatePacket.class;
    }

}
