package org.machinemc.primallib.event.player;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Is called when a {@link Player} exists the configuration state after
 * exiting it using {@link org.machinemc.primallib.player.PlayerActionService#switchToPlay(Player)}.
 * <p>
 * This event is not called when the client state change was not initiated by PrimalLib.
 */
@VersionDependant
public class PlayerExitReconfigurationEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public PlayerExitReconfigurationEvent(Player who) {
        this(who, !Bukkit.isPrimaryThread());
    }

    public PlayerExitReconfigurationEvent(Player who, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
