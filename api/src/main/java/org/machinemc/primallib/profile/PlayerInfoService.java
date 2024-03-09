package org.machinemc.primallib.profile;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;

import java.util.*;

/**
 * Service that can manage client-side player infos for players.
 */
@VersionDependant
public abstract class PlayerInfoService extends AutoRegisteringService<PlayerInfoService> {

    /**
     * Returns instance of player info service for currently running server.
     *
     * @return player info service
     */
    public static PlayerInfoService get() {
        var provider = Bukkit.getServicesManager().getRegistration(PlayerInfoService.class);
        Preconditions.checkNotNull(provider, "Player Info service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Creates new player info from player.
     *
     * @param player player to create the player info from
     * @return player info
     */
    public abstract PlayerInfo fromPlayer(Player player);

    /**
     * Sends player info updates to given player.
     *
     * @param player player to send the updates to
     * @param playerInfos player infos to update
     * @param actions actions
     */
    public abstract void sendPlayerInfoUpdates(Player player, Collection<PlayerInfo> playerInfos, PlayerInfo.Action... actions);

    /**
     * Sends player info updates to multiple given players.
     *
     * @param players players to send the updates to
     * @param playerInfos player infos to update
     * @param actions actions
     */
    public void sendPlayerInfoUpdates(Collection<Player> players, Collection<PlayerInfo> playerInfos, PlayerInfo.Action... actions) {
        players.forEach(player -> sendPlayerInfoUpdates(player, playerInfos, actions));
    }

    /**
     * Sends player info updates to given player.
     *
     * @param player player to send the updates to
     * @param playerInfo player info to update
     * @param actions actions
     */
    public void sendPlayerInfoUpdates(Player player, PlayerInfo playerInfo, PlayerInfo.Action... actions) {
        sendPlayerInfoUpdates(player, Collections.singleton(playerInfo), actions);
    }

    /**
     * Sends player info updates to multiple given players.
     *
     * @param players players to send the updates to
     * @param playerInfo player info to update
     * @param actions actions
     */
    public void sendPlayerInfoUpdates(Collection<Player> players, PlayerInfo playerInfo, PlayerInfo.Action... actions) {
        players.forEach(player -> sendPlayerInfoUpdates(player, playerInfo, actions));
    }

    /**
     * Adds and updates player info to given player.
     *
     * @param player player to send the updates to
     * @param playerInfo player info to add and update
     */
    public void addAndUpdatePlayerInfo(Player player, PlayerInfo playerInfo) {
        sendPlayerInfoUpdates(player, playerInfo, PlayerInfo.Action.addAndUpdate());
    }

    /**
     * Adds and updates player info to multiple given players.
     *
     * @param players players to send the updates to
     * @param playerInfo player info to add and update
     */
    public void addAndUpdatePlayerInfo(Collection<Player> players, PlayerInfo playerInfo) {
        players.forEach(player -> addAndUpdatePlayerInfo(player, playerInfo));
    }

    /**
     * Removes player infos from player.
     *
     * @param player player to remove the player infos from
     * @param toRemove player infos to remove
     */
    public void removePlayerInfo(Player player, PlayerInfo... toRemove) {
        removePlayerInfo(player, Arrays.stream(toRemove).map(PlayerInfo::getUUID).toList());
    }

    /**
     * Removes player infos from players.
     *
     * @param players players to remove the player infos from
     * @param toRemove player infos to remove
     */
    public void removePlayerInfo(Collection<Player> players, PlayerInfo... toRemove) {
        players.forEach(player -> removePlayerInfo(player, toRemove));
    }

    /**
     * Removes player infos from player.
     *
     * @param player player to remove the player infos from
     * @param toRemove uuid of player infos to remove
     */
    public void removePlayerInfo(Player player, UUID... toRemove) {
        removePlayerInfo(player, List.of(toRemove));
    }

    /**
     * Removes player infos from players.
     *
     * @param players players to remove the player infos from
     * @param toRemove uuid of player infos to remove
     */
    public void removePlayerInfo(Collection<Player> players, UUID... toRemove) {
        players.forEach(player -> removePlayerInfo(player, toRemove));
    }

    /**
     * Removes player infos from player.
     *
     * @param player player to remove the player infos from
     * @param toRemove uuid of player infos to remove
     */
    public abstract void removePlayerInfo(Player player, Collection<UUID> toRemove);

    /**
     * Removes player infos from players.
     *
     * @param players players to remove the player infos from
     * @param toRemove uuid of player infos to remove
     */
    public void removePlayerInfo(Collection<Player> players, Collection<UUID> toRemove) {
        players.forEach(player -> removePlayerInfo(player, toRemove));
    }

    /**
     * Returns all player infos currently visible by the player.
     * <p>
     * This includes also unlisted player infos.
     *
     * @return player infos of the player
     */
    public abstract List<PlayerInfo> getPlayerInfos(Player player);

    /**
     * Returns player info of another player currently visible by the player.
     *
     * @param player player
     * @param from player to get the player info from (player info source)
     * @return player info
     */
    public Optional<PlayerInfo> getPlayerInfo(Player player, Player from) {
        return getPlayerInfo(player, from.getUniqueId());
    }

    /**
     * Returns player info with given UUID currently visible by the player.
     *
     * @param player player
     * @param uuid uuid of the player info
     * @return player info
     */
    public abstract Optional<PlayerInfo> getPlayerInfo(Player player, UUID uuid);

    @Override
    public Class<PlayerInfoService> getRegistrationClass() {
        return PlayerInfoService.class;
    }

}
