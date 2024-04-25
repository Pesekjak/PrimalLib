package org.machinemc.primallib.metadata.model;

import java.lang.Boolean;
import java.lang.Byte;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Pose;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link Entity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface Entity {
    /**
     * Metadata field for FLAGS state of the entity
     */
    EntityData.Field<Byte> FLAGS = Initializer.FLAGS;

    /**
     * Metadata field for AIR state of the entity
     */
    EntityData.Field<Integer> AIR = Initializer.AIR;

    /**
     * Metadata field for CUSTOM_NAME state of the entity
     */
    EntityData.Field<Component> CUSTOM_NAME = Initializer.CUSTOM_NAME;

    /**
     * Metadata field for NAME_VISIBLE state of the entity
     */
    EntityData.Field<Boolean> NAME_VISIBLE = Initializer.NAME_VISIBLE;

    /**
     * Metadata field for SILENT state of the entity
     */
    EntityData.Field<Boolean> SILENT = Initializer.SILENT;

    /**
     * Metadata field for NO_GRAVITY state of the entity
     */
    EntityData.Field<Boolean> NO_GRAVITY = Initializer.NO_GRAVITY;

    /**
     * Metadata field for POSE state of the entity
     */
    EntityData.Field<Pose> POSE = Initializer.POSE;

    /**
     * Metadata field for FROZEN_TICKS state of the entity
     */
    EntityData.Field<Integer> FROZEN_TICKS = Initializer.FROZEN_TICKS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> FLAGS;

        static final EntityData.Field<Integer> AIR;

        static final EntityData.Field<Component> CUSTOM_NAME;

        static final EntityData.Field<Boolean> NAME_VISIBLE;

        static final EntityData.Field<Boolean> SILENT;

        static final EntityData.Field<Boolean> NO_GRAVITY;

        static final EntityData.Field<Pose> POSE;

        static final EntityData.Field<Integer> FROZEN_TICKS;

        static {
            FLAGS = new EntityData.Field<>(0, Serializer.BYTE);
            AIR = new EntityData.Field<>(1, Serializer.INT);
            CUSTOM_NAME = new EntityData.Field<>(2, Serializer.OPTIONAL_COMPONENT);
            NAME_VISIBLE = new EntityData.Field<>(3, Serializer.BOOLEAN);
            SILENT = new EntityData.Field<>(4, Serializer.BOOLEAN);
            NO_GRAVITY = new EntityData.Field<>(5, Serializer.BOOLEAN);
            POSE = new EntityData.Field<>(6, Serializer.POSE);
            FROZEN_TICKS = new EntityData.Field<>(7, Serializer.INT);
        }
    }
}
