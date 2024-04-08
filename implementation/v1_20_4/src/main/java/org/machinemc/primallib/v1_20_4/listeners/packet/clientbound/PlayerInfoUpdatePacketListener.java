package org.machinemc.primallib.v1_20_4.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.event.profile.PlayerPlayerInfoUpdateEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.profile.PlayerInfo;
import org.machinemc.primallib.profile.PlayerInfoService;
import org.machinemc.primallib.v1_20_4.impl.profile.PlayerInfoServiceImpl;
import org.machinemc.primallib.v1_20_4.util.Converters;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoUpdatePacketListener implements PacketListener<ClientboundPlayerInfoUpdatePacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundPlayerInfoUpdatePacket> event) {
        PlayerInfoServiceImpl playerInfoService = (PlayerInfoServiceImpl) PlayerInfoService.get();

        Player player = event.getPlayer();
        var packet = event.getPacket();

        List<PlayerInfo.Action> actions = packet.actions().stream().map(Converters::fromMinecraft).toList();
        List<PlayerInfo> playerInfos = new ArrayList<>();

        for (ClientboundPlayerInfoUpdatePacket.Entry entry : packet.entries()) {
            @Nullable PlayerInfo playerInfo = playerInfoService.getPlayerInfo(player, entry.profileId()).orElse(null);
            PlayerInfo converted;
            try {
                converted = Converters.fromMinecraft(entry, playerInfo, packet.actions());
            } catch (Exception exception) {
                continue; // tried to update non-existing profile
            }

            var playerInfoUpdateEvent = new PlayerPlayerInfoUpdateEvent(player, converted, actions);
            if (!playerInfoUpdateEvent.callEvent()) continue;
            playerInfos.add(playerInfoUpdateEvent.getPlayerInfo());
        }

        packet = new ClientboundPlayerInfoUpdatePacket(packet.actions(), playerInfos.stream().map(Converters::toMinecraft).toList());

        event.setPacket(packet);

        playerInfoService.setPlayerInfos(event.getPlayer(), packet);
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
