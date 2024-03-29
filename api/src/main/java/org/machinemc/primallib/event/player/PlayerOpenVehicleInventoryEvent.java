package org.machinemc.primallib.event.player;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.version.VersionDependant;

import java.util.Optional;

/**
 * Is called when a {@link Player} attempts to open vehicle inventory.
 * <p>
 * Open vehicle inventory is only sent when pressing the inventory key (default: E)
 * while on a horse/llama or chest boat — all other methods of opening such an inventory
 * (involving right-clicking or shift-right-clicking it) do not use this event.
 * <p>
 * This event does not require player to ride an actual server side entity.
 */
@Getter
@Setter
@VersionDependant
public class PlayerOpenVehicleInventoryEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled = false;

    public PlayerOpenVehicleInventoryEvent(Player who) {
        this(who, !Bukkit.isPrimaryThread());
    }

    public PlayerOpenVehicleInventoryEvent(Player who, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
    }

    /**
     * Returns current vehicle of the player.
     * <p>
     * Can be null in case there is no server side vehicle
     * for the player.
     *
     * @return vehicle
     */
    public Optional<Entity> getVehicle() {
        return Optional.ofNullable(player.getVehicle());
    }

    /**
     * Returns inventory of current vehicle of the player.
     * <p>
     * Can be null in case there is no server side vehicle
     * for the player that holds an inventory.
     *
     * @return inventory of player's vehicle
     */
    public Optional<Inventory> getInventory() {
        Entity vehicle = getVehicle().orElse(null);
        if (!(vehicle instanceof InventoryHolder holder))
            return Optional.empty();
        return Optional.of(holder.getInventory());
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
