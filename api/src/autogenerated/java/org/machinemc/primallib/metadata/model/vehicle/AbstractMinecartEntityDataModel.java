package org.machinemc.primallib.metadata.model.vehicle;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link AbstractMinecartEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface AbstractMinecartEntityDataModel extends VehicleEntityDataModel {
    /**
     * Metadata field for CUSTOM_BLOCK_ID state of the entity
     */
    EntityData.Field<Integer> CUSTOM_BLOCK_ID = Initializer.CUSTOM_BLOCK_ID;

    /**
     * Metadata field for CUSTOM_BLOCK_OFFSET state of the entity
     */
    EntityData.Field<Integer> CUSTOM_BLOCK_OFFSET = Initializer.CUSTOM_BLOCK_OFFSET;

    /**
     * Metadata field for CUSTOM_BLOCK_PRESENT state of the entity
     */
    EntityData.Field<Boolean> CUSTOM_BLOCK_PRESENT = Initializer.CUSTOM_BLOCK_PRESENT;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> CUSTOM_BLOCK_ID;

        static final EntityData.Field<Integer> CUSTOM_BLOCK_OFFSET;

        static final EntityData.Field<Boolean> CUSTOM_BLOCK_PRESENT;

        static {
            CUSTOM_BLOCK_ID = new EntityData.Field<>(11, Serializer.INT);
            CUSTOM_BLOCK_OFFSET = new EntityData.Field<>(12, Serializer.INT);
            CUSTOM_BLOCK_PRESENT = new EntityData.Field<>(13, Serializer.BOOLEAN);
        }
    }
}
