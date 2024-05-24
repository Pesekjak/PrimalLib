package org.machinemc.primallib.metadata.model.decoration;

import io.papermc.paper.math.Position;
import java.lang.Boolean;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.EntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link EndCrystalEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface EndCrystalEntityDataModel extends EntityDataModel {
    /**
     * Metadata field for BEAM_TARGET state of the entity
     */
    EntityData.Field<Optional<Position>> BEAM_TARGET = Initializer.BEAM_TARGET;

    /**
     * Metadata field for SHOW_BOTTOM state of the entity
     */
    EntityData.Field<Boolean> SHOW_BOTTOM = Initializer.SHOW_BOTTOM;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Optional<Position>> BEAM_TARGET;

        static final EntityData.Field<Boolean> SHOW_BOTTOM;

        static {
            BEAM_TARGET = new EntityData.Field<>(8, Serializer.OPTIONAL_POSITION);
            SHOW_BOTTOM = new EntityData.Field<>(9, Serializer.BOOLEAN);
        }
    }
}
