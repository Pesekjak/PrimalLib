package org.machinemc.primallib.v1_21.listeners.packet.clientbound.configuration;

import net.minecraft.network.ProtocolInfo;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.configuration.ConfigurationProtocols;
import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.player.ConfigurationStateService;
import org.machinemc.primallib.v1_21.impl.player.ConfigurationStateServiceImpl;
import org.machinemc.primallib.v1_21.util.configuration.PlayerReconfigurationData;

import java.util.HashSet;
import java.util.Set;

/**
 * Filters all outgoing play packets while player is kept in reconfiguration by PrimalLib.
 */
public class ConfigurationFilteringPacketListener implements PacketListener<Packet<?>> {

    private static final Set<PacketType<?>> configurationPackets;

    static {
        configurationPackets = new HashSet<>();
        ProtocolInfo.Unbound.PacketVisitor packetVisitor = (type, protocolId) -> configurationPackets.add(type);
        ConfigurationProtocols.SERVERBOUND_TEMPLATE.listPackets(packetVisitor);
        ConfigurationProtocols.CLIENTBOUND_TEMPLATE.listPackets(packetVisitor);
    }

    @Override
    public void onPacket(PacketEvent<Packet<?>> event) throws Exception {
        ConfigurationStateServiceImpl configurationStateService = (ConfigurationStateServiceImpl) ConfigurationStateService.get();
        PlayerReconfigurationData data = configurationStateService.getReconfigurationData(event.getPlayer());

        Packet<?> packet = event.getPacket();

        switch (data.getState()) {

            case NONE -> { }

            case IN_CONFIGURATION -> {
                boolean isConfigPacket = configurationPackets.contains(packet.type());
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
    }

    public void eat(PlayerReconfigurationData data, Packet<?> packet) {
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
