package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link WolfEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface WolfEntity extends TameableEntity {
    /**
     * Metadata field for BEGGING state of the entity
     */
    EntityData.Field<Boolean> BEGGING = Initializer.BEGGING;

    /**
     * Metadata field for COLLAR_COLOR state of the entity
     */
    EntityData.Field<Integer> COLLAR_COLOR = Initializer.COLLAR_COLOR;

    /**
     * Metadata field for ANGER_TIME state of the entity
     */
    EntityData.Field<Integer> ANGER_TIME = Initializer.ANGER_TIME;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> BEGGING;

        static final EntityData.Field<Integer> COLLAR_COLOR;

        static final EntityData.Field<Integer> ANGER_TIME;

        static {
            BEGGING = new EntityData.Field<>(19, Serializer.BOOLEAN);
            COLLAR_COLOR = new EntityData.Field<>(20, Serializer.INT);
            ANGER_TIME = new EntityData.Field<>(21, Serializer.INT);
        }
    }
}
