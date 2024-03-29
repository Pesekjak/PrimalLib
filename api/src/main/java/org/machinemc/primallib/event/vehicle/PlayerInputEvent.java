package org.machinemc.primallib.event.vehicle;

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
 * Is called when a {@link Player} inputs movement update while riding an entity.
 * <p>
 * This event does not care about server side state and is fired even
 * if player is not riding an actual entity.
 */
@Getter
@Setter
@VersionDependant
public class PlayerInputEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Positive to the left of the player.
     */
    private float sidewaysDelta;
    /**
     * Positive forward.
     */
    private float forwardDelta;

    private boolean jumping;
    private boolean dismounting;

    private boolean cancelled = false;

    public PlayerInputEvent(Player who, float sideways, float forward, boolean jumping, boolean dismounting) {
        this(who, sideways, forward, jumping, dismounting, !Bukkit.isPrimaryThread());
    }

    public PlayerInputEvent(Player who, float sideways, float forward, boolean jumping, boolean dismounting, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        sidewaysDelta = sideways;
        forwardDelta = forward;
        this.jumping = jumping;
        this.dismounting = dismounting;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
