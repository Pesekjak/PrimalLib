package org.machinemc.primallib.metadata.model.projectile;

import java.lang.Boolean;
import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link TridentEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface TridentEntityDataModel extends PersistentProjectileEntityDataModel {
    /**
     * Metadata field for LOYALTY state of the entity
     */
    EntityData.Field<Byte> LOYALTY = Initializer.LOYALTY;

    /**
     * Metadata field for ENCHANTED state of the entity
     */
    EntityData.Field<Boolean> ENCHANTED = Initializer.ENCHANTED;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> LOYALTY;

        static final EntityData.Field<Boolean> ENCHANTED;

        static {
            LOYALTY = new EntityData.Field<>(10, Serializer.BYTE);
            ENCHANTED = new EntityData.Field<>(11, Serializer.BOOLEAN);
        }
    }
}
