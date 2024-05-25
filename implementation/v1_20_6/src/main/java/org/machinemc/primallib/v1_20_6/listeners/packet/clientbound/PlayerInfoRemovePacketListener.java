package org.machinemc.primallib.v1_20_6.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.profile.PlayerInfoService;
import org.machinemc.primallib.v1_20_6.impl.profile.PlayerInfoServiceImpl;

public class PlayerInfoRemovePacketListener implements PacketListener<ClientboundPlayerInfoRemovePacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundPlayerInfoRemovePacket> event) {
        PlayerInfoServiceImpl playerInfoService = (PlayerInfoServiceImpl) PlayerInfoService.get();
        playerInfoService.removePlayerInfos(event.getPlayer(), event.getPacket());
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundPlayerInfoRemovePacket> packetClass() {
        return ClientboundPlayerInfoRemovePacket.class;
    }

}
