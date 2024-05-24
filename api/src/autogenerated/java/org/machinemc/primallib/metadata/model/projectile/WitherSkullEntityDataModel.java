package org.machinemc.primallib.metadata.model.projectile;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link WitherSkullEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface WitherSkullEntityDataModel extends ExplosiveProjectileEntityDataModel {
    /**
     * Metadata field for CHARGED state of the entity
     */
    EntityData.Field<Boolean> CHARGED = Initializer.CHARGED;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> CHARGED;

        static {
            CHARGED = new EntityData.Field<>(8, Serializer.BOOLEAN);
        }
    }
}
