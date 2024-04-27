package org.machinemc.primallib.player;

import com.google.common.base.Preconditions;
import io.papermc.paper.math.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AbstractHorseInventory;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.util.OwnerPlugin;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;
import org.machinemc.primallib.world.BlockAction;

/**
 * Service for additional interactions with players not available in the API that
 * did not fit anywhere else.
 */
@VersionDependant
public abstract class PlayerActionService extends AutoRegisteringService<PlayerActionService> {

    /**
     * Returns instance of player action service for currently running server.
     *
     * @return player action service
     */
    public static PlayerActionService get() {
        var provider = Bukkit.getServicesManager().getRegistration(PlayerActionService.class);
        Preconditions.checkNotNull(provider, "Plugin Message service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Opens sign at given position to player.
     * <p>
     * Compare to the method in the API, sign at this position can be fully
     * client-sided and used for sign input menus.
     * <p>
     * Should only be used for packet/clientside related stuff.
     * Not intended for modifying server side state.
     *
     * @param player player to open the sign for
     * @param position position of the sign
     * @param side side of the sign to open
     * @see org.machinemc.primallib.event.block.PlayerSignChangeEvent
     */
    @SuppressWarnings("UnstableApiUsage")
    public abstract void openSign(Player player, BlockPosition position, Side side);

    /**
     * Changes camera spectator target of player to another entity.
     * <p>
     * Should only be used for packet/clientside related stuff.
     * Not intended for modifying server side state.
     *
     * @param player player
     * @param entityLike new target
     */
    public abstract void setCamera(Player player, EntityLike entityLike);

    /**
     * Resets camera target for given player.
     *
     * @param player player to reset the camera for
     */
    public void resetCamera(Player player) {
        setCamera(player, EntityLike.fromBukkit(player));
    }

    /**
     * Opens horse inventory for the player.
     *
     * @param player player to open the inventory for
     * @param inventory inventory to open
     */
    public abstract void openHorseInventory(Player player, AbstractHorseInventory inventory);

    /**
     * Modifies the field of view, like a speed potion. Defaults to 0.1 for players.
     *
     * @param player player
     * @param fov new fov
     */
    public abstract void setFOVModifier(Player player, float fov);

    /**
     * Plays block action for player on given block.
     * <p>
     * Should only be used for packet/clientside related stuff.
     * Not intended for modifying server side state.
     *
     * @param player player
     * @param block block
     * @param blockAction block action to play
     */
    @SuppressWarnings("UnstableApiUsage")
    public void playBlockAction(Player player, Block block, BlockAction blockAction) {
        playBlockAction(player, block.getLocation().toBlock(), block.getType(), blockAction);
    }

    /**
     * Plays block action for player on given block.
     * <p>
     * Should only be used for packet/clientside related stuff.
     * Not intended for modifying server side state.
     *
     * @param player player
     * @param position position of the block
     * @param block material of the block
     * @param blockAction block action to play
     */
    @SuppressWarnings("UnstableApiUsage")
    public abstract void playBlockAction(Player player, BlockPosition position, Material block, BlockAction blockAction);

    /**
     * Refreshes the entity for a player.
     * <p>
     * This can not be used for refreshing player itself, for
     * that use {@link #refreshPlayer(Player)}.
     *
     * @param player player to refresh the entity for
     * @param entity entity to refresh
     */
    public void refreshEntity(Player player, Entity entity) {
        Preconditions.checkNotNull(player, "Player can not be null");
        Preconditions.checkNotNull(entity, "Entity can not be null");
        if (player.equals(entity)) return;
        player.hideEntity(OwnerPlugin.get(), entity);
        player.showEntity(OwnerPlugin.get(), entity);
    }

    /**
     * Refreshes the player for all players online and themselves.
     *
     * @param player player to refresh
     */
    public abstract void refreshPlayer(Player player);

    /**
     * Shows terrain loading screen for the player.
     * <p>
     * This results in unexpected behaviour on the client-side, it is required
     * to use {@link #refreshPlayer(Player)} to sync up the client with the server
     * after closing the loading screen.
     * <p>
     * If the screen is not closed using {@link Player#closeInventory()}, client
     * automatically closes the screen after 30 seconds.
     *
     * @param player player to display the loading screen for
     */
    @ApiStatus.Experimental
    public abstract void showLoadingScreen(Player player);

    /**
     * Switches player to configuration state.
     *
     * @param player player
     * @see #switchToPlay(Player)
     */
    public abstract void switchToConfiguration(Player player);

    /**
     * Switches the player to play state.
     *
     * @param player player
     * @see #switchToConfiguration(Player)
     */
    public abstract void switchToPlay(Player player);

    /**
     * Resends configuration to the player.
     * <p>
     * To receive a configuration, player has to be in a configuration state.
     * <p>
     * This operation will return player back to play state.
     *
     * @param player player
     * @see #switchToConfiguration(Player)
     */
    public abstract void resendConfigurations(Player player);

    @Override
    public Class<PlayerActionService> getRegistrationClass() {
        return PlayerActionService.class;
    }

}
