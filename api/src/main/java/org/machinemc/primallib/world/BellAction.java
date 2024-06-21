package org.machinemc.primallib.world;

import com.google.common.base.Preconditions;
import lombok.With;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

/**
 * Causes the bell to perform the ring animation. The ring sound is not played from this action.
 *
 * @param face direction of the rotation
 */
@With
public record BellAction(BlockFace face) implements BlockAction {

    public BellAction {
        Preconditions.checkNotNull(face, "Block face of the piston action can not be null");
        Preconditions.checkState(face.isCartesian(), "Block face needs to be cartesian");
    }

    @Override
    public Material[] supports() {
        return new Material[] {Material.BELL};
    }

}
