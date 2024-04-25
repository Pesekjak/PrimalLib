package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ZombieEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ZombieEntity extends HostileEntity {
    /**
     * Metadata field for BABY state of the entity
     */
    EntityData.Field<Boolean> BABY = Initializer.BABY;

    /**
     * Metadata field for ZOMBIE_TYPE state of the entity
     */
    EntityData.Field<Integer> ZOMBIE_TYPE = Initializer.ZOMBIE_TYPE;

    /**
     * Metadata field for CONVERTING_IN_WATER state of the entity
     */
    EntityData.Field<Boolean> CONVERTING_IN_WATER = Initializer.CONVERTING_IN_WATER;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> BABY;

        static final EntityData.Field<Integer> ZOMBIE_TYPE;

        static final EntityData.Field<Boolean> CONVERTING_IN_WATER;

        static {
            BABY = new EntityData.Field<>(16, Serializer.BOOLEAN);
            ZOMBIE_TYPE = new EntityData.Field<>(17, Serializer.INT);
            CONVERTING_IN_WATER = new EntityData.Field<>(18, Serializer.BOOLEAN);
        }
    }
}
