package org.machinemc.primallib.metadata.model.decoration.painting;

import javax.annotation.processing.Generated;
import org.bukkit.Art;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.decoration.AbstractDecorationEntity;

/**
 * Applicable metadata fields for all entities inheriting {@link PaintingEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PaintingEntity extends AbstractDecorationEntity {
    /**
     * Metadata field for VARIANT state of the entity
     */
    EntityData.Field<Art> VARIANT = Initializer.VARIANT;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Art> VARIANT;

        static {
            VARIANT = new EntityData.Field<>(8, Serializer.PAINTING_VARIANT);
        }
    }
}
