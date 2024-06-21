package org.machinemc.primallib.v1_21.listeners.packet.clientbound;

import net.minecraft.core.RegistrySynchronization;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import org.machinemc.primallib.event.configuration.PlayerRegistryDataEvent;
import org.machinemc.primallib.event.configuration.Registry;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.player.ConfigurationStateService;
import org.machinemc.primallib.v1_21.impl.player.ConfigurationStateServiceImpl;
import org.machinemc.primallib.v1_21.util.Converters;

import java.util.*;

public class RegistryDataPacketListener implements PacketListener<ClientboundRegistryDataPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundRegistryDataPacket> event) {
        var packet = event.getPacket();

        Map<Integer, Registry.Entry> entries = new LinkedHashMap<>();

        for (RegistrySynchronization.PackedRegistryEntry entry : packet.entries()) {
            entries.put(entries.size(), Converters.fromMinecraft(entry));
        }

        Registry registry = new Registry(Converters.fromMinecraft(packet.registry().location()), entries);
        ConfigurationStateServiceImpl configurationStateService = (ConfigurationStateServiceImpl) ConfigurationStateService.get();
        configurationStateService.updateRegistry(event.getPlayer(), registry.getKey(), registry);

        var registryEvent = new PlayerRegistryDataEvent(event.getPlayer(), registry);
        registryEvent.callEvent();

        List<RegistrySynchronization.PackedRegistryEntry> modified = registry.getEntries().values().stream()
                .map(Converters::toMinecraft)
                .toList();

        event.setPacket(new ClientboundRegistryDataPacket(packet.registry(), modified));
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundRegistryDataPacket> packetClass() {
        return ClientboundRegistryDataPacket.class;
    }

}
