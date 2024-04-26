package org.machinemc.primallib.metadata.model.decoration;

import java.lang.Byte;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link TextDisplayEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface TextDisplayEntity extends DisplayEntity {
    /**
     * Metadata field for TEXT state of the entity
     */
    EntityData.Field<Component> TEXT = Initializer.TEXT;

    /**
     * Metadata field for LINE_WIDTH state of the entity
     */
    EntityData.Field<Integer> LINE_WIDTH = Initializer.LINE_WIDTH;

    /**
     * Metadata field for BACKGROUND state of the entity
     */
    EntityData.Field<Integer> BACKGROUND = Initializer.BACKGROUND;

    /**
     * Metadata field for TEXT_OPACITY state of the entity
     */
    EntityData.Field<Byte> TEXT_OPACITY = Initializer.TEXT_OPACITY;

    /**
     * Metadata field for TEXT_DISPLAY_FLAGS state of the entity
     */
    EntityData.Field<Byte> TEXT_DISPLAY_FLAGS = Initializer.TEXT_DISPLAY_FLAGS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Component> TEXT;

        static final EntityData.Field<Integer> LINE_WIDTH;

        static final EntityData.Field<Integer> BACKGROUND;

        static final EntityData.Field<Byte> TEXT_OPACITY;

        static final EntityData.Field<Byte> TEXT_DISPLAY_FLAGS;

        static {
            TEXT = new EntityData.Field<>(23, Serializer.COMPONENT);
            LINE_WIDTH = new EntityData.Field<>(24, Serializer.INT);
            BACKGROUND = new EntityData.Field<>(25, Serializer.INT);
            TEXT_OPACITY = new EntityData.Field<>(26, Serializer.BYTE);
            TEXT_DISPLAY_FLAGS = new EntityData.Field<>(27, Serializer.BYTE);
        }
    }
}
