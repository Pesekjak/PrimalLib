package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.bukkit.entity.Frog;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link FrogEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface FrogEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for VARIANT state of the entity
     */
    EntityData.Field<Frog.Variant> VARIANT = Initializer.VARIANT;

    /**
     * Metadata field for TARGET state of the entity
     */
    EntityData.Field<Optional<Integer>> TARGET = Initializer.TARGET;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Frog.Variant> VARIANT;

        static final EntityData.Field<Optional<Integer>> TARGET;

        static {
            VARIANT = new EntityData.Field<>(17, Serializer.FROG_VARIANT);
            TARGET = new EntityData.Field<>(18, Serializer.OPTIONAL_INT);
        }
    }
}
