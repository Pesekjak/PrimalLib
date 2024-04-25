package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link CreeperEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface CreeperEntity extends HostileEntity {
    /**
     * Metadata field for FUSE_SPEED state of the entity
     */
    EntityData.Field<Integer> FUSE_SPEED = Initializer.FUSE_SPEED;

    /**
     * Metadata field for CHARGED state of the entity
     */
    EntityData.Field<Boolean> CHARGED = Initializer.CHARGED;

    /**
     * Metadata field for IGNITED state of the entity
     */
    EntityData.Field<Boolean> IGNITED = Initializer.IGNITED;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> FUSE_SPEED;

        static final EntityData.Field<Boolean> CHARGED;

        static final EntityData.Field<Boolean> IGNITED;

        static {
            FUSE_SPEED = new EntityData.Field<>(16, Serializer.INT);
            CHARGED = new EntityData.Field<>(17, Serializer.BOOLEAN);
            IGNITED = new EntityData.Field<>(18, Serializer.BOOLEAN);
        }
    }
}
