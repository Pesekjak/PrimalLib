package org.machinemc.primallib.nbt;

import lombok.experimental.ExtensionMethod;
import net.kyori.adventure.nbt.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains extensions for {@link PersistentDataContainer} class.
 * <p>
 * This class can be either used standalone as utils class, or
 * together with {@link ExtensionMethod} lombok
 * annotation for easier operations.
 */
public final class BinaryTagExtension {

    private BinaryTagExtension() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unwraps the binary tag and returns a Java primitive.
     * <p>
     * For lists, instance of {@link java.util.List} is returned instead.
     * <p>
     * For compounds, instance of {@link java.util.Map} is returned instead.
     *
     * @param tag tag to unwrap
     * @return primitive
     * @param <T> primitive
     */
    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    public static <T> T unwrap(BinaryTag tag) {
        return (T) switch (tag) {
            case ByteBinaryTag byteTag -> byteTag.value();
            case ShortBinaryTag shortTag -> shortTag.value();
            case IntBinaryTag intTag -> intTag.value();
            case LongBinaryTag longTag -> longTag.value();
            case FloatBinaryTag floatTag -> floatTag.value();
            case DoubleBinaryTag doubleTag -> doubleTag.value();
            case ByteArrayBinaryTag byteArrayTag -> byteArrayTag.value();
            case StringBinaryTag stringTag -> stringTag.value();
            case ListBinaryTag listTag -> listTag.stream().map(BinaryTagExtension::unwrap).toList();
            case CompoundBinaryTag compoundTag -> {
                Map<String, ?> map = new LinkedHashMap<>();
                compoundTag.forEach(entry -> map.put(entry.getKey(), unwrap(entry.getValue())));
                yield Collections.unmodifiableMap(map);
            }
            case IntArrayBinaryTag intArrayTag -> intArrayTag.value();
            case LongArrayBinaryTag longArrayTag -> longArrayTag.value();
            default -> throw new IllegalStateException("Unexpected value: " + tag);
        };
    }

    /**
     * Creates binary tag variant of provided object.
     *
     * @param o object to create the binary tag from.
     * @return binary tag
     * @param <T> binary tag
     */
    @SuppressWarnings("unchecked")
    public static <T extends BinaryTag> T wrap(Object o) {
        if (o instanceof BinaryTag) return (T) o;
        return (T) switch (o) {
            case Byte b -> ByteBinaryTag.byteBinaryTag(b);
            case Short s -> ShortBinaryTag.shortBinaryTag(s);
            case Integer i -> IntBinaryTag.intBinaryTag(i);
            case Long l -> LongBinaryTag.longBinaryTag(l);
            case Float f -> FloatBinaryTag.floatBinaryTag(f);
            case Double d -> DoubleBinaryTag.doubleBinaryTag(d);
            case byte[] bytes -> ByteArrayBinaryTag.byteArrayBinaryTag(bytes);
            case String string -> StringBinaryTag.stringBinaryTag(string);
            case List<?> list -> {
                if (list.isEmpty()) yield ListBinaryTag.empty();
                yield ListBinaryTag.listBinaryTag(
                        wrap(list.getFirst()).type(),
                        list.stream().map(e -> (BinaryTag) wrap(e)).toList()
                );
            }
            case Map<?, ?> map -> {
                Map<String, BinaryTag> tags = new LinkedHashMap<>();
                map.forEach((key, value) -> tags.put(key.toString(), wrap(value)));
                yield CompoundBinaryTag.from(tags);
            }
            case int[] ints -> IntArrayBinaryTag.intArrayBinaryTag(ints);
            case long[] longs -> LongArrayBinaryTag.longArrayBinaryTag(longs);
            default -> throw new IllegalStateException("Unexpected value: " + o);
        };
    }

}
