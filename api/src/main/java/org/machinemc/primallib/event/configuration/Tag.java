package org.machinemc.primallib.event.configuration;

import com.google.common.base.Preconditions;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Represents tag as a map.
 * <p>
 * To each tag there are mapped numeric IDs of the given type (block, item, etc.).
 *
 * @param registry registry of the tag
 * @param map underlying map
 * @see org.machinemc.primallib.util.MagicNumberService
 */
public record Tag(Key registry, @Unmodifiable Map<Key, int[]> map) {

    public Tag {
        Preconditions.checkNotNull(map, "Map can not be null");
        map = Collections.unmodifiableMap(map);
    }

    /**
     * Returns new tag with added elements from provided map.
     * <p>
     * It replaces already existing int arrays with the arrays provided
     * with the other map.
     *
     * @param other map to add
     * @return new tag
     */
    @Contract(pure = true)
    public Tag putAll(Map<Key, int[]> other) {
        Map<Key, int[]> clone = new HashMap<>(map);
        clone.putAll(other);
        return new Tag(registry, clone);
    }

    /**
     * Returns new tag with added elements from provided map.
     * <p>
     * It merges already existing int arrays with the arrays provided
     * with the other map.
     *
     * @param other map to add
     * @return new tag
     */
    @Contract(pure = true)
    public Tag mergeAll(Map<Key, int[]> other) {
        Map<Key, int[]> clone = new HashMap<>(map);
        other.forEach((key, ids) -> {
            if (!clone.containsKey(key)) {
                clone.put(key, ids);
                return;
            }
            int[] merged = Stream.concat(
                    Arrays.stream(clone.get(key)).boxed(),
                    Arrays.stream(ids).boxed()
            ).distinct().mapToInt(Integer::intValue).toArray();
            clone.put(key, merged);
        });
        return new Tag(registry, clone);
    }

}
