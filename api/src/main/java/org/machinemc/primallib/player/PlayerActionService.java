package org.machinemc.primallib.player;

import com.google.common.base.Preconditions;
import io.papermc.paper.math.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.util.OwnerPlugin;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;
import org.machinemc.primallib.world.BlockAction;

import java.util.Map;

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
        Preconditions.checkNotNull(provider, "Player action service is not supported on " + MinecraftVersion.get() + " server");
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
     * Updates active skin parts of the player.
     *
     * @param skinParts skin parts
     */
    public abstract void setActiveSkinParts(Player player, SkinPart... skinParts);

    /**
     * Returns active skin parts of the player.
     *
     * @param player player
     * @return active skin parts
     */
    public abstract SkinPart[] getActiveSkinParts(Player player);

    /**
     * Changes camera spectator target of player to another entity.
     * <p>
     * Should only be used for packet/clientside related stuff.
     * Not intended for modifying server side state.
     *
     * @param player player
     * @param target new target
     */
    public abstract void setCamera(Player player, EntityLike target);

    /**
     * Resets camera target for given player.
     *
     * @param player player to reset the camera for
     */
    public void resetCamera(Player player) {
        setCamera(player, EntityLike.fromBukkit(player));
    }

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
     * Updates crash properties that will be print to client's crash log in case
     * of a game error.
     *
     * <p> Max length of each key is 128.
     * <p> Max length of each value is 4096;
     * <p> Max number of entries is 32
     *
     * @param player player
     * @param properties properties
     */
    public abstract void setCrashProperties(Player player, Map<String, String> properties);

    @Override
    public Class<PlayerActionService> getRegistrationClass() {
        return PlayerActionService.class;
    }

}
