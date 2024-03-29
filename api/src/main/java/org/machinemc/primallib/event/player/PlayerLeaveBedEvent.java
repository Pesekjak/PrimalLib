package org.machinemc.primallib.event.player;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Is called when a {@link Player} attempts to leave bed.
 * <p>
 * In comparison to {@link org.bukkit.event.player.PlayerBedLeaveEvent}, this event
 * is called even when the server side state of the bed does not exist.
 * (player tries to leave non-existing bed)
 */
@Getter
@Setter
@VersionDependant
public class PlayerLeaveBedEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled = false;

    public PlayerLeaveBedEvent(Player who) {
        this(who, !Bukkit.isPrimaryThread());
    }

    public PlayerLeaveBedEvent(Player who, boolean async) {
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
