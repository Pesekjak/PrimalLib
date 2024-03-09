package org.machinemc.primallib.v1_20_4.impl.advancement;

import net.kyori.adventure.key.Key;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.machinemc.primallib.advancement.Advancement;
import org.machinemc.primallib.advancement.AdvancementProgress;
import org.machinemc.primallib.advancement.AdvancementService;
import org.machinemc.primallib.v1_20_4.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_20_4.util.Converters;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AdvancementServiceImpl extends AdvancementService implements Listener {

    private final Map<Player, Map<ResourceLocation, AdvancementHolder>> cachedAdvancements = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        cachedAdvancements.put(event.getPlayer(), new HashMap<>());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        cachedAdvancements.remove(event.getPlayer());
    }

    public void setAdvancements(Player player, ClientboundUpdateAdvancementsPacket packet) {
        Map<ResourceLocation, AdvancementHolder> map = cachedAdvancements.get(player);
        if (packet.shouldReset()) map.clear();
        packet.getAdded().forEach(advancement -> map.put(advancement.id(), advancement));
        packet.getRemoved().forEach(map::remove);
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
        if (!cachedAdvancements.containsKey(player)) return Collections.emptyList();
        return cachedAdvancements.get(player).values().stream().map(Converters::fromMinecraft).toList();
    }

    @Override
    public Optional<Advancement> getAdvancement(Player player, Key key) {
        if (!cachedAdvancements.containsKey(player)) return Optional.empty();
        return Optional.ofNullable(cachedAdvancements.get(player).get(Converters.toMinecraft(key))).map(Converters::fromMinecraft);
    }

}
