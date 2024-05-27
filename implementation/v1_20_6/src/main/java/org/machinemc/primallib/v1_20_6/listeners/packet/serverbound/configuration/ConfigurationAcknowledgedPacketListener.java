package org.machinemc.primallib.v1_20_6.listeners.packet.serverbound.configuration;

import net.minecraft.network.protocol.configuration.ConfigurationProtocols;
import net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.player.ConfigurationStateService;
import org.machinemc.primallib.v1_20_6.impl.player.ConfigurationStateServiceImpl;
import org.machinemc.primallib.v1_20_6.util.configuration.PaperServerConfigurationPacketListener;
import org.machinemc.primallib.v1_20_6.util.configuration.PlayerReconfigurationData;

public class ConfigurationAcknowledgedPacketListener implements PacketListener<ServerboundConfigurationAcknowledgedPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundConfigurationAcknowledgedPacket> event) throws Exception {
        ConfigurationStateServiceImpl configurationStateService = (ConfigurationStateServiceImpl) ConfigurationStateService.get();
        PlayerReconfigurationData data = configurationStateService.getReconfigurationData(event.getPlayer());

        if (data.getState() != PlayerReconfigurationData.State.AWAITING_CONFIGURATION_ACK) return;

        event.setCancelled(true);
        data.setState(PlayerReconfigurationData.State.IN_CONFIGURATION);

        ServerPlayer player = ((CraftPlayer) event.getPlayer()).getHandle();

        CommonListenerCookie cookie = new CommonListenerCookie(
                player.gameProfile,
                player.connection.latency(),
                player.clientInformation(),
                player.connection.isTransferred()
        );
        PaperServerConfigurationPacketListener newListener = new PaperServerConfigurationPacketListener(
                MinecraftServer.getServer(),
                player.connection.connection,
                cookie,
                player
        );

        data.setOldListener(player.connection);

        player.connection.connection.setupInboundProtocol(ConfigurationProtocols.SERVERBOUND, newListener);
        player.connection.connection.setupOutboundProtocol(ConfigurationProtocols.CLIENTBOUND);
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
