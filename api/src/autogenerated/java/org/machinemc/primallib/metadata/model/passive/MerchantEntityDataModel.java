package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link MerchantEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface MerchantEntityDataModel extends PassiveEntityDataModel {
    /**
     * Metadata field for HEAD_ROLLING_TIME_LEFT state of the entity
     */
    EntityData.Field<Integer> HEAD_ROLLING_TIME_LEFT = Initializer.HEAD_ROLLING_TIME_LEFT;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> HEAD_ROLLING_TIME_LEFT;

        static {
            HEAD_ROLLING_TIME_LEFT = new EntityData.Field<>(17, Serializer.INT);
        }
    }
}
