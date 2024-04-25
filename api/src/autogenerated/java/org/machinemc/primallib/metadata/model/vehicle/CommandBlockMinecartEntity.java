package org.machinemc.primallib.metadata.model.vehicle;

import java.lang.String;
import javax.annotation.processing.Generated;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link CommandBlockMinecartEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface CommandBlockMinecartEntity extends AbstractMinecartEntity {
    /**
     * Metadata field for COMMAND state of the entity
     */
    EntityData.Field<String> COMMAND = Initializer.COMMAND;

    /**
     * Metadata field for LAST_OUTPUT state of the entity
     */
    EntityData.Field<Component> LAST_OUTPUT = Initializer.LAST_OUTPUT;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<String> COMMAND;

        static final EntityData.Field<Component> LAST_OUTPUT;

        static {
            COMMAND = new EntityData.Field<>(14, Serializer.STRING);
            LAST_OUTPUT = new EntityData.Field<>(15, Serializer.COMPONENT);
        }
    }
}
