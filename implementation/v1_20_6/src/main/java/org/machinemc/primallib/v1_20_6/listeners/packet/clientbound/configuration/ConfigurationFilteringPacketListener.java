package org.machinemc.primallib.v1_20_6.listeners.packet.clientbound.configuration;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.*;
import net.minecraft.network.protocol.configuration.*;
import net.minecraft.network.protocol.cookie.ClientboundCookieRequestPacket;
import net.minecraft.network.protocol.cookie.ServerboundCookieResponsePacket;
import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.player.PlayerActionService;
import org.machinemc.primallib.v1_20_6.impl.player.PlayerActionServiceImpl;
import org.machinemc.primallib.v1_20_6.util.configuration.PlayerReconfigurationData;

import java.util.HashSet;
import java.util.Set;

/**
 * Filters all outgoing play packets while player is kept in reconfiguration by PrimalLib.
 */
public class ConfigurationFilteringPacketListener implements PacketListener<Packet<?>> {

    private static final Set<Class<? extends Packet<?>>> configurationPackets;

    static {
        // copy ConfigurationProtocols for both CLIENT and SERVERBOUND
        configurationPackets = new HashSet<>();
        configurationPackets.add(ServerboundClientInformationPacket.class);
        configurationPackets.add(ServerboundCookieResponsePacket.class);
        configurationPackets.add(ServerboundCustomPayloadPacket.class);
        configurationPackets.add(ServerboundFinishConfigurationPacket.class);
        configurationPackets.add(ServerboundKeepAlivePacket.class);
        configurationPackets.add(ServerboundPongPacket.class);
        configurationPackets.add(ServerboundResourcePackPacket.class);
        configurationPackets.add(ServerboundSelectKnownPacks.class);
        configurationPackets.add(ClientboundCookieRequestPacket.class);
        configurationPackets.add(ClientboundCustomPayloadPacket.class);
        configurationPackets.add(ClientboundDisconnectPacket.class);
        configurationPackets.add(ClientboundFinishConfigurationPacket.class);
        configurationPackets.add(ClientboundKeepAlivePacket.class);
        configurationPackets.add(ClientboundPingPacket.class);
        configurationPackets.add(ClientboundResetChatPacket.class);
        configurationPackets.add(ClientboundRegistryDataPacket.class);
        configurationPackets.add(ClientboundResourcePackPopPacket.class);
        configurationPackets.add(ClientboundResourcePackPushPacket.class);
        configurationPackets.add(ClientboundStoreCookiePacket.class);
        configurationPackets.add(ClientboundTransferPacket.class);
        configurationPackets.add(ClientboundUpdateEnabledFeaturesPacket.class);
        configurationPackets.add(ClientboundUpdateTagsPacket.class);
        configurationPackets.add(ClientboundSelectKnownPacks.class);
    }

    @Override
    public void onPacket(PacketEvent<Packet<?>> event) throws Exception {
        PlayerActionServiceImpl playerActionService = (PlayerActionServiceImpl) PlayerActionService.get();
        PlayerReconfigurationData data = playerActionService.getReconfigurationData(event.getPlayer());

        Packet<?> packet = event.getPacket();
        ServerPlayer player = ((CraftPlayer) event.getPlayer()).getHandle();

        switch (data.getState()) {

            case NONE -> { }

            case IN_CONFIGURATION -> {
                boolean isConfigPacket = configurationPackets.contains(packet.getClass());
                if (isConfigPacket) break;
                event.setCancelled(true);
                eat(data, packet);
            }

            case AWAITING_CONFIGURATION_REQ -> {
                boolean isReq = packet instanceof ClientboundStartConfigurationPacket;

                if (isReq) {
                    data.setState(PlayerReconfigurationData.State.AWAITING_CONFIGURATION_ACK);
                    break;
                }

                event.setCancelled(true);
                eat(data, packet);
            }

            case AWAITING_CONFIGURATION_ACK -> {
                event.setCancelled(true);
                eat(data, packet);
            }

        }

        if (!event.isCancelled()) return;
        if (!(packet instanceof ClientboundKeepAlivePacket keepAlive)) return;

        ServerboundKeepAlivePacket answer = new ServerboundKeepAlivePacket(keepAlive.getId());
        // keeps the connection alive because player can not answer the keep alive packets while in configuration
        answer.handle(player.connection);
    }

    public void eat(PlayerReconfigurationData data, Packet<?> packet) {
        if (packet instanceof ClientboundKeepAlivePacket) return; // keep alive packets handled above
        data.getEaten().add(packet);
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<Packet<?>> packetClass() {
        return (Class<Packet<?>>) (Class<?>) Packet.class;
    }

}
