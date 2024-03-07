package org.machinemc.primallib.v1_20_4.listeners.packet.clientbound;

import com.google.common.base.Preconditions;
import io.netty.buffer.Unpooled;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import org.machinemc.primallib.event.configuration.PlayerRegistryDataEvent;
import org.machinemc.primallib.event.configuration.Registry;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.v1_20_4.util.Converters;

import java.util.ArrayList;
import java.util.List;

public class RegistryDataPacketListener implements PacketListener<ClientboundRegistryDataPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundRegistryDataPacket> event) {
        var packet = event.getPacket();
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        packet.write(buf);
        CompoundBinaryTag nbt = Converters.fromMinecraft(buf.readNbt());
        Preconditions.checkNotNull(nbt, "Invalid NBT format");

        List<Registry> registries = Registry.getRegistries(nbt);
        List<Registry> modified = new ArrayList<>();

        for (Registry registry : registries) {
            var registryEvent = new PlayerRegistryDataEvent(event.getPlayer(), registry);
            registryEvent.callEvent();
            modified.add(registryEvent.getRegistry());
        }

        CompoundBinaryTag modifiedNBT = Registry.createCodec(modified);
        buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeNbt(Converters.toMinecraft(modifiedNBT));
        var modifiedPacket = new ClientboundRegistryDataPacket(buf);

        event.setPacket(modifiedPacket);
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
