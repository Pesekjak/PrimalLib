package org.machinemc.primallib.v1_21.impl.player;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import net.kyori.adventure.key.Key;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.configuration.*;
import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.world.flag.FeatureFlags;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.machinemc.primallib.event.configuration.Registry;
import org.machinemc.primallib.player.ConfigurationStateService;
import org.machinemc.primallib.v1_21.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_21.util.Converters;
import org.machinemc.primallib.v1_21.util.configuration.PlayerReconfigurationData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationStateServiceImpl extends ConfigurationStateService {

    private final Map<Player, PlayerReconfigurationData> reconfigurationData = new ConcurrentHashMap<>();
    private final Table<Player, Key, Registry> registryCache = Tables.newCustomTable(new ConcurrentHashMap<>(), () -> Collections.synchronizedMap(new LinkedHashMap<>()));

    public PlayerReconfigurationData getReconfigurationData(Player player) {
        return reconfigurationData.computeIfAbsent(player, PlayerReconfigurationData::new);
    }

    public void updateRegistry(Player player, Key key, Registry registry) {
        registryCache.put(player, key, registry);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        reconfigurationData.remove(player);
        registryCache.rowMap().remove(player);
    }

    @Override
    public void switchToConfiguration(Player player) {
        player.closeInventory();
        getReconfigurationData(player).setState(PlayerReconfigurationData.State.AWAITING_CONFIGURATION_REQ);
        PacketChannelHandlerImpl.sendPacket(player, ClientboundStartConfigurationPacket.INSTANCE, false);
    }

    @Override
    public void switchToPlay(Player player) {
        PacketChannelHandlerImpl.sendPacket(player, ClientboundFinishConfigurationPacket.INSTANCE, false);
    }

    @Override
    public void resendEnabledFeatures(Player player) {
        ServerPlayer handle = ((CraftPlayer) player).getHandle();
        var packet = new ClientboundUpdateEnabledFeaturesPacket(FeatureFlags.REGISTRY.toNames(handle.server.getWorldData().enabledFeatures()));
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    @Override
    public void resendServerResources(Player player) {
        ServerPlayer handle = ((CraftPlayer) player).getHandle();
        List<KnownPack> list = handle.server.getResourceManager().listPacks()
                .flatMap(pack -> pack.location().knownPackInfo().stream())
                .toList();
        PacketChannelHandlerImpl.sendPacket(player, new ClientboundSelectKnownPacks(list), false);
    }

    @Override
    public void resendRegistries(Player player) {
        List<Registry> registryList = new ArrayList<>(registryCache.row(player).values());
        for (Registry registry : registryList) {
            var key = ResourceKey.createRegistryKey(Converters.toMinecraft(registry.getKey()));
            List<RegistrySynchronization.PackedRegistryEntry> modified = registry.getEntries().values().stream()
                    .map(Converters::toMinecraft)
                    .toList();

            PacketChannelHandlerImpl.sendPacket(player, new ClientboundRegistryDataPacket(key, modified), false);
        }
    }

    @Override
    public void resendTags(Player player) {
        ServerPlayer handle = ((CraftPlayer) player).getHandle();
        LayeredRegistryAccess<RegistryLayer> registryAccess = handle.server.registries();
        PacketChannelHandlerImpl.sendPacket(player, new ClientboundUpdateTagsPacket(TagNetworkSerialization.serializeTagsToNetwork(registryAccess)), false);
    }

    @Override
    public void clearChat(Player player) {
        PacketChannelHandlerImpl.sendPacket(player, ClientboundResetChatPacket.INSTANCE, false);
    }

}
