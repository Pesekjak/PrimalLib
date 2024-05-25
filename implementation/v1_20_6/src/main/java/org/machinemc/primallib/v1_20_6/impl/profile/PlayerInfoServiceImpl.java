package org.machinemc.primallib.v1_20_6.impl.profile;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.machinemc.primallib.profile.ChatSession;
import org.machinemc.primallib.profile.GameProfile;
import org.machinemc.primallib.profile.PlayerInfo;
import org.machinemc.primallib.profile.PlayerInfoService;
import org.machinemc.primallib.v1_20_6.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_20_6.util.Converters;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInfoServiceImpl extends PlayerInfoService implements Listener {

    private final Map<Player, Map<UUID, PlayerInfo>> cachedPlayerInfos = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        cachedPlayerInfos.put(event.getPlayer(), new HashMap<>());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        cachedPlayerInfos.remove(event.getPlayer());
    }

    public void setPlayerInfos(Player player, ClientboundPlayerInfoUpdatePacket packet) {
        Map<UUID, PlayerInfo> map = cachedPlayerInfos.get(player);
        EnumSet<ClientboundPlayerInfoUpdatePacket.Action> actions = packet.actions();

        for (ClientboundPlayerInfoUpdatePacket.Entry entry : packet.entries()) {
            PlayerInfo playerInfo;
            if (map.containsKey(entry.profileId())) {
                playerInfo = map.get(entry.profileId());
            } else if (entry.profile() != null) {
                playerInfo = new PlayerInfo(Converters.fromMinecraft(entry.profile()));
            } else {
                continue; // tried to update non-existing profile
            }

            playerInfo = Converters.fromMinecraft(entry, playerInfo, actions);
            map.put(entry.profileId(), playerInfo);
        }
    }

    public void removePlayerInfos(Player player, ClientboundPlayerInfoRemovePacket packet) {
        Map<UUID, PlayerInfo> map = cachedPlayerInfos.get(player);
        packet.profileIds().forEach(map::remove);
    }

    @Override
    public PlayerInfo fromPlayer(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer handle = craftPlayer.getHandle();

        GameProfile gameProfile = Converters.fromMinecraft(handle.gameProfile);
        ChatSession session = handle.getChatSession() != null
                ? Converters.fromMinecraft(handle.getChatSession().asData())
                : null;
        GameMode gameMode = player.getGameMode();
        int latency = handle.connection.latency();

        return new PlayerInfo(gameProfile, session, gameMode, true, latency, null);
    }

    @Override
    public void sendPlayerInfoUpdates(Player player, Collection<PlayerInfo> playerInfos, PlayerInfo.Action... actions) {
        EnumSet<ClientboundPlayerInfoUpdatePacket.Action> actionEnumSet = EnumSet.copyOf(Arrays.stream(actions).map(Converters::toMinecraft).toList());
        List<ClientboundPlayerInfoUpdatePacket.Entry> entries = playerInfos.stream().map(Converters::toMinecraft).toList();
        ClientboundPlayerInfoUpdatePacket packet = new ClientboundPlayerInfoUpdatePacket(actionEnumSet, entries);
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    @Override
    public void removePlayerInfo(Player player, Collection<UUID> toRemove) {
        ClientboundPlayerInfoRemovePacket packet = new ClientboundPlayerInfoRemovePacket(new ArrayList<>(toRemove));
        PacketChannelHandlerImpl.sendPacket(player, packet, false);
    }

    @Override
    public List<PlayerInfo> getPlayerInfos(Player player) {
        return ImmutableList.copyOf(cachedPlayerInfos.get(player).values());
    }

    @Override
    public Optional<PlayerInfo> getPlayerInfo(Player player, UUID uuid) {
        return Optional.ofNullable(cachedPlayerInfos.get(player).get(uuid));
    }

}
