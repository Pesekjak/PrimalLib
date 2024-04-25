package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.bukkit.entity.Cat;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link CatEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface CatEntity extends TameableEntity {
    /**
     * Metadata field for CAT_VARIANT state of the entity
     */
    EntityData.Field<Cat.Type> CAT_VARIANT = Initializer.CAT_VARIANT;

    /**
     * Metadata field for IN_SLEEPING_POSE state of the entity
     */
    EntityData.Field<Boolean> IN_SLEEPING_POSE = Initializer.IN_SLEEPING_POSE;

    /**
     * Metadata field for HEAD_DOWN state of the entity
     */
    EntityData.Field<Boolean> HEAD_DOWN = Initializer.HEAD_DOWN;

    /**
     * Metadata field for COLLAR_COLOR state of the entity
     */
    EntityData.Field<Integer> COLLAR_COLOR = Initializer.COLLAR_COLOR;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Cat.Type> CAT_VARIANT;

        static final EntityData.Field<Boolean> IN_SLEEPING_POSE;

        static final EntityData.Field<Boolean> HEAD_DOWN;

        static final EntityData.Field<Integer> COLLAR_COLOR;

        static {
            CAT_VARIANT = new EntityData.Field<>(19, Serializer.CAT_VARIANT);
            IN_SLEEPING_POSE = new EntityData.Field<>(20, Serializer.BOOLEAN);
            HEAD_DOWN = new EntityData.Field<>(21, Serializer.BOOLEAN);
            COLLAR_COLOR = new EntityData.Field<>(22, Serializer.INT);
        }
    }
}
