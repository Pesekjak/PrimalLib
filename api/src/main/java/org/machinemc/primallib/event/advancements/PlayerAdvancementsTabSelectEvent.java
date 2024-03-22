package org.machinemc.primallib.event.advancements;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Is called when player interacts with the advancements menu screen.
 */
@Getter
@VersionDependant
public class PlayerAdvancementsTabSelectEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Action action;
    private final @Nullable Key tab;

    public PlayerAdvancementsTabSelectEvent(Player who, Action action, @Nullable Key tab) {
        this(who, action, tab, !Bukkit.isPrimaryThread());
    }

    public PlayerAdvancementsTabSelectEvent(Player who, Action action, @Nullable Key tab, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.action = Preconditions.checkNotNull(action, "Action can not be null");
        this.tab = tab;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Action player done with the advancements screen.
     */
    public enum Action {

        /**
         * Action when player opens a new tab, either
         * by opening the advancements menu by pressing L (or the menu button)
         * or switching from one tab to another.
         */
        OPENED_TAB,

        /**
         * Action when player closes the advancements menu.
         */
        CLOSED_SCREEN

    }

}
