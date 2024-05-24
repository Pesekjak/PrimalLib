package org.machinemc.primallib.metadata.model.vehicle;

import java.lang.Float;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.EntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link VehicleEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface VehicleEntityDataModel extends EntityDataModel {
    /**
     * Metadata field for DAMAGE_WOBBLE_TICKS state of the entity
     */
    EntityData.Field<Integer> DAMAGE_WOBBLE_TICKS = Initializer.DAMAGE_WOBBLE_TICKS;

    /**
     * Metadata field for DAMAGE_WOBBLE_SIDE state of the entity
     */
    EntityData.Field<Integer> DAMAGE_WOBBLE_SIDE = Initializer.DAMAGE_WOBBLE_SIDE;

    /**
     * Metadata field for DAMAGE_WOBBLE_STRENGTH state of the entity
     */
    EntityData.Field<Float> DAMAGE_WOBBLE_STRENGTH = Initializer.DAMAGE_WOBBLE_STRENGTH;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> DAMAGE_WOBBLE_TICKS;

        static final EntityData.Field<Integer> DAMAGE_WOBBLE_SIDE;

        static final EntityData.Field<Float> DAMAGE_WOBBLE_STRENGTH;

        static {
            DAMAGE_WOBBLE_TICKS = new EntityData.Field<>(8, Serializer.INT);
            DAMAGE_WOBBLE_SIDE = new EntityData.Field<>(9, Serializer.INT);
            DAMAGE_WOBBLE_STRENGTH = new EntityData.Field<>(10, Serializer.FLOAT);
        }
    }
}
