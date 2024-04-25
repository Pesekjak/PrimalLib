package org.machinemc.primallib.metadata.model;

import java.lang.Boolean;
import java.lang.Float;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.particle.ConfiguredParticle;

/**
 * Applicable metadata fields for all entities inheriting {@link AreaEffectCloudEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface AreaEffectCloudEntity extends Entity {
    /**
     * Metadata field for RADIUS state of the entity
     */
    EntityData.Field<Float> RADIUS = Initializer.RADIUS;

    /**
     * Metadata field for COLOR state of the entity
     */
    EntityData.Field<Integer> COLOR = Initializer.COLOR;

    /**
     * Metadata field for WAITING state of the entity
     */
    EntityData.Field<Boolean> WAITING = Initializer.WAITING;

    /**
     * Metadata field for PARTICLE_ID state of the entity
     */
    EntityData.Field<ConfiguredParticle> PARTICLE_ID = Initializer.PARTICLE_ID;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Float> RADIUS;

        static final EntityData.Field<Integer> COLOR;

        static final EntityData.Field<Boolean> WAITING;

        static final EntityData.Field<ConfiguredParticle> PARTICLE_ID;

        static {
            RADIUS = new EntityData.Field<>(8, Serializer.FLOAT);
            COLOR = new EntityData.Field<>(9, Serializer.INT);
            WAITING = new EntityData.Field<>(10, Serializer.BOOLEAN);
            PARTICLE_ID = new EntityData.Field<>(11, Serializer.PARTICLE);
        }
    }
}
