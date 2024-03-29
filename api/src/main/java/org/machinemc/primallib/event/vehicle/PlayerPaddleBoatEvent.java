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
 * Is called when a {@link Player} turns paddles of a boat while riding it.
 * <p>
 * This is the only interaction sent by the client regarding movement while spectating
 * other entity.
 * <p>
 * This event does not care about server side and is fired even
 * if player is not riding an actual boat.
 */
@Getter
@Setter
@VersionDependant
public class PlayerPaddleBoatEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Whether the left paddle is turning.
     */
    private boolean leftTurning;

    /**
     * Whether the right paddle is turning.
     */
    private boolean rightTurning;

    private boolean cancelled = false;

    public PlayerPaddleBoatEvent(Player who, boolean left, boolean right) {
        this(who, left, right, !Bukkit.isPrimaryThread());
    }

    public PlayerPaddleBoatEvent(Player who, boolean left, boolean right, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        leftTurning = left;
        rightTurning = right;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
