package org.machinemc.primallib.event.configuration;

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
 * Is called when a {@link Player} receives tags update.
 * <p>
 * Should only be used for packet/clientside related stuff.
 * Not intended for modifying server side state.
 */
@Getter
@VersionDependant
public class PlayerTagsEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Tag tag;

    @Setter
    private boolean cancelled = false;

    public PlayerTagsEvent(Player who, Tag tag) {
        this(who, tag, !Bukkit.isPrimaryThread());
    }

    public PlayerTagsEvent(Player who, Tag tag, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.tag = Preconditions.checkNotNull(tag, "Tag can not be null");
    }

    /**
     * Sets new tag for this event.
     *
     * @param tag new tag
     */
    public void setTags(Tag tag) {
        this.tag = Preconditions.checkNotNull(tag, "Tag array can not be null");
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
