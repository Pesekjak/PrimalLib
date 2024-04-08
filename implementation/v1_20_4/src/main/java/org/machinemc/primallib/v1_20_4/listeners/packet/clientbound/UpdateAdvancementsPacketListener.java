package org.machinemc.primallib.v1_20_4.listeners.packet.clientbound;

import net.kyori.adventure.key.Key;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.entity.Player;
import org.machinemc.primallib.advancement.Advancement;
import org.machinemc.primallib.advancement.AdvancementProgress;
import org.machinemc.primallib.advancement.AdvancementService;
import org.machinemc.primallib.event.advancements.PlayerAdvancementsUpdateEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.v1_20_4.impl.advancement.AdvancementServiceImpl;
import org.machinemc.primallib.v1_20_4.util.Converters;

import java.util.*;
import java.util.stream.Collectors;

public class UpdateAdvancementsPacketListener implements PacketListener<ClientboundUpdateAdvancementsPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundUpdateAdvancementsPacket> event) {
        AdvancementServiceImpl advancementService = (AdvancementServiceImpl) AdvancementService.get();

        Player player = event.getPlayer();
        var packet = event.getPacket();

        List<Advancement> toAdd = packet.getAdded().stream().map(Converters::fromMinecraft).toList();

        Set<Key> toRemoveSet = new HashSet<>(packet.getRemoved().stream()
                .map(Converters::fromMinecraft)
                .map(namespacedKey -> (Key) namespacedKey)
                .toList());

        Map<Key, AdvancementProgress> progressMap = new HashMap<>();
        packet.getProgress().forEach((key, progress) ->
                progressMap.put(Converters.fromMinecraft(key), Converters.fromMinecraft(progress))
        );

        var advancementsUpdateEvent = new PlayerAdvancementsUpdateEvent(
                player,
                packet.shouldReset(),
                toAdd,
                toRemoveSet,
                progressMap
        );

        if (!advancementsUpdateEvent.callEvent()) {
            event.setCancelled(true);
            return;
        }

        var minecraftToAdd = advancementsUpdateEvent.getToAdd().stream().map(Converters::toMinecraft).toList();
        var minecraftToRemove = advancementsUpdateEvent.getToRemove().stream().map(Converters::toMinecraft).collect(Collectors.toSet());
        Map<ResourceLocation, net.minecraft.advancements.AdvancementProgress> minecraftProgress = new HashMap<>();
        advancementsUpdateEvent.getProgressMap().forEach((key, progress) ->
                minecraftProgress.put(Converters.toMinecraft(key), Converters.toMinecraft(progress))
        );

        packet = new ClientboundUpdateAdvancementsPacket(
                advancementsUpdateEvent.isReset(),
                minecraftToAdd,
                minecraftToRemove,
                minecraftProgress
        );

        event.setPacket(packet);

        advancementService.setAdvancements(player, packet);
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundUpdateAdvancementsPacket> packetClass() {
        return ClientboundUpdateAdvancementsPacket.class;
    }

}
