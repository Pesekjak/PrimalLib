package org.machinemc.primallib.util;

/**
 * Represents object that can be written to the {@link MinecraftBuf}.
 */
public interface Writable {

    /**
     * Writes this object to the byte buffer.
     *
     * @param buf buffer to write into
     */
    void write(MinecraftBuf buf);

}
