package org.machinemc.primallib.event.profile;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.machinemc.primallib.profile.PlayerInfo;
import org.machinemc.primallib.version.VersionDependant;

import java.util.Collection;
import java.util.Set;

/**
 * Is called when a {@link Player} receives {@link PlayerInfo} update.
 * <p>
 * Should only be used for packet/clientside related stuff.
 * Not intended for modifying server side state.
 */
@Getter
@VersionDependant
public class PlayerPlayerInfoUpdateEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Player info being updated.
     */
    private PlayerInfo playerInfo;

    /**
     * Actions that will be applied to the player info.
     */
    private final @Unmodifiable Set<PlayerInfo.Action> actions;

    @Setter
    private boolean cancelled = false;

    public PlayerPlayerInfoUpdateEvent(Player who, PlayerInfo playerInfo, Collection<PlayerInfo.Action> actions) {
        this(who, playerInfo, actions, !Bukkit.isPrimaryThread());
    }

    public PlayerPlayerInfoUpdateEvent(Player who, PlayerInfo playerInfo, Collection<PlayerInfo.Action> actions, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.playerInfo = Preconditions.checkNotNull(playerInfo, "Player info can not be null");
        this.actions = Set.copyOf(actions);
    }

    /**
     * Changes the player info that will be updated,
     * can be used to just change some properties of the original player info
     * such as listed visibility or gamemode.
     *
     * @param playerInfo new player info
     */
    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = Preconditions.checkNotNull(playerInfo, "Player info can not be null");
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
