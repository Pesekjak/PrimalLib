package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.bukkit.entity.Sniffer;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link SnifferEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface SnifferEntity extends AnimalEntity {
    /**
     * Metadata field for STATE state of the entity
     */
    EntityData.Field<Sniffer.State> STATE = Initializer.STATE;

    /**
     * Metadata field for FINISH_DIG_TIME state of the entity
     */
    EntityData.Field<Integer> FINISH_DIG_TIME = Initializer.FINISH_DIG_TIME;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Sniffer.State> STATE;

        static final EntityData.Field<Integer> FINISH_DIG_TIME;

        static {
            STATE = new EntityData.Field<>(17, Serializer.SNIFFER_STATE);
            FINISH_DIG_TIME = new EntityData.Field<>(18, Serializer.INT);
        }
    }
}
