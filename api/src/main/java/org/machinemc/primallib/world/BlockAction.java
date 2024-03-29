package org.machinemc.primallib.world;

import org.bukkit.Material;

/**
 * Represents an action of a block.
 * <p>
 * This could for example be opening chest or pushing piston.
 */
public sealed interface BlockAction permits BellAction, ChestAction, EndGatewayAction, PistonAction, ShulkerBoxAction {

    /**
     * Returns an array of supported block types for this action.
     *
     * @return supported block types
     */
    Material[] supports();

}
