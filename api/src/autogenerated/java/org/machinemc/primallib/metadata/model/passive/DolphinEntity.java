package org.machinemc.primallib.metadata.model.passive;

import io.papermc.paper.math.Position;
import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.mob.WaterCreatureEntity;

/**
 * Applicable metadata fields for all entities inheriting {@link DolphinEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface DolphinEntity extends WaterCreatureEntity {
    /**
     * Metadata field for TREASURE_POS state of the entity
     */
    EntityData.Field<Position> TREASURE_POS = Initializer.TREASURE_POS;

    /**
     * Metadata field for HAS_FISH state of the entity
     */
    EntityData.Field<Boolean> HAS_FISH = Initializer.HAS_FISH;

    /**
     * Metadata field for MOISTNESS state of the entity
     */
    EntityData.Field<Integer> MOISTNESS = Initializer.MOISTNESS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Position> TREASURE_POS;

        static final EntityData.Field<Boolean> HAS_FISH;

        static final EntityData.Field<Integer> MOISTNESS;

        static {
            TREASURE_POS = new EntityData.Field<>(16, Serializer.POSITION);
            HAS_FISH = new EntityData.Field<>(17, Serializer.BOOLEAN);
            MOISTNESS = new EntityData.Field<>(18, Serializer.INT);
        }
    }
}
