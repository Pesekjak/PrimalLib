package org.machinemc.primallib.version;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents Minecraft protocol version.
 */
@Getter
public enum ProtocolVersion {

    /**
     * Unknown protocol version, used for {@link Targets} annotations.
     * @see Targets.Utils
     */
    @ApiStatus.Internal UNKNOWN (-1),

    PROTOCOL_1_20_5             (766);

    private final int value;

    /**
     * Returns protocol version of currently running
     * Paper server.
     *
     * @return protocol version
     */
    public static ProtocolVersion get() {
        return MinecraftVersion.get().getProtocol();
    }

    /**
     * Returns the latest supported protocol version.
     *
     * @return latest supported protocol version
     */
    public static ProtocolVersion latest() {
        ProtocolVersion[] all = ProtocolVersion.values();
        return all[all.length - 1];
    }

    /**
     * Returns the oldest supported protocol version.
     *
     * @return oldest supported protocol version
     */
    public static ProtocolVersion oldest() {
        ProtocolVersion[] all = ProtocolVersion.values();
        Preconditions.checkState(all.length > 1);
        return all[1];
    }

    ProtocolVersion(int value) {
        this.value = value;
    }

    /**
     * Checks if this protocol version is greater than the provided one.
     *
     * @param other other
     * @return whether this protocol version is greater
     */
    public boolean greaterThan(ProtocolVersion other) {
        return getValue() > other.getValue();
    }

    /**
     * Checks if this protocol version is not less than the provided one.
     *
     * @param other other
     * @return whether this protocol version is not less
     */
    public boolean noLessThan(ProtocolVersion other) {
        return getValue() >= other.getValue();
    }

    /**
     * Checks if this protocol version is less than the provided one.
     *
     * @param other other
     * @return whether this protocol version is less
     */
    public boolean lessThan(ProtocolVersion other) {
        return getValue() < other.getValue();
    }

    /**
     * Checks if this protocol version is not greater than the provided one.
     *
     * @param other other
     * @return whether this protocol version not greater
     */
    public boolean noGreaterThan(ProtocolVersion other) {
        return getValue() <= other.getValue();
    }

    /**
     * Checks if this protocol version equals to the provided one.
     *
     * @param other other
     * @return whether this protocol version equals to the other one
     */
    public boolean noGreaterOrLessThan(ProtocolVersion other) {
        return getValue() == other.getValue();
    }

}
