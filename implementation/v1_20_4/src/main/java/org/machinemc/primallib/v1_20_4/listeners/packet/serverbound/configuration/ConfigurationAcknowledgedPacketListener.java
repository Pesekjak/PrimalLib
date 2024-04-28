package org.machinemc.primallib.v1_20_4.listeners.packet.serverbound.configuration;

import net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.player.PlayerActionService;
import org.machinemc.primallib.v1_20_4.impl.player.PlayerActionServiceImpl;
import org.machinemc.primallib.v1_20_4.util.configuration.PaperServerConfigurationPacketListener;
import org.machinemc.primallib.v1_20_4.util.configuration.PlayerReconfigurationData;

public class ConfigurationAcknowledgedPacketListener implements PacketListener<ServerboundConfigurationAcknowledgedPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundConfigurationAcknowledgedPacket> event) throws Exception {
        PlayerActionServiceImpl playerActionService = (PlayerActionServiceImpl) PlayerActionService.get();
        PlayerReconfigurationData data = playerActionService.getReconfigurationData(event.getPlayer());

        if (data.getState() != PlayerReconfigurationData.State.AWAITING_CONFIGURATION_ACK) return;

        event.setCancelled(true);
        data.setState(PlayerReconfigurationData.State.IN_CONFIGURATION);

        ServerPlayer player = ((CraftPlayer) event.getPlayer()).getHandle();
        CommonListenerCookie cookie = new CommonListenerCookie(
                player.gameProfile,
                player.connection.latency(),
                player.clientInformation()
        );
        PaperServerConfigurationPacketListener newListener = new PaperServerConfigurationPacketListener(
                MinecraftServer.getServer(),
                player.connection.connection,
                cookie,
                player
        );

        data.setOldListener(player.connection.connection.getPacketListener());
        player.connection.connection.setListener(newListener);
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundConfigurationAcknowledgedPacket> packetClass() {
        return ServerboundConfigurationAcknowledgedPacket.class;
    }

}
