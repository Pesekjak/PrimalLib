package org.machinemc.primallib.nbt;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;

import java.util.List;
import java.util.Optional;

import static net.kyori.adventure.nbt.BinaryTagTypes.*;

/**
 * Additional operations for binary tag types.
 */
public final class BinaryTagTypeUtils {

    private static final Int2ObjectMap<BinaryTagType<?>> TYPES = new Int2ObjectOpenHashMap<>();

    static {
        List.of(
                END, BYTE, SHORT, INT, LONG, FLOAT, LONG, FLOAT, DOUBLE,
                BYTE_ARRAY, STRING, LIST, COMPOUND, INT_ARRAY, LONG_ARRAY
        ).forEach(type -> TYPES.put(type.id(), type));
    }

    private BinaryTagTypeUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns binary tag type from an ID.
     *
     * @param id id
     * @return binary tag type with given ID.
     */
    @SuppressWarnings("unchecked")
    public static <T extends BinaryTag> Optional<BinaryTagType<T>> getByID(int id) {
        return Optional.ofNullable((BinaryTagType<T>) TYPES.get(id));
    }

}
