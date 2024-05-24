package org.machinemc.primallib.metadata.model.mob;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.LivingEntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link MobEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface MobEntityDataModel extends LivingEntityDataModel {
    /**
     * Metadata field for MOB_FLAGS state of the entity
     */
    EntityData.Field<Byte> MOB_FLAGS = Initializer.MOB_FLAGS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> MOB_FLAGS;

        static {
            MOB_FLAGS = new EntityData.Field<>(15, Serializer.BYTE);
        }
    }
}
