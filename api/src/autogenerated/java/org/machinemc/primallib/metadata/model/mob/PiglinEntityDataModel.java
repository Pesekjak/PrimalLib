package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link PiglinEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PiglinEntityDataModel extends AbstractPiglinEntityDataModel {
    /**
     * Metadata field for BABY state of the entity
     */
    EntityData.Field<Boolean> BABY = Initializer.BABY;

    /**
     * Metadata field for CHARGING state of the entity
     */
    EntityData.Field<Boolean> CHARGING = Initializer.CHARGING;

    /**
     * Metadata field for DANCING state of the entity
     */
    EntityData.Field<Boolean> DANCING = Initializer.DANCING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> BABY;

        static final EntityData.Field<Boolean> CHARGING;

        static final EntityData.Field<Boolean> DANCING;

        static {
            BABY = new EntityData.Field<>(17, Serializer.BOOLEAN);
            CHARGING = new EntityData.Field<>(18, Serializer.BOOLEAN);
            DANCING = new EntityData.Field<>(19, Serializer.BOOLEAN);
        }
    }
}
