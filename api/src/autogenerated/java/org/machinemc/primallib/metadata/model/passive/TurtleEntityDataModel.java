package org.machinemc.primallib.metadata.model.passive;

import io.papermc.paper.math.Position;
import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link TurtleEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface TurtleEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for HOME_POS state of the entity
     */
    EntityData.Field<Position> HOME_POS = Initializer.HOME_POS;

    /**
     * Metadata field for HAS_EGG state of the entity
     */
    EntityData.Field<Boolean> HAS_EGG = Initializer.HAS_EGG;

    /**
     * Metadata field for DIGGING_SAND state of the entity
     */
    EntityData.Field<Boolean> DIGGING_SAND = Initializer.DIGGING_SAND;

    /**
     * Metadata field for TRAVEL_POS state of the entity
     */
    EntityData.Field<Position> TRAVEL_POS = Initializer.TRAVEL_POS;

    /**
     * Metadata field for LAND_BOUND state of the entity
     */
    EntityData.Field<Boolean> LAND_BOUND = Initializer.LAND_BOUND;

    /**
     * Metadata field for ACTIVELY_TRAVELING state of the entity
     */
    EntityData.Field<Boolean> ACTIVELY_TRAVELING = Initializer.ACTIVELY_TRAVELING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Position> HOME_POS;

        static final EntityData.Field<Boolean> HAS_EGG;

        static final EntityData.Field<Boolean> DIGGING_SAND;

        static final EntityData.Field<Position> TRAVEL_POS;

        static final EntityData.Field<Boolean> LAND_BOUND;

        static final EntityData.Field<Boolean> ACTIVELY_TRAVELING;

        static {
            HOME_POS = new EntityData.Field<>(17, Serializer.POSITION);
            HAS_EGG = new EntityData.Field<>(18, Serializer.BOOLEAN);
            DIGGING_SAND = new EntityData.Field<>(19, Serializer.BOOLEAN);
            TRAVEL_POS = new EntityData.Field<>(20, Serializer.POSITION);
            LAND_BOUND = new EntityData.Field<>(21, Serializer.BOOLEAN);
            ACTIVELY_TRAVELING = new EntityData.Field<>(22, Serializer.BOOLEAN);
        }
    }
}
