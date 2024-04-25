package org.machinemc.primallib.metadata.model;

import io.papermc.paper.math.Position;
import java.lang.Boolean;
import java.lang.Byte;
import java.lang.Float;
import java.lang.Integer;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link LivingEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface LivingEntity extends Entity {
    /**
     * Metadata field for LIVING_FLAGS state of the entity
     */
    EntityData.Field<Byte> LIVING_FLAGS = Initializer.LIVING_FLAGS;

    /**
     * Metadata field for HEALTH state of the entity
     */
    EntityData.Field<Float> HEALTH = Initializer.HEALTH;

    /**
     * Metadata field for POTION_SWIRLS_COLOR state of the entity
     */
    EntityData.Field<Integer> POTION_SWIRLS_COLOR = Initializer.POTION_SWIRLS_COLOR;

    /**
     * Metadata field for POTION_SWIRLS_AMBIENT state of the entity
     */
    EntityData.Field<Boolean> POTION_SWIRLS_AMBIENT = Initializer.POTION_SWIRLS_AMBIENT;

    /**
     * Metadata field for STUCK_ARROW_COUNT state of the entity
     */
    EntityData.Field<Integer> STUCK_ARROW_COUNT = Initializer.STUCK_ARROW_COUNT;

    /**
     * Metadata field for STINGER_COUNT state of the entity
     */
    EntityData.Field<Integer> STINGER_COUNT = Initializer.STINGER_COUNT;

    /**
     * Metadata field for SLEEPING_POSITION state of the entity
     */
    EntityData.Field<Optional<Position>> SLEEPING_POSITION = Initializer.SLEEPING_POSITION;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> LIVING_FLAGS;

        static final EntityData.Field<Float> HEALTH;

        static final EntityData.Field<Integer> POTION_SWIRLS_COLOR;

        static final EntityData.Field<Boolean> POTION_SWIRLS_AMBIENT;

        static final EntityData.Field<Integer> STUCK_ARROW_COUNT;

        static final EntityData.Field<Integer> STINGER_COUNT;

        static final EntityData.Field<Optional<Position>> SLEEPING_POSITION;

        static {
            LIVING_FLAGS = new EntityData.Field<>(8, Serializer.BYTE);
            HEALTH = new EntityData.Field<>(9, Serializer.FLOAT);
            POTION_SWIRLS_COLOR = new EntityData.Field<>(10, Serializer.INT);
            POTION_SWIRLS_AMBIENT = new EntityData.Field<>(11, Serializer.BOOLEAN);
            STUCK_ARROW_COUNT = new EntityData.Field<>(12, Serializer.INT);
            STINGER_COUNT = new EntityData.Field<>(13, Serializer.INT);
            SLEEPING_POSITION = new EntityData.Field<>(14, Serializer.OPTIONAL_POSITION);
        }
    }
}
