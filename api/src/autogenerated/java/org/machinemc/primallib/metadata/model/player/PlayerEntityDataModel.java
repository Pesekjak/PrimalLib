package org.machinemc.primallib.metadata.model.player;

import java.lang.Byte;
import java.lang.Float;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.LivingEntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link PlayerEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PlayerEntityDataModel extends LivingEntityDataModel {
    /**
     * Metadata field for ABSORPTION_AMOUNT state of the entity
     */
    EntityData.Field<Float> ABSORPTION_AMOUNT = Initializer.ABSORPTION_AMOUNT;

    /**
     * Metadata field for SCORE state of the entity
     */
    EntityData.Field<Integer> SCORE = Initializer.SCORE;

    /**
     * Metadata field for PLAYER_MODEL_PARTS state of the entity
     */
    EntityData.Field<Byte> PLAYER_MODEL_PARTS = Initializer.PLAYER_MODEL_PARTS;

    /**
     * Metadata field for MAIN_ARM state of the entity
     */
    EntityData.Field<Byte> MAIN_ARM = Initializer.MAIN_ARM;

    /**
     * Metadata field for LEFT_SHOULDER_ENTITY state of the entity
     */
    EntityData.Field<CompoundBinaryTag> LEFT_SHOULDER_ENTITY = Initializer.LEFT_SHOULDER_ENTITY;

    /**
     * Metadata field for RIGHT_SHOULDER_ENTITY state of the entity
     */
    EntityData.Field<CompoundBinaryTag> RIGHT_SHOULDER_ENTITY = Initializer.RIGHT_SHOULDER_ENTITY;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Float> ABSORPTION_AMOUNT;

        static final EntityData.Field<Integer> SCORE;

        static final EntityData.Field<Byte> PLAYER_MODEL_PARTS;

        static final EntityData.Field<Byte> MAIN_ARM;

        static final EntityData.Field<CompoundBinaryTag> LEFT_SHOULDER_ENTITY;

        static final EntityData.Field<CompoundBinaryTag> RIGHT_SHOULDER_ENTITY;

        static {
            ABSORPTION_AMOUNT = new EntityData.Field<>(15, Serializer.FLOAT);
            SCORE = new EntityData.Field<>(16, Serializer.INT);
            PLAYER_MODEL_PARTS = new EntityData.Field<>(17, Serializer.BYTE);
            MAIN_ARM = new EntityData.Field<>(18, Serializer.BYTE);
            LEFT_SHOULDER_ENTITY = new EntityData.Field<>(19, Serializer.NBT);
            RIGHT_SHOULDER_ENTITY = new EntityData.Field<>(20, Serializer.NBT);
        }
    }
}
