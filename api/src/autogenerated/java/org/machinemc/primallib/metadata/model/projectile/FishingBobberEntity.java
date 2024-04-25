package org.machinemc.primallib.metadata.model.projectile;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link FishingBobberEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface FishingBobberEntity extends ProjectileEntity {
    /**
     * Metadata field for HOOK_ENTITY_ID state of the entity
     */
    EntityData.Field<Integer> HOOK_ENTITY_ID = Initializer.HOOK_ENTITY_ID;

    /**
     * Metadata field for CAUGHT_FISH state of the entity
     */
    EntityData.Field<Boolean> CAUGHT_FISH = Initializer.CAUGHT_FISH;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> HOOK_ENTITY_ID;

        static final EntityData.Field<Boolean> CAUGHT_FISH;

        static {
            HOOK_ENTITY_ID = new EntityData.Field<>(8, Serializer.INT);
            CAUGHT_FISH = new EntityData.Field<>(9, Serializer.BOOLEAN);
        }
    }
}
