package org.machinemc.primallib.version;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.List;

import static org.machinemc.primallib.version.ProtocolVersion.PROTOCOL_1_20_3;

/**
 * Represents Minecraft version.
 * <p>
 * All enums in this class have to follow strict
 * format of {@code MINECRAFT_VERSION}.
 */
public enum MinecraftVersion {

    MINECRAFT_1_20_4    (PROTOCOL_1_20_3, "1.20.4");

    @Getter
    private final ProtocolVersion protocol;
    private final List<String> names;

    // Cached version of currently running server
    private static MinecraftVersion current;

    /**
     * Returns minecraft version of currently running
     * Paper server.
     *
     * @return minecraft version
     */
    public static MinecraftVersion get() {
        if (current != null) return current;
        String version = Bukkit.getServer().getMinecraftVersion();
        for (MinecraftVersion minecraftVersion : values()) {
            if (!minecraftVersion.names.contains(version)) continue;
            current = minecraftVersion;
            return current;
        }
        throw new UnsupportedOperationException("There is no supported MinecraftVersion for version '%s'".formatted(version));
    }

    MinecraftVersion(ProtocolVersion protocol, String... names) {
        this.protocol = protocol;
        this.names = List.of(names);
    }

    /**
     * Returns identifier for given Minecraft version.
     *
     * @return code name
     */
    public String getCodeName() {
        return name().replace("MINECRAFT_", "v");
    }

    /**
     * Checks if this minecraft version is greater than the provided one.
     *
     * @param other other
     * @return whether this minecraft version is greater
     */
    public boolean greaterThan(MinecraftVersion other) {
        return compareTo(other) > 0;
    }

    /**
     * Checks if this minecraft version is not less than the provided one.
     *
     * @param other other
     * @return whether this minecraft version is not less
     */
    public boolean noLessThan(MinecraftVersion other) {
        return compareTo(other) >= 0;
    }

    /**
     * Checks if this minecraft version is less than the provided one.
     *
     * @param other other
     * @return whether this minecraft version is less
     */
    public boolean lessThan(MinecraftVersion other) {
        return compareTo(other) < 0;
    }

    /**
     * Checks if this minecraft version is not greater than the provided one.
     *
     * @param other other
     * @return whether this minecraft version not greater
     */
    public boolean noGreaterThan(MinecraftVersion other) {
        return compareTo(other) <= 0;
    }

    /**
     * Checks if this minecraft version equals to the provided one.
     *
     * @param other other
     * @return whether this minecraft version equals to the other one
     */
    public boolean noGreaterOrLessThan(MinecraftVersion other) {
        return compareTo(other) == 0;
    }

}
