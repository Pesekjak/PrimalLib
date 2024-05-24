package org.machinemc.primallib.metadata.model.passive;

import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.entity.VillagerData;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link VillagerEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface VillagerEntityDataModel extends MerchantEntityDataModel {
    /**
     * Metadata field for VILLAGER_DATA state of the entity
     */
    EntityData.Field<VillagerData> VILLAGER_DATA = Initializer.VILLAGER_DATA;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<VillagerData> VILLAGER_DATA;

        static {
            VILLAGER_DATA = new EntityData.Field<>(18, Serializer.VILLAGER_DATA);
        }
    }
}
