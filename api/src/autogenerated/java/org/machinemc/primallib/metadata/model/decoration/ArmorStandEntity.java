package org.machinemc.primallib.metadata.model.decoration;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.LivingEntity;

/**
 * Applicable metadata fields for all entities inheriting {@link ArmorStandEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ArmorStandEntity extends LivingEntity {
    /**
     * Metadata field for ARMOR_STAND_FLAGS state of the entity
     */
    EntityData.Field<Byte> ARMOR_STAND_FLAGS = Initializer.ARMOR_STAND_FLAGS;

    /**
     * Metadata field for TRACKER_HEAD_ROTATION state of the entity
     */
    EntityData.Field<EulerAngle> TRACKER_HEAD_ROTATION = Initializer.TRACKER_HEAD_ROTATION;

    /**
     * Metadata field for TRACKER_BODY_ROTATION state of the entity
     */
    EntityData.Field<EulerAngle> TRACKER_BODY_ROTATION = Initializer.TRACKER_BODY_ROTATION;

    /**
     * Metadata field for TRACKER_LEFT_ARM_ROTATION state of the entity
     */
    EntityData.Field<EulerAngle> TRACKER_LEFT_ARM_ROTATION = Initializer.TRACKER_LEFT_ARM_ROTATION;

    /**
     * Metadata field for TRACKER_RIGHT_ARM_ROTATION state of the entity
     */
    EntityData.Field<EulerAngle> TRACKER_RIGHT_ARM_ROTATION = Initializer.TRACKER_RIGHT_ARM_ROTATION;

    /**
     * Metadata field for TRACKER_LEFT_LEG_ROTATION state of the entity
     */
    EntityData.Field<EulerAngle> TRACKER_LEFT_LEG_ROTATION = Initializer.TRACKER_LEFT_LEG_ROTATION;

    /**
     * Metadata field for TRACKER_RIGHT_LEG_ROTATION state of the entity
     */
    EntityData.Field<EulerAngle> TRACKER_RIGHT_LEG_ROTATION = Initializer.TRACKER_RIGHT_LEG_ROTATION;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> ARMOR_STAND_FLAGS;

        static final EntityData.Field<EulerAngle> TRACKER_HEAD_ROTATION;

        static final EntityData.Field<EulerAngle> TRACKER_BODY_ROTATION;

        static final EntityData.Field<EulerAngle> TRACKER_LEFT_ARM_ROTATION;

        static final EntityData.Field<EulerAngle> TRACKER_RIGHT_ARM_ROTATION;

        static final EntityData.Field<EulerAngle> TRACKER_LEFT_LEG_ROTATION;

        static final EntityData.Field<EulerAngle> TRACKER_RIGHT_LEG_ROTATION;

        static {
            ARMOR_STAND_FLAGS = new EntityData.Field<>(15, Serializer.BYTE);
            TRACKER_HEAD_ROTATION = new EntityData.Field<>(16, Serializer.ROTATIONS);
            TRACKER_BODY_ROTATION = new EntityData.Field<>(17, Serializer.ROTATIONS);
            TRACKER_LEFT_ARM_ROTATION = new EntityData.Field<>(18, Serializer.ROTATIONS);
            TRACKER_RIGHT_ARM_ROTATION = new EntityData.Field<>(19, Serializer.ROTATIONS);
            TRACKER_LEFT_LEG_ROTATION = new EntityData.Field<>(20, Serializer.ROTATIONS);
            TRACKER_RIGHT_LEG_ROTATION = new EntityData.Field<>(21, Serializer.ROTATIONS);
        }
    }
}
