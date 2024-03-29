package org.machinemc.primallib.world;

import org.bukkit.Material;

/**
 * Causes the bell to perform the ring animation. The ring sound is not played from this action.
 */
public final class BellAction implements BlockAction {

    @Override
    public Material[] supports() {
        return new Material[] {Material.BELL};
    }

}
