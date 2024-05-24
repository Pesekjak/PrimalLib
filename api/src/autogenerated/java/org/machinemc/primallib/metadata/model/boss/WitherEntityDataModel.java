package org.machinemc.primallib.metadata.model.boss;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.mob.HostileEntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link WitherEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface WitherEntityDataModel extends HostileEntityDataModel {
    /**
     * Metadata field for TRACKED_ENTITY_ID_1 state of the entity
     */
    EntityData.Field<Integer> TRACKED_ENTITY_ID_1 = Initializer.TRACKED_ENTITY_ID_1;

    /**
     * Metadata field for TRACKED_ENTITY_ID_2 state of the entity
     */
    EntityData.Field<Integer> TRACKED_ENTITY_ID_2 = Initializer.TRACKED_ENTITY_ID_2;

    /**
     * Metadata field for TRACKED_ENTITY_ID_3 state of the entity
     */
    EntityData.Field<Integer> TRACKED_ENTITY_ID_3 = Initializer.TRACKED_ENTITY_ID_3;

    /**
     * Metadata field for INVUL_TIMER state of the entity
     */
    EntityData.Field<Integer> INVUL_TIMER = Initializer.INVUL_TIMER;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> TRACKED_ENTITY_ID_1;

        static final EntityData.Field<Integer> TRACKED_ENTITY_ID_2;

        static final EntityData.Field<Integer> TRACKED_ENTITY_ID_3;

        static final EntityData.Field<Integer> INVUL_TIMER;

        static {
            TRACKED_ENTITY_ID_1 = new EntityData.Field<>(16, Serializer.INT);
            TRACKED_ENTITY_ID_2 = new EntityData.Field<>(17, Serializer.INT);
            TRACKED_ENTITY_ID_3 = new EntityData.Field<>(18, Serializer.INT);
            INVUL_TIMER = new EntityData.Field<>(19, Serializer.INT);
        }
    }
}
