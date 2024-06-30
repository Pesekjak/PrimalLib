package org.machinemc.primallib.scoreboard;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Unmodifiable;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;

import java.util.List;
import java.util.Optional;

/**
 * Service for interacting with player teams.
 */
@VersionDependant
public abstract class TeamService extends AutoRegisteringService<TeamService> {

    /**
     * Returns instance of teams service for currently running server.
     *
     * @return teams service
     */
    public static TeamService get() {
        var provider = Bukkit.getServicesManager().getRegistration(TeamService.class);
        Preconditions.checkNotNull(provider, "Team service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Creates new team for the player.
     *
     * @param player player to create the team for
     * @param team team to create
     */
    public abstract void createTeam(Player player, Team team);

    /**
     * Removes team from a player.
     *
     * @param player player to remove the team from
     * @param name name of the team
     */
    public abstract void removeTeam(Player player, String name);

    /**
     * Removes team from a player.
     *
     * @param player player to remove the team from
     * @param team team to remove
     */
    public void removeTeam(Player player, Team team) {
        removeTeam(player, team.name());
    }

    /**
     * Updates team properties for the player.
     *
     * @param player player to update the team for
     * @param team team with updated properties
     */
    public abstract void updateTeam(Player player, Team team);

    /**
     * Adds entities to the team.
     *
     * @param player player to update the team for
     * @param team team
     * @param entities entities to add
     */
    public abstract void addToTeam(Player player, Team team, EntityLike... entities);

    /**
     * Adds entities to the team.
     *
     * @param player player to update the team for
     * @param team team
     * @param entities entities to add
     */
    public void addToTeam(Player player, Team team, List<EntityLike> entities) {
        addToTeam(player, team, entities.toArray(new EntityLike[0]));
    }

    /**
     * Removes entities from the team.
     *
     * @param player player to update the team for
     * @param team team
     * @param entities entities to remove
     */
    public abstract void removeFromTeam(Player player, Team team, EntityLike... entities);

    /**
     * Removes entities from the team.
     *
     * @param player player to update the team for
     * @param team team
     * @param entities entities to remove
     */
    public void removeFromTeam(Player player, Team team, List<EntityLike> entities) {
        removeFromTeam(player, team, entities.toArray(new EntityLike[0]));
    }

    /**
     * Returns all teams added to a player.
     *
     * @param player player the get the teams from
     * @return teams of the player
     */
    public abstract List<Team> getTeams(Player player);

    /**
     * Return team with given name added to a player or empty if
     * there is none.
     *
     * @param player player
     * @param name name of team
     * @return team with given name added to the player
     */
    public abstract Optional<Team> getTeam(Player player, String name);

    /**
     * Returns list of team entries for given team for player.
     *
     * @param player player
     * @param team team
     * @return entries of the team for given player
     */
    public abstract @Unmodifiable List<EntityLike> getTeamEntries(Player player, Team team);

    @Override
    public Class<TeamService> getRegistrationClass() {
        return TeamService.class;
    }

}
