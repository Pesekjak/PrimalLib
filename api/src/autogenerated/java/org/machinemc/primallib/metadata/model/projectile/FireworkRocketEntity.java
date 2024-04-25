package org.machinemc.primallib.metadata.model.projectile;

import java.lang.Boolean;
import java.lang.Integer;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link FireworkRocketEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface FireworkRocketEntity extends ProjectileEntity {
    /**
     * Metadata field for ITEM state of the entity
     */
    EntityData.Field<ItemStack> ITEM = Initializer.ITEM;

    /**
     * Metadata field for SHOOTER_ENTITY_ID state of the entity
     */
    EntityData.Field<Optional<Integer>> SHOOTER_ENTITY_ID = Initializer.SHOOTER_ENTITY_ID;

    /**
     * Metadata field for SHOT_AT_ANGLE state of the entity
     */
    EntityData.Field<Boolean> SHOT_AT_ANGLE = Initializer.SHOT_AT_ANGLE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<ItemStack> ITEM;

        static final EntityData.Field<Optional<Integer>> SHOOTER_ENTITY_ID;

        static final EntityData.Field<Boolean> SHOT_AT_ANGLE;

        static {
            ITEM = new EntityData.Field<>(8, Serializer.SLOT);
            SHOOTER_ENTITY_ID = new EntityData.Field<>(9, Serializer.OPTIONAL_INT);
            SHOT_AT_ANGLE = new EntityData.Field<>(10, Serializer.BOOLEAN);
        }
    }
}
