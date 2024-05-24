package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.raid.RaiderEntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link WitchEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface WitchEntityDataModel extends RaiderEntityDataModel {
    /**
     * Metadata field for DRINKING state of the entity
     */
    EntityData.Field<Boolean> DRINKING = Initializer.DRINKING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> DRINKING;

        static {
            DRINKING = new EntityData.Field<>(17, Serializer.BOOLEAN);
        }
    }
}
