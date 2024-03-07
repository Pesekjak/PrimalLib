package org.machinemc.primallib.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class LegacyColorTest {

    @Test
    public void testColorsConversion() {
        Arrays.stream(LegacyColor.values())
                .filter(LegacyColor::isColor)
                .filter(legacyColor -> legacyColor != LegacyColor.RESET)
                .forEach(LegacyColor::asTextColor);
    }

    @Test
    public void testDecorationConversion() {
        Arrays.stream(LegacyColor.values())
                .filter(LegacyColor::isFormat)
                .forEach(LegacyColor::asTextDecoration);
    }

}
