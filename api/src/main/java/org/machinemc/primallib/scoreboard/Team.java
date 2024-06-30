package org.machinemc.primallib.scoreboard;

import com.google.common.base.Preconditions;
import lombok.With;
import net.kyori.adventure.text.Component;
import org.machinemc.primallib.util.LegacyColor;

/**
 * Represents a player team.
 *
 * @param name name of the team
 * @param displayName display name of the team
 * @param prefix name prefix for players in the team
 * @param suffix suffix for players in the team
 * @param nameTagVisibility visibility of name tags of players in the team
 * @param collisionRule collision rules of players in the team
 * @param color color of the team
 * @param allowFriendlyFire friendly fire rule
 * @param seeFriendlyInvisible friendly invisible rule
 */
@With
public record Team(String name,
                   Component displayName,
                   Component prefix,
                   Component suffix,
                   Visibility nameTagVisibility,
                   Collisions collisionRule,
                   LegacyColor color,
                   boolean allowFriendlyFire,
                   boolean seeFriendlyInvisible) {

    public Team {
        Preconditions.checkNotNull(name, "Team name can not be null");
        Preconditions.checkNotNull(displayName, "Display name can not be null");
        Preconditions.checkNotNull(prefix, "Prefix can not be null");
        Preconditions.checkNotNull(suffix, "Suffix can not be null");
        Preconditions.checkNotNull(nameTagVisibility, "Name tag visibility can not be null");
        Preconditions.checkNotNull(collisionRule, "Collision rule can not be null");
        Preconditions.checkNotNull(color, "Team color can not be null");
    }

    public Team(String name, Component displayName, LegacyColor color) {
        this(name, displayName, Component.empty(), Component.empty(), Visibility.ALWAYS, Collisions.ALWAYS, color, false, false);
    }

    /**
     * Represents a state for name tag visibility of teams.
     */
    public enum Visibility {

        /**
         * Always enabled.
         */
        ALWAYS,

        /**
         * Disabled for players in other teams.
         */
        HIDE_FOR_OTHER,

        /**
         * Disabled only for the players in the same team.
         */
        HIDE_FOR_OWN,

        /**
         * Always disabled.
         */
        NEVER

    }

    /**
     * Represents a state for collisions in teams.
     */
    public enum Collisions {

        /**
         * Always enabled.
         */
        ALWAYS,

        /**
         * Disabled only for the players in the team.
         */
        PUSH_OTHER_TEAMS,

        /**
         * Disabled for players in other teams.
         */
        PUSH_OWN_TEAM,

        /**
         * Always disabled.
         */
        NEVER

    }

}
