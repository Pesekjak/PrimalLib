package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.mob.WaterCreatureEntity;

/**
 * Applicable metadata fields for all entities inheriting {@link FishEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface FishEntity extends WaterCreatureEntity {
    /**
     * Metadata field for FROM_BUCKET state of the entity
     */
    EntityData.Field<Boolean> FROM_BUCKET = Initializer.FROM_BUCKET;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> FROM_BUCKET;

        static {
            FROM_BUCKET = new EntityData.Field<>(16, Serializer.BOOLEAN);
        }
    }
}
