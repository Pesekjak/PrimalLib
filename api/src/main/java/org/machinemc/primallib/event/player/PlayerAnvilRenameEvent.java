package org.machinemc.primallib.event.player;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Event called when player changes name of an item in anvil.
 * <p>
 * This is called each time the text input field is changed by the player.
 */
@Getter
@VersionDependant
public class PlayerAnvilRenameEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final String input;

    public PlayerAnvilRenameEvent(Player who, String input) {
        this(who, input, !Bukkit.isPrimaryThread());
    }

    public PlayerAnvilRenameEvent(Player who, String input, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.input = Preconditions.checkNotNull(input, "Anvil input can not be null");
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
