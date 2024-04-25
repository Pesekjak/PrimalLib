package org.machinemc.primallib.metadata.model.decoration;

import java.lang.Byte;
import java.lang.Float;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.Entity;

/**
 * Applicable metadata fields for all entities inheriting {@link DisplayEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface DisplayEntity extends Entity {
    /**
     * Metadata field for START_INTERPOLATION state of the entity
     */
    EntityData.Field<Integer> START_INTERPOLATION = Initializer.START_INTERPOLATION;

    /**
     * Metadata field for INTERPOLATION_DURATION state of the entity
     */
    EntityData.Field<Integer> INTERPOLATION_DURATION = Initializer.INTERPOLATION_DURATION;

    /**
     * Metadata field for TELEPORT_DURATION state of the entity
     */
    EntityData.Field<Integer> TELEPORT_DURATION = Initializer.TELEPORT_DURATION;

    /**
     * Metadata field for TRANSLATION state of the entity
     */
    EntityData.Field<Vector3f> TRANSLATION = Initializer.TRANSLATION;

    /**
     * Metadata field for SCALE state of the entity
     */
    EntityData.Field<Vector3f> SCALE = Initializer.SCALE;

    /**
     * Metadata field for LEFT_ROTATION state of the entity
     */
    EntityData.Field<Quaternionf> LEFT_ROTATION = Initializer.LEFT_ROTATION;

    /**
     * Metadata field for RIGHT_ROTATION state of the entity
     */
    EntityData.Field<Quaternionf> RIGHT_ROTATION = Initializer.RIGHT_ROTATION;

    /**
     * Metadata field for BILLBOARD state of the entity
     */
    EntityData.Field<Byte> BILLBOARD = Initializer.BILLBOARD;

    /**
     * Metadata field for BRIGHTNESS state of the entity
     */
    EntityData.Field<Integer> BRIGHTNESS = Initializer.BRIGHTNESS;

    /**
     * Metadata field for VIEW_RANGE state of the entity
     */
    EntityData.Field<Float> VIEW_RANGE = Initializer.VIEW_RANGE;

    /**
     * Metadata field for SHADOW_RADIUS state of the entity
     */
    EntityData.Field<Float> SHADOW_RADIUS = Initializer.SHADOW_RADIUS;

    /**
     * Metadata field for SHADOW_STRENGTH state of the entity
     */
    EntityData.Field<Float> SHADOW_STRENGTH = Initializer.SHADOW_STRENGTH;

    /**
     * Metadata field for WIDTH state of the entity
     */
    EntityData.Field<Float> WIDTH = Initializer.WIDTH;

    /**
     * Metadata field for HEIGHT state of the entity
     */
    EntityData.Field<Float> HEIGHT = Initializer.HEIGHT;

    /**
     * Metadata field for GLOW_COLOR_OVERRIDE state of the entity
     */
    EntityData.Field<Integer> GLOW_COLOR_OVERRIDE = Initializer.GLOW_COLOR_OVERRIDE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> START_INTERPOLATION;

        static final EntityData.Field<Integer> INTERPOLATION_DURATION;

        static final EntityData.Field<Integer> TELEPORT_DURATION;

        static final EntityData.Field<Vector3f> TRANSLATION;

        static final EntityData.Field<Vector3f> SCALE;

        static final EntityData.Field<Quaternionf> LEFT_ROTATION;

        static final EntityData.Field<Quaternionf> RIGHT_ROTATION;

        static final EntityData.Field<Byte> BILLBOARD;

        static final EntityData.Field<Integer> BRIGHTNESS;

        static final EntityData.Field<Float> VIEW_RANGE;

        static final EntityData.Field<Float> SHADOW_RADIUS;

        static final EntityData.Field<Float> SHADOW_STRENGTH;

        static final EntityData.Field<Float> WIDTH;

        static final EntityData.Field<Float> HEIGHT;

        static final EntityData.Field<Integer> GLOW_COLOR_OVERRIDE;

        static {
            START_INTERPOLATION = new EntityData.Field<>(8, Serializer.INT);
            INTERPOLATION_DURATION = new EntityData.Field<>(9, Serializer.INT);
            TELEPORT_DURATION = new EntityData.Field<>(10, Serializer.INT);
            TRANSLATION = new EntityData.Field<>(11, Serializer.VECTOR3);
            SCALE = new EntityData.Field<>(12, Serializer.VECTOR3);
            LEFT_ROTATION = new EntityData.Field<>(13, Serializer.QUATERNION);
            RIGHT_ROTATION = new EntityData.Field<>(14, Serializer.QUATERNION);
            BILLBOARD = new EntityData.Field<>(15, Serializer.BYTE);
            BRIGHTNESS = new EntityData.Field<>(16, Serializer.INT);
            VIEW_RANGE = new EntityData.Field<>(17, Serializer.FLOAT);
            SHADOW_RADIUS = new EntityData.Field<>(18, Serializer.FLOAT);
            SHADOW_STRENGTH = new EntityData.Field<>(19, Serializer.FLOAT);
            WIDTH = new EntityData.Field<>(20, Serializer.FLOAT);
            HEIGHT = new EntityData.Field<>(21, Serializer.FLOAT);
            GLOW_COLOR_OVERRIDE = new EntityData.Field<>(22, Serializer.INT);
        }
    }
}
