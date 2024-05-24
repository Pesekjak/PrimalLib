package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link GoatEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface GoatEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for SCREAMING state of the entity
     */
    EntityData.Field<Boolean> SCREAMING = Initializer.SCREAMING;

    /**
     * Metadata field for LEFT_HORN state of the entity
     */
    EntityData.Field<Boolean> LEFT_HORN = Initializer.LEFT_HORN;

    /**
     * Metadata field for RIGHT_HORN state of the entity
     */
    EntityData.Field<Boolean> RIGHT_HORN = Initializer.RIGHT_HORN;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> SCREAMING;

        static final EntityData.Field<Boolean> LEFT_HORN;

        static final EntityData.Field<Boolean> RIGHT_HORN;

        static {
            SCREAMING = new EntityData.Field<>(17, Serializer.BOOLEAN);
            LEFT_HORN = new EntityData.Field<>(18, Serializer.BOOLEAN);
            RIGHT_HORN = new EntityData.Field<>(19, Serializer.BOOLEAN);
        }
    }
}
