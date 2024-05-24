package org.machinemc.primallib.metadata.model.passive;

import java.lang.Byte;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link PandaEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PandaEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for ASK_FOR_BAMBOO_TICKS state of the entity
     */
    EntityData.Field<Integer> ASK_FOR_BAMBOO_TICKS = Initializer.ASK_FOR_BAMBOO_TICKS;

    /**
     * Metadata field for SNEEZE_PROGRESS state of the entity
     */
    EntityData.Field<Integer> SNEEZE_PROGRESS = Initializer.SNEEZE_PROGRESS;

    /**
     * Metadata field for EATING_TICKS state of the entity
     */
    EntityData.Field<Integer> EATING_TICKS = Initializer.EATING_TICKS;

    /**
     * Metadata field for MAIN_GENE state of the entity
     */
    EntityData.Field<Byte> MAIN_GENE = Initializer.MAIN_GENE;

    /**
     * Metadata field for HIDDEN_GENE state of the entity
     */
    EntityData.Field<Byte> HIDDEN_GENE = Initializer.HIDDEN_GENE;

    /**
     * Metadata field for PANDA_FLAGS state of the entity
     */
    EntityData.Field<Byte> PANDA_FLAGS = Initializer.PANDA_FLAGS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> ASK_FOR_BAMBOO_TICKS;

        static final EntityData.Field<Integer> SNEEZE_PROGRESS;

        static final EntityData.Field<Integer> EATING_TICKS;

        static final EntityData.Field<Byte> MAIN_GENE;

        static final EntityData.Field<Byte> HIDDEN_GENE;

        static final EntityData.Field<Byte> PANDA_FLAGS;

        static {
            ASK_FOR_BAMBOO_TICKS = new EntityData.Field<>(17, Serializer.INT);
            SNEEZE_PROGRESS = new EntityData.Field<>(18, Serializer.INT);
            EATING_TICKS = new EntityData.Field<>(19, Serializer.INT);
            MAIN_GENE = new EntityData.Field<>(20, Serializer.BYTE);
            HIDDEN_GENE = new EntityData.Field<>(21, Serializer.BYTE);
            PANDA_FLAGS = new EntityData.Field<>(22, Serializer.BYTE);
        }
    }
}
