package org.machinemc.primallib.metadata.model.boss.dragon;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.mob.MobEntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link EnderDragonEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface EnderDragonEntityDataModel extends MobEntityDataModel {
    /**
     * Metadata field for PHASE_TYPE state of the entity
     */
    EntityData.Field<Integer> PHASE_TYPE = Initializer.PHASE_TYPE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> PHASE_TYPE;

        static {
            PHASE_TYPE = new EntityData.Field<>(16, Serializer.INT);
        }
    }
}
