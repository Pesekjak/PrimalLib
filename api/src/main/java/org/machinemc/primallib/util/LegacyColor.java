package org.machinemc.primallib.util;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents a legacy Minecraft color constants that can still be used at
 * many places even in newer versions of Minecraft.
 * <p>
 * This class serves as a replacement for deprecated {@code org.bukkit.ChatColor}.
 */
@Getter
public enum LegacyColor {

    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    OBFUSCATED('k', true),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINED('n', true),
    ITALIC('o', true),
    RESET('r');

    private final char code;
    private final boolean format;

    LegacyColor(char code) {
        this(code, false);
    }

    LegacyColor(char code, boolean format) {
        this.code = code;
        this.format = format;
    }

    /**
     * Returns legacy color with given color code or
     * empty if there is none.
     *
     * @return legacy color with given color code
     */
    public static Optional<LegacyColor> byCode(char code) {
        return Arrays.stream(values()).filter(color -> color.code == code).findFirst();
    }

    /**
     * Returns true if the legacy color is color and not format.
     *
     * @return whether the legacy color is color
     */
    public boolean isColor() {
        return !isFormat();
    }

    /**
     * Returns magic ID of this color.
     *
     * @return id of the color
     */
    public int getID() {
        return ordinal();
    }

    /**
     * Returns this legacy color as adventure named text color.
     * <p>
     * Does not work for format and {@link #RESET} legacy colors.
     *
     * @return text color
     */
    public NamedTextColor asTextColor() {
        Preconditions.checkState(!format);
        return NamedTextColor.NAMES.value(name());
    }

    /**
     * Returns this legacy color as adventure text decoration.
     * <p>
     * Does not work for not format and {@link #RESET} legacy colors.
     *
     * @return text decoration
     */
    public TextDecoration asTextDecoration() {
        Preconditions.checkState(format);
        return TextDecoration.valueOf(name());
    }

    @Override
    public String toString() {
        return "§" + code;
    }

}
