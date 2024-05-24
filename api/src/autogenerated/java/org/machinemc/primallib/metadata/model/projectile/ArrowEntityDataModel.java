package org.machinemc.primallib.metadata.model.projectile;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ArrowEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ArrowEntityDataModel extends PersistentProjectileEntityDataModel {
    /**
     * Metadata field for COLOR state of the entity
     */
    EntityData.Field<Integer> COLOR = Initializer.COLOR;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> COLOR;

        static {
            COLOR = new EntityData.Field<>(10, Serializer.INT);
        }
    }
}
