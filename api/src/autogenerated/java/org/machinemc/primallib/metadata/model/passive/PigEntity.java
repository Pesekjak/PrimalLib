package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link PigEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PigEntity extends AnimalEntity {
    /**
     * Metadata field for SADDLED state of the entity
     */
    EntityData.Field<Boolean> SADDLED = Initializer.SADDLED;

    /**
     * Metadata field for BOOST_TIME state of the entity
     */
    EntityData.Field<Integer> BOOST_TIME = Initializer.BOOST_TIME;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> SADDLED;

        static final EntityData.Field<Integer> BOOST_TIME;

        static {
            SADDLED = new EntityData.Field<>(17, Serializer.BOOLEAN);
            BOOST_TIME = new EntityData.Field<>(18, Serializer.INT);
        }
    }
}
