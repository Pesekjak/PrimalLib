package org.machinemc.primallib.scoreboard;

import com.google.common.base.Preconditions;
import io.papermc.paper.scoreboard.numbers.NumberFormat;
import lombok.With;
import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.RenderType;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.entity.EntityLike;

/**
 * Represents a scoreboard objective.
 *
 * @param name name of the objective
 * @param displayName display name of the objective
 * @param renderType render type of the objective
 * @param numberFormat number format of the objective, determines how the score number should be formatted
 */
@With
public record Objective(String name, @Nullable Component displayName, RenderType renderType, NumberFormat numberFormat) {

    public Objective {
        Preconditions.checkNotNull(name, "Name can not be null");
        Preconditions.checkNotNull(renderType, "Render type can not be null");
        Preconditions.checkNotNull(numberFormat, "Number format can not be null");
    }

    public Objective(String name, Component displayName, RenderType renderType) {
        this(name, displayName, renderType, NumberFormat.noStyle());
    }

    public Objective(String name, Component displayName) {
        this(name, displayName, RenderType.INTEGER);
    }

    public Objective(String name) {
        this(name, null);
    }

    /**
     * Represents an entry of an objective with assigned score.
     *
     * @param source entry source
     * @param score score value
     * @param displayName display name of the entry
     * @param numberFormat Determines how the score number should be formatted,
     *                     this overrides the number format set on the objective, if any.
     */
    @With
    public record Entry(EntityLike source, int score, @Nullable Component displayName, @Nullable NumberFormat numberFormat) {

        public Entry {
            Preconditions.checkNotNull(source, "Source of objective entry can not be null");
        }

        public Entry(EntityLike source, int score) {
            this(source, score, null, null);
        }

    }

}
