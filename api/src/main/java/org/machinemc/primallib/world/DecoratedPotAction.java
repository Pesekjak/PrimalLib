package org.machinemc.primallib.world;

import com.google.common.base.Preconditions;
import lombok.With;
import org.bukkit.Material;

/**
 * Block action used to play the wobble animation of decorated pot when
 * inserting new items.
 *
 * @param style wobble animation style
 */
@With
public record DecoratedPotAction(WobbleStyle style) implements BlockAction {

    public DecoratedPotAction {
        Preconditions.checkNotNull(style, "Wobble style can not be null");
    }

    public DecoratedPotAction(boolean positive) {
        this(positive ? WobbleStyle.POSITIVE : WobbleStyle.NEGATIVE);
    }

    @Override
    public Material[] supports() {
        return new Material[] {Material.DECORATED_POT};
    }

    /**
     * Wobble style of the decorated pot animation.
     */
    public enum WobbleStyle {

        /**
         * Used when the item insertion is positive.
         */
        POSITIVE,

        /**
         * Used when the item insertion is negative.
         */
        NEGATIVE

    }

}
