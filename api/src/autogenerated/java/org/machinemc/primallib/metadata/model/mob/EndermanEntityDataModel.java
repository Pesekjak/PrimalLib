package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link EndermanEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface EndermanEntityDataModel extends HostileEntityDataModel {
    /**
     * Metadata field for CARRIED_BLOCK state of the entity
     */
    EntityData.Field<Optional<BlockData>> CARRIED_BLOCK = Initializer.CARRIED_BLOCK;

    /**
     * Metadata field for ANGRY state of the entity
     */
    EntityData.Field<Boolean> ANGRY = Initializer.ANGRY;

    /**
     * Metadata field for PROVOKED state of the entity
     */
    EntityData.Field<Boolean> PROVOKED = Initializer.PROVOKED;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Optional<BlockData>> CARRIED_BLOCK;

        static final EntityData.Field<Boolean> ANGRY;

        static final EntityData.Field<Boolean> PROVOKED;

        static {
            CARRIED_BLOCK = new EntityData.Field<>(16, Serializer.OPTIONAL_BLOCK_STATE);
            ANGRY = new EntityData.Field<>(17, Serializer.BOOLEAN);
            PROVOKED = new EntityData.Field<>(18, Serializer.BOOLEAN);
        }
    }
}
