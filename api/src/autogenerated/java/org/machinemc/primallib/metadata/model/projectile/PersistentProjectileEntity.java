package org.machinemc.primallib.metadata.model.projectile;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link PersistentProjectileEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PersistentProjectileEntity extends ProjectileEntity {
    /**
     * Metadata field for PROJECTILE_FLAGS state of the entity
     */
    EntityData.Field<Byte> PROJECTILE_FLAGS = Initializer.PROJECTILE_FLAGS;

    /**
     * Metadata field for PIERCE_LEVEL state of the entity
     */
    EntityData.Field<Byte> PIERCE_LEVEL = Initializer.PIERCE_LEVEL;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> PROJECTILE_FLAGS;

        static final EntityData.Field<Byte> PIERCE_LEVEL;

        static {
            PROJECTILE_FLAGS = new EntityData.Field<>(8, Serializer.BYTE);
            PIERCE_LEVEL = new EntityData.Field<>(9, Serializer.BYTE);
        }
    }
}
