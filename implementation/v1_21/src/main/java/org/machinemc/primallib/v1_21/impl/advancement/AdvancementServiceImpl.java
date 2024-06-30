package org.machinemc.primallib.v1_21.impl.advancement;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.key.Key;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.advancement.Advancement;
import org.machinemc.primallib.advancement.AdvancementProgress;
import org.machinemc.primallib.advancement.AdvancementService;
import org.machinemc.primallib.v1_21.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_21.util.Converters;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AdvancementServiceImpl extends AdvancementService implements Listener {

    private final Map<Player, Map<Key, Advancement>> cachedAdvancements = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        cachedAdvancements.put(event.getPlayer(), new ConcurrentHashMap<>());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        cachedAdvancements.remove(event.getPlayer());
    }

    public void setAdvancements(Player player, ClientboundUpdateAdvancementsPacket packet) {
        Map<Key, Advancement> map = cachedAdvancements.get(player);
        if (packet.shouldReset()) map.clear();
        packet.getAdded().forEach(advancement -> map.put(Converters.fromMinecraft(advancement.id()), Converters.fromMinecraft(advancement)));
        packet.getRemoved().forEach(key -> map.remove(Converters.fromMinecraft(key)));
    }

    @Override
    public void sendAdvancements(Player player, boolean reset, Collection<Advancement> toAdd, Collection<Key> toRemove, Map<Key, AdvancementProgress> progressMap) {
        Map<ResourceLocation, net.minecraft.advancements.AdvancementProgress> map = new HashMap<>();
        progressMap.forEach((key, progress) -> map.put(Converters.toMinecraft(key), Converters.toMinecraft(progress)));
        var packet = new ClientboundUpdateAdvancementsPacket(
                reset,
                toAdd.stream().map(Converters::toMinecraft).toList(),
                toRemove.stream().map(Converters::toMinecraft).collect(Collectors.toSet()),
                map
        );
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    @Override
    public List<Advancement> getAdvancements(Player player) {
        return ImmutableList.copyOf(cachedAdvancements.get(player).values());
    }

    @Override
    public Optional<Advancement> getAdvancement(Player player, Key key) {
        return Optional.ofNullable(cachedAdvancements.get(player).get(key));
    }

    @Override
    public void selectAdvancementsTab(Player player, @Nullable Key tab) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                new ClientboundSelectAdvancementsTabPacket(tab != null ? Converters.toMinecraft(tab) : null),
                false
        );
    }

}
