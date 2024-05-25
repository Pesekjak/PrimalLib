package org.machinemc.primallib.event.player;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Is called when {@link Player} receives a login request.
 * <p>
 * This event can change the client state of some properties sent on player login.
 * <p>
 * This event can be triggered with {@link org.machinemc.primallib.player.PlayerActionService#resendConfigurations(Player)}.
 */
@Getter
@Setter
@VersionDependant
public class PlayerLoginEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Changes client state to hardcore mode.
     */
    private boolean hardcore;

    /**
     * If true, client shows reduced information on the debug screen.
     */
    private boolean reducedDebugInfo;

    /**
     * True if the world is a debug mode world;
     * debug mode worlds cannot be modified and have predefined blocks.
     */
    @ApiStatus.Experimental
    private boolean isDebug;

    /**
     * True if the world is a super-flat world;
     * flat worlds have different void fog and a horizon at y level 0 instead of 63.
     */
    private boolean isFlat;

    /**
     * Whether the server enforces secure chat.
     * <p>
     * If set to false, this will display a warning pop up on join.
     */
    private boolean enforcesSecureChat;

    public PlayerLoginEvent(Player who, boolean hardcore, boolean reducedDebugInfo, boolean isDebug, boolean isFlat, boolean enforcesSecureChat) {
        this(who, hardcore, reducedDebugInfo, isDebug, isFlat, enforcesSecureChat, !Bukkit.isPrimaryThread());
    }

    public PlayerLoginEvent(Player who, boolean hardcore, boolean reducedDebugInfo, boolean isDebug, boolean isFlat, boolean enforcesSecureChat, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.hardcore = hardcore;
        this.reducedDebugInfo = reducedDebugInfo;
        this.isDebug = isDebug;
        this.isFlat = isFlat;
        this.enforcesSecureChat = enforcesSecureChat;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
