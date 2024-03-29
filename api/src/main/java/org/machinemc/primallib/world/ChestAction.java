package org.machinemc.primallib.world;

import lombok.With;
import org.bukkit.Material;

/**
 * Block action used to update lid animation.
 *
 * @param opened whether the lid should be opened
 */
@With
public record ChestAction(boolean opened) implements BlockAction {

    @Override
    public Material[] supports() {
        return new Material[] {Material.CHEST, Material.ENDER_CHEST, Material.TRAPPED_CHEST};
    }

}
