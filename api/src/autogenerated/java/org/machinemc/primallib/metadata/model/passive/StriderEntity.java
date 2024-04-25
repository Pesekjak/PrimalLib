package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link StriderEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface StriderEntity extends AnimalEntity {
    /**
     * Metadata field for BOOST_TIME state of the entity
     */
    EntityData.Field<Integer> BOOST_TIME = Initializer.BOOST_TIME;

    /**
     * Metadata field for COLD state of the entity
     */
    EntityData.Field<Boolean> COLD = Initializer.COLD;

    /**
     * Metadata field for SADDLED state of the entity
     */
    EntityData.Field<Boolean> SADDLED = Initializer.SADDLED;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> BOOST_TIME;

        static final EntityData.Field<Boolean> COLD;

        static final EntityData.Field<Boolean> SADDLED;

        static {
            BOOST_TIME = new EntityData.Field<>(17, Serializer.INT);
            COLD = new EntityData.Field<>(18, Serializer.BOOLEAN);
            SADDLED = new EntityData.Field<>(19, Serializer.BOOLEAN);
        }
    }
}
