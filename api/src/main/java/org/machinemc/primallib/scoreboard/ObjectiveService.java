package org.machinemc.primallib.scoreboard;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.Unmodifiable;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;

import java.util.*;

/**
 * Service for interacting with player objectives.
 */
@VersionDependant
public abstract class ObjectiveService extends AutoRegisteringService<ObjectiveService> {

    /**
     * Returns instance of objective service for currently running server.
     *
     * @return objective service
     */
    public static ObjectiveService get() {
        var provider = Bukkit.getServicesManager().getRegistration(ObjectiveService.class);
        Preconditions.checkNotNull(provider, "Objective service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Updates an entry of objective for given player.
     *
     * @param player player
     * @param objective objective
     * @param entry entry to update
     */
    public abstract void updateEntry(Player player, Objective objective, Objective.Entry entry);

    /**
     * Resets objective score of given target for given player.
     *
     * @param player player to see the changes
     * @param objective objective to reset
     * @param targets the targets for resetting the score
     */
    public abstract void resetScore(Player player, Objective objective, EntityLike... targets);

    /**
     * Resets objective score of given targets for given player.
     *
     * @param player player to see the changes
     * @param objective objective to reset
     * @param targets the targets for resetting the score
     */
    public void resetScore(Player player, Objective objective, Collection<EntityLike> targets) {
        resetScore(player, objective, targets.toArray(EntityLike[]::new));
    }

    /**
     * Resets objective score of given entries for given player.
     *
     * @param player player to see the changes
     * @param objective objective to reset
     * @param entries the entries for resetting the score
     */
    public void resetEntries(Player player, Objective objective, Objective.Entry... entries) {
        resetScore(player, objective, Arrays.stream(entries).map(Objective.Entry::source).toList());
    }

    /**
     * Resets objective score of given entries for given player.
     *
     * @param player player to see the changes
     * @param objective objective to reset
     * @param entries the entries for resetting the score
     */
    public void resetEntries(Player player, Objective objective, Collection<Objective.Entry> entries) {
        resetEntries(player, objective, entries.toArray(Objective.Entry[]::new));
    }

    /**
     * Changes the display slot of an objective for given player.
     *
     * @param player player
     * @param objective objective
     * @param slot new display slot
     */
    public abstract void displayObjective(Player player, Objective objective, DisplaySlot slot);

    /**
     * Creates new objective for the player.
     *
     * @param player player
     * @param objective objective to create
     */
    public abstract void createObjective(Player player, Objective objective);

    /**
     * Removes objective from the player.
     *
     * @param player player
     * @param name name of the objective to remove
     */
    public abstract void removeObjective(Player player, String name);

    /**
     * Removes objective from the player.
     *
     * @param player player
     * @param objective objective to remove
     */
    public void removeObjective(Player player, Objective objective) {
        removeObjective(player, objective.name());
    }

    /**
     * Updates objective (style and formatting) for the player.
     *
     * @param player player
     * @param objective objective to update
     */
    public abstract void updateObjective(Player player, Objective objective);

    /**
     * Returns all objectives added to a player.
     *
     * @param player player the get the objective from
     * @return objectives of the player
     */
    public abstract List<Objective> getObjectives(Player player);

    /**
     * Return objective with given name added to a player or empty if
     * there is none.
     *
     * @param player player
     * @param name name of objective
     * @return objective with given name added to the player
     */
    public abstract Optional<Objective> getObjective(Player player, String name);

    /**
     * Returns list of objective entries for given objective for player.
     *
     * @param player player
     * @param objective objective
     * @return entries of the objective for given player
     */
    public abstract @Unmodifiable List<Objective.Entry> getObjectiveEntries(Player player, Objective objective);

    @Override
    public Class<ObjectiveService> getRegistrationClass() {
        return ObjectiveService.class;
    }

}
