package org.machinemc.primallib.v1_20_6.listeners.packet.clientbound;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import org.machinemc.primallib.event.configuration.PlayerRegistryDataEvent;
import org.machinemc.primallib.event.configuration.Registry;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.v1_20_6.util.Converters;

import java.util.*;

public class RegistryDataPacketListener implements PacketListener<ClientboundRegistryDataPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundRegistryDataPacket> event) {
        var packet = event.getPacket();

        Map<Integer, Registry.Entry> entries = new LinkedHashMap<>();

        for (RegistrySynchronization.PackedRegistryEntry entry : packet.entries()) {
            Key name = Converters.fromMinecraft(entry.id());
            CompoundBinaryTag tag = entry.data().map(t -> Converters.fromMinecraft((CompoundTag) t)).orElse(null);
            Registry.Entry wrapped = new Registry.Entry(name, tag);
            entries.put(entries.size(), wrapped);
        }

        Registry registry = new Registry(Converters.fromMinecraft(packet.registry().location()), entries);


        var registryEvent = new PlayerRegistryDataEvent(event.getPlayer(), registry);
        registryEvent.callEvent();

        List<RegistrySynchronization.PackedRegistryEntry> modified = new ArrayList<>();
        for (Registry.Entry entry : registry.getEntries().values()) {
            modified.add(new RegistrySynchronization.PackedRegistryEntry(
                    Converters.toMinecraft(entry.key()),
                    entry.element() != null
                            ? Optional.of(Converters.toMinecraft(entry.element()))
                            : Optional.empty()
            ));
        }

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
