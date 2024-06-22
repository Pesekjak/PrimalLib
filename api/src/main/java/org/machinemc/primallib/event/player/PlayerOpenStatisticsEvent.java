package org.machinemc.primallib.event.player;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Event called when player opens the Statistics menu accessible
 * from the pause menu screen.
 */
@VersionDependant
public class PlayerOpenStatisticsEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public PlayerOpenStatisticsEvent(Player who) {
        this(who, !Bukkit.isPrimaryThread());
    }

    public PlayerOpenStatisticsEvent(Player who, boolean async) {
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
