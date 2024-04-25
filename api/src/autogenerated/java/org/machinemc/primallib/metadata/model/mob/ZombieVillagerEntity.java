package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.entity.VillagerData;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ZombieVillagerEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ZombieVillagerEntity extends ZombieEntity {
    /**
     * Metadata field for CONVERTING state of the entity
     */
    EntityData.Field<Boolean> CONVERTING = Initializer.CONVERTING;

    /**
     * Metadata field for VILLAGER_DATA state of the entity
     */
    EntityData.Field<VillagerData> VILLAGER_DATA = Initializer.VILLAGER_DATA;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> CONVERTING;

        static final EntityData.Field<VillagerData> VILLAGER_DATA;

        static {
            CONVERTING = new EntityData.Field<>(19, Serializer.BOOLEAN);
            VILLAGER_DATA = new EntityData.Field<>(20, Serializer.VILLAGER_DATA);
        }
    }
}
