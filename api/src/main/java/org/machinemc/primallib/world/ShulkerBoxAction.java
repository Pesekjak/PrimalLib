package org.machinemc.primallib.world;

import lombok.With;
import org.bukkit.Material;

import static org.bukkit.Material.*;

/**
 * Block action used to update shell animation.
 *
 * @param opened whether the shell should be opened
 */
@With
public record ShulkerBoxAction(boolean opened) implements BlockAction {

    @Override
    public Material[] supports() {
        return new Material[] {
                SHULKER_BOX,
                WHITE_SHULKER_BOX,
                ORANGE_SHULKER_BOX,
                MAGENTA_SHULKER_BOX,
                LIGHT_BLUE_SHULKER_BOX,
                YELLOW_SHULKER_BOX,
                LIME_SHULKER_BOX,
                PINK_SHULKER_BOX,
                GRAY_SHULKER_BOX,
                LIGHT_GRAY_SHULKER_BOX,
                CYAN_SHULKER_BOX,
                PURPLE_SHULKER_BOX,
                BLUE_SHULKER_BOX,
                BROWN_SHULKER_BOX,
                GREEN_SHULKER_BOX,
                RED_SHULKER_BOX,
                BLACK_SHULKER_BOX
        };
    }

}
