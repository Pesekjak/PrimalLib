package org.machinemc.primallib.event.configuration;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import lombok.With;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

/**
 * Represents a repository of data that contain entries pertaining to certain aspects of the game,
 * such as the world, the player, among others.
 * <p>
 * This class is only used for packet-based events and modifying the registry deeply could cause
 * serious issues for the client. Because of that reason only entry element modification is allowed.
 */
public class Registry {

    @Getter
    private final Key key;
    private final Int2ObjectMap<Entry> entries = new Int2ObjectOpenHashMap<>();

    /**
     * New empty registry with specified key.
     *
     * @param key key of the registry
     */
    public Registry(Key key) {
        this(key, Collections.emptyMap());
    }

    /**
     * New registry with specified key and contents.
     *
     * @param key key
     * @param entries entries in the registry
     */
    public Registry(Key key, Map<Integer, Entry> entries) {
        Preconditions.checkNotNull(key, "Key can not be null");
        Preconditions.checkNotNull(entries, "Entries can not be null");
        this.key = key;
        this.entries.putAll(entries);
    }

    /**
     * Returns unmodifiable collections of the entries of this registry.
     *
     * @return entries
     */
    public final @Unmodifiable Int2ObjectMap<Entry> getEntries() {
        return Int2ObjectMaps.unmodifiable(entries);
    }

    /**
     * Modifiers element of an entry.
     *
     * @param key key of the entry to modify
     * @param function function that will replace the NBT element of the entry
     * @return whether the entry has been modified successfully
     */
    public boolean modifyEntryElement(Key key, Function<Entry, CompoundBinaryTag> function) {
        int found = -1;
        for (int i : entries.keySet()) {
            Entry next = entries.get(i);
            if (next.key != key) continue;
            found = i;
            break;
        }
        if (found == -1) return false;
        Entry entry = entries.get(found);
        Entry modified = entry.withElement(function.apply(entry));
        entries.put(found, modified);
        return true;
    }

    /**
     * Represents an entry inside a registry.
     *
     * @param key name of the entry
     * @param element NBT element of the entry.
     */
    public record Entry(Key key, @With @Nullable CompoundBinaryTag element) {

        public Entry {
            Preconditions.checkNotNull(key, "Key can not be null");
        }

    }

}
