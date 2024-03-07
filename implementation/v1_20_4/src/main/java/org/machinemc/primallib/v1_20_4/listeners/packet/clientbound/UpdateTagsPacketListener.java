package org.machinemc.primallib.v1_20_4.listeners.packet.clientbound;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntList;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagNetworkSerialization;
import org.machinemc.primallib.event.configuration.PlayerTagsEvent;
import org.machinemc.primallib.event.configuration.Tag;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.v1_20_4.util.Converters;

import java.util.*;

public class UpdateTagsPacketListener implements PacketListener<ClientboundUpdateTagsPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundUpdateTagsPacket> event) {
        List<Tag> tags = getTags(event.getPacket());
        List<Tag> converted = new ArrayList<>();

        for (Tag tag : tags) {
            var tagsEvent = new PlayerTagsEvent(event.getPlayer(), tag);
            if (!tagsEvent.callEvent()) continue;
            converted.add(tagsEvent.getTag());
        }

        Map<ResourceKey<? extends Registry<?>>, Map<ResourceLocation, IntList>> groups = new HashMap<>();

        for (Tag tag : converted) {
            ResourceKey<? extends Registry<?>> key = ResourceKey.createRegistryKey(Converters.toMinecraft(tag.registry()));
            Map<ResourceLocation, IntList> map = groups.computeIfAbsent(key, k -> new HashMap<>());
            tag.map().forEach((subKey, ids) -> map.put(Converters.toMinecraft(subKey), IntList.of(ids)));
        }

        Map<ResourceKey<? extends Registry<?>>, TagNetworkSerialization.NetworkPayload> payloadGroups = new HashMap<>();
        groups.forEach((key, map) -> {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeMap(map, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeIntIdList);
            TagNetworkSerialization.NetworkPayload payload = TagNetworkSerialization.NetworkPayload.read(buf);
            payloadGroups.put(key, payload);
        });

        event.setPacket(new ClientboundUpdateTagsPacket(payloadGroups));
    }

    private static List<Tag> getTags(ClientboundUpdateTagsPacket packet) {
        var tagData = packet.getTags();

        List<Tag> tags = new ArrayList<>();

        tagData.forEach((location, payload) -> {
            Key key = Converters.fromMinecraft(location.location());
            Map<Key, int[]> map = new HashMap<>();

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            payload.write(buf);
            buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readIntIdList)
                    .forEach((subKey, ints) -> map.put(Converters.fromMinecraft(subKey), ints.toIntArray()));

            tags.add(new Tag(key, map));
        });
        return tags;
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundUpdateTagsPacket> packetClass() {
        return ClientboundUpdateTagsPacket.class;
    }

}
