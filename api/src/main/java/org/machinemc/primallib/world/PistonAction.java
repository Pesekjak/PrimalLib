package org.machinemc.primallib.world;

import com.google.common.base.Preconditions;
import lombok.With;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

/**
 * Controls the state of the piston and affected blocks.
 * Always used on the piston base (either sticky or regular), never on the head.
 * <p>
 * Upon receiving this action, the Notchian client is responsible for the visuals,
 * including movement of the blocks. Server then should change the blocks on own side.
 *
 * @param type type of the piston
 * @param face rotation of the piston
 */
@With
public record PistonAction(Type type, BlockFace face) implements BlockAction {

    public PistonAction {
        Preconditions.checkNotNull(type, "Type of the piston action can not be null");
        Preconditions.checkNotNull(face, "Block face of the piston action can not be null");
        Preconditions.checkState(face.isCartesian(), "Block face needs to be cartesian");
    }

    @Override
    public Material[] supports() {
        return new Material[] {Material.PISTON, Material.STICKY_PISTON, Material.MOVING_PISTON};
    }

    /**
     * Represents a type of action piston can perform.
     */
    public enum Type {

        EXTEND,
        RETRACT,
        CANCEL_EXTENSION // cancels an ongoing extension

    }

}
