package org.machinemc.primallib.metadata.model.vehicle;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link BoatEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface BoatEntityDataModel extends VehicleEntityDataModel {
    /**
     * Metadata field for BOAT_TYPE state of the entity
     */
    EntityData.Field<Integer> BOAT_TYPE = Initializer.BOAT_TYPE;

    /**
     * Metadata field for LEFT_PADDLE_MOVING state of the entity
     */
    EntityData.Field<Boolean> LEFT_PADDLE_MOVING = Initializer.LEFT_PADDLE_MOVING;

    /**
     * Metadata field for RIGHT_PADDLE_MOVING state of the entity
     */
    EntityData.Field<Boolean> RIGHT_PADDLE_MOVING = Initializer.RIGHT_PADDLE_MOVING;

    /**
     * Metadata field for BUBBLE_WOBBLE_TICKS state of the entity
     */
    EntityData.Field<Integer> BUBBLE_WOBBLE_TICKS = Initializer.BUBBLE_WOBBLE_TICKS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> BOAT_TYPE;

        static final EntityData.Field<Boolean> LEFT_PADDLE_MOVING;

        static final EntityData.Field<Boolean> RIGHT_PADDLE_MOVING;

        static final EntityData.Field<Integer> BUBBLE_WOBBLE_TICKS;

        static {
            BOAT_TYPE = new EntityData.Field<>(11, Serializer.INT);
            LEFT_PADDLE_MOVING = new EntityData.Field<>(12, Serializer.BOOLEAN);
            RIGHT_PADDLE_MOVING = new EntityData.Field<>(13, Serializer.BOOLEAN);
            BUBBLE_WOBBLE_TICKS = new EntityData.Field<>(14, Serializer.INT);
        }
    }
}
