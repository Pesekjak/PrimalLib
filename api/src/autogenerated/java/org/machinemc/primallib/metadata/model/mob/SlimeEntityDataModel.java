package org.machinemc.primallib.metadata.model.mob;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link SlimeEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface SlimeEntityDataModel extends MobEntityDataModel {
    /**
     * Metadata field for SLIME_SIZE state of the entity
     */
    EntityData.Field<Integer> SLIME_SIZE = Initializer.SLIME_SIZE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> SLIME_SIZE;

        static {
            SLIME_SIZE = new EntityData.Field<>(16, Serializer.INT);
        }
    }
}
