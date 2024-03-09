package org.machinemc.primallib.v1_20_4.impl.profile;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.machinemc.primallib.profile.ChatSession;
import org.machinemc.primallib.profile.GameProfile;
import org.machinemc.primallib.profile.PlayerInfo;
import org.machinemc.primallib.profile.PlayerInfoService;
import org.machinemc.primallib.v1_20_4.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_20_4.util.Converters;

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

            if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER))
                playerInfo = playerInfo.withGameProfile(Converters.fromMinecraft(entry.profile()));

            if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.INITIALIZE_CHAT))
                playerInfo = playerInfo.withChatSession(Converters.fromMinecraft(entry.chatSession()));

            if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE))
                playerInfo = playerInfo.withGameMode(Converters.fromMinecraft(entry.gameMode()));

            if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED))
                playerInfo = playerInfo.withListed(entry.listed());

            if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY))
                playerInfo = playerInfo.withLatency(entry.latency());

            if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME))
                playerInfo = playerInfo.withDisplayName(Converters.fromMinecraft(entry.displayName()));

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
        EnumSet<ClientboundPlayerInfoUpdatePacket.Action> actionEnumSet = EnumSet.copyOf(Arrays.stream(actions).map(this::getAction).toList());
        List<ClientboundPlayerInfoUpdatePacket.Entry> entries = playerInfos.stream().map(this::asEntry).toList();
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

    private ClientboundPlayerInfoUpdatePacket.Entry asEntry(PlayerInfo playerInfo) {
        UUID uuid = playerInfo.getUUID();
        com.mojang.authlib.GameProfile gameProfile = Converters.toMinecraft(playerInfo.gameProfile());
        boolean listed = playerInfo.listed();
        int latency = playerInfo.latency();
        GameType gameType = Converters.toMinecraft(playerInfo.gameMode());
        Component displayName = playerInfo.hasDisplayName()
                ? Converters.toMinecraft(playerInfo.displayName())
                : null;
        RemoteChatSession.Data session = playerInfo.hasInitializedChat()
                ? Converters.toMinecraft(playerInfo.chatSession())
                : null;
        return new ClientboundPlayerInfoUpdatePacket.Entry(uuid, gameProfile, listed, latency, gameType, displayName, session);
    }

    private ClientboundPlayerInfoUpdatePacket.Action getAction(PlayerInfo.Action action) {
        return switch (action) {
            case ADD_PLAYER -> ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER;
            case INITIALIZE_CHAT -> ClientboundPlayerInfoUpdatePacket.Action.INITIALIZE_CHAT;
            case UPDATE_GAME_MODE -> ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE;
            case UPDATE_LISTED -> ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED;
            case UPDATE_LATENCY -> ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY;
            case UPDATE_DISPLAY_NAME -> ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME;
        };
    }

}
