package org.machinemc.primallib.metadata.model.mob;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link PhantomEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PhantomEntityDataModel extends FlyingEntityDataModel {
    /**
     * Metadata field for SIZE state of the entity
     */
    EntityData.Field<Integer> SIZE = Initializer.SIZE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> SIZE;

        static {
            SIZE = new EntityData.Field<>(16, Serializer.INT);
        }
    }
}
