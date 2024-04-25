package org.machinemc.primallib.metadata.model.mob;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.passive.GolemEntity;

/**
 * Applicable metadata fields for all entities inheriting {@link ShulkerEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ShulkerEntity extends GolemEntity {
    /**
     * Metadata field for ATTACHED_FACE state of the entity
     */
    EntityData.Field<BlockFace> ATTACHED_FACE = Initializer.ATTACHED_FACE;

    /**
     * Metadata field for PEEK_AMOUNT state of the entity
     */
    EntityData.Field<Byte> PEEK_AMOUNT = Initializer.PEEK_AMOUNT;

    /**
     * Metadata field for COLOR state of the entity
     */
    EntityData.Field<Byte> COLOR = Initializer.COLOR;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<BlockFace> ATTACHED_FACE;

        static final EntityData.Field<Byte> PEEK_AMOUNT;

        static final EntityData.Field<Byte> COLOR;

        static {
            ATTACHED_FACE = new EntityData.Field<>(16, Serializer.DIRECTION);
            PEEK_AMOUNT = new EntityData.Field<>(17, Serializer.BYTE);
            COLOR = new EntityData.Field<>(18, Serializer.BYTE);
        }
    }
}
