package org.machinemc.primallib.metadata.model.decoration;

import java.lang.Boolean;
import java.lang.Float;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.EntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link InteractionEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface InteractionEntityDataModel extends EntityDataModel {
    /**
     * Metadata field for WIDTH state of the entity
     */
    EntityData.Field<Float> WIDTH = Initializer.WIDTH;

    /**
     * Metadata field for HEIGHT state of the entity
     */
    EntityData.Field<Float> HEIGHT = Initializer.HEIGHT;

    /**
     * Metadata field for RESPONSE state of the entity
     */
    EntityData.Field<Boolean> RESPONSE = Initializer.RESPONSE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Float> WIDTH;

        static final EntityData.Field<Float> HEIGHT;

        static final EntityData.Field<Boolean> RESPONSE;

        static {
            WIDTH = new EntityData.Field<>(8, Serializer.FLOAT);
            HEIGHT = new EntityData.Field<>(9, Serializer.FLOAT);
            RESPONSE = new EntityData.Field<>(10, Serializer.BOOLEAN);
        }
    }
}
