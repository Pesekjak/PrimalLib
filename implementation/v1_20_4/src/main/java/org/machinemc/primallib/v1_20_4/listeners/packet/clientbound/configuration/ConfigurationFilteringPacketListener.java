package org.machinemc.primallib.v1_20_4.listeners.packet.clientbound.configuration;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.player.PlayerActionService;
import org.machinemc.primallib.v1_20_4.impl.player.PlayerActionServiceImpl;
import org.machinemc.primallib.v1_20_4.util.configuration.PlayerReconfigurationData;

import java.util.HashSet;
import java.util.Set;

/**
 * Filters all outgoing play packets while player is kept in reconfiguration by PrimalLib.
 */
public class ConfigurationFilteringPacketListener implements PacketListener<Packet<?>> {

    private static final Set<Class<? extends Packet<?>>> configurationPackets;

    static {
        configurationPackets = new HashSet<>(ConnectionProtocol.CONFIGURATION.getPacketsByIds(PacketFlow.CLIENTBOUND).values());
    }

    @Override
    public void onPacket(PacketEvent<Packet<?>> event) throws Exception {
        PlayerActionServiceImpl playerActionService = (PlayerActionServiceImpl) PlayerActionService.get();
        PlayerReconfigurationData data = playerActionService.getReconfigurationData(event.getPlayer());

        Packet<?> packet = event.getPacket();

        switch (data.getState()) {

            case NONE -> { }

            case IN_CONFIGURATION -> {
                boolean isConfigPacket = configurationPackets.contains(packet.getClass());
                if (isConfigPacket) return;

                event.setCancelled(true);
                eat(data, packet);
            }

            case AWAITING_CONFIGURATION_REQ -> {
                boolean isReq = packet instanceof ClientboundStartConfigurationPacket;

                if (isReq) {
                    data.setState(PlayerReconfigurationData.State.AWAITING_CONFIGURATION_ACK);
                    return;
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
