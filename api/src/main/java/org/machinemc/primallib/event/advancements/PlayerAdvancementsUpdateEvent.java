package org.machinemc.primallib.event.advancements;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.advancement.Advancement;
import org.machinemc.primallib.advancement.AdvancementProgress;
import org.machinemc.primallib.version.VersionDependant;

import java.util.*;

/**
 * Is called when a {@link Player} receives advancement update.
 * <p>
 * All collections provided by this event are modifiable and can be modified
 * to change the final state of data provided by
 * {@link org.machinemc.primallib.advancement.AdvancementService}.
 */
@Getter
@VersionDependant
public class PlayerAdvancementsUpdateEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Whether to reset/clear the current advancements.
     * <p>
     * If false, the toasts of completed advancements will always be displayed
     */
    @Setter
    private boolean reset;

    private final List<Advancement> toAdd;
    private final List<Key> toRemove;
    private final Map<Key, AdvancementProgress> progressMap;

    @Setter
    private boolean cancelled = false;

    public PlayerAdvancementsUpdateEvent(Player who,
                                         boolean reset,
                                         Collection<Advancement> toAdd,
                                         Collection<Key> toRemove,
                                         Map<Key, AdvancementProgress> progressMap) {
        this(
                who,
                reset,
                toAdd,
                toRemove,
                progressMap,
                !Bukkit.isPrimaryThread()
        );
    }

    public PlayerAdvancementsUpdateEvent(Player who,
                                         boolean reset,
                                         Collection<Advancement> toAdd,
                                         Collection<Key> toRemove,
                                         Map<Key, AdvancementProgress> progressMap,
                                         boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.reset = reset;
        this.toAdd = new ArrayList<>(Preconditions.checkNotNull(toAdd, "Advancements to add can not be null"));
        this.toRemove = new ArrayList<>(Preconditions.checkNotNull(toRemove, "Advancements to remove can not be null"));
        this.progressMap = new HashMap<>(Preconditions.checkNotNull(progressMap, "Advancement progress map can not be null"));
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
