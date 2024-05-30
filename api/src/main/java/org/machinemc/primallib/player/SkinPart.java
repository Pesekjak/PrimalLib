package org.machinemc.primallib.player;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents skin parts settings of a player model.
 */
@Getter
public enum SkinPart {

    /**
     * Enabled cape.
     */
    CAPE_ENABLED(0x01),

    /**
     * Enabled second layer of jacket.
     */
    JACKET_ENABLED(0x02),

    /**
     * Enabled second layer of left sleeve.
     */
    LEFT_SLEEVE_ENABLED(0x04),

    /**
     * Enabled second layer of right sleeve.
     */
    RIGHT_SLEEVE_ENABLED(0x08),

    /**
     * Enabled second layer of left leg.
     */
    LEFT_PANTS_LEG_ENABLED(0x10),

    /**
     * Enabled second layer of right leg.
     */
    RIGHT_PANTS_LEG_ENABLED(0x20),

    /**
     * Enabled second layer of head.
     */
    HAT_ENABLED(0x40);

    private final int mask;

    SkinPart(int mask) {
        this.mask = mask;
    }

    /**
     * Creates mask from skin parts.
     *
     * @param parts skin parts
     * @return mask
     */
    public static int createMask(SkinPart... parts) {
        int mask = 0;
        for (SkinPart part : parts) {
            mask |= part.mask;
        }
        return mask;
    }

    /**
     * Returns skin parts from a mask.
     *
     * @param mask mask
     * @return skin parts
     */
    public static SkinPart[] fromMask(int mask) {
        List<SkinPart> list = new ArrayList<>();
        for (SkinPart parts : values()) {
            if ((parts.mask & mask) == parts.mask) {
                list.add(parts);
            }
        }
        return list.toArray(new SkinPart[0]);
    }

}
