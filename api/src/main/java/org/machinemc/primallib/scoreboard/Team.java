package org.machinemc.primallib.scoreboard;

import com.google.common.base.Preconditions;
import lombok.With;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.TriState;
import org.machinemc.primallib.util.LegacyColor;

import java.util.Optional;

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
                   RuleState nameTagVisibility,
                   RuleState collisionRule,
                   LegacyColor color,
                   TriState allowFriendlyFire,
                   TriState seeFriendlyInvisible) {

    public Team {
        Preconditions.checkNotNull(name, "Team name can not be null");
        Preconditions.checkNotNull(displayName, "Display name can not be null");
        Preconditions.checkNotNull(prefix, "Prefix can not be null");
        Preconditions.checkNotNull(suffix, "Suffix can not be null");
        Preconditions.checkNotNull(nameTagVisibility, "Name tag visibility can not be null");
        Preconditions.checkNotNull(collisionRule, "Collision rule can not be null");
        Preconditions.checkNotNull(color, "Team color can not be null");
        Preconditions.checkNotNull(allowFriendlyFire, "Friendly fire rule can not be null");
        Preconditions.checkNotNull(seeFriendlyInvisible, "Friendly invisible rule can not be null");
    }

    public Team(String name, Component displayName, LegacyColor color) {
        this(name, displayName, Component.empty(), Component.empty(), RuleState.getDefault(), RuleState.getDefault(), color, TriState.NOT_SET, TriState.NOT_SET);
    }

    /**
     * Represents a state for name tag visibility and collision rules of teams.
     */
    public enum RuleState {

        /**
         * Always enabled.
         */
        ALWAYS("always"),

        /**
         * Disabled for players in other teams.
         */
        HIDE_FOR_OTHER("hideForOtherTeams"),

        /**
         * Disabled only for the players in the team.
         */
        HIDE_FOR_OWN("hideForOwnTeam"),

        /**
         * Always disabled.
         */
        NEVER("never");

        private final String state;

        RuleState(String state) {
            this.state = state;
        }

        /**
         * Returns rule state from its string representation or
         * empty if the provided string has no valid rule state.
         *
         * @param stateName string representation
         * @return rule state
         */
        public static Optional<RuleState> fromStateName(String stateName) {
            for (RuleState state : values()) {
                if (state.state.equals(stateName)) return Optional.of(state);
            }
            return Optional.empty();
        }

        /**
         * Returns the default rule state.
         *
         * @return default rule state
         */
        public static RuleState getDefault() {
            return ALWAYS;
        }

        /**
         * Returns the string representation of the rule state.
         *
         * @return string representation
         */
        public String getStateName() {
            return state;
        }

    }

}
