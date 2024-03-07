package org.machinemc.primallib.event.configuration;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.*;
import org.bukkit.NamespacedKey;

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
     * Returns list of registries from codec NBT.
     *
     * @param registryData data of all registries as NBT
     * @return registries included in the provided NBT codec
     */
    public static List<Registry> getRegistries(CompoundBinaryTag registryData) {
        List<Registry> registries = new ArrayList<>();
        for (String unparsed : registryData.keySet()) {
            NamespacedKey name = NamespacedKey.fromString(unparsed);
            CompoundBinaryTag data = registryData.getCompound(unparsed);

            ListBinaryTag values = data.getList("value");
            Int2ObjectMap<Entry> entries = new Int2ObjectOpenHashMap<>();
            values.stream()
                    .map(e -> (CompoundBinaryTag) e)
                    .forEach(e -> {
                        NamespacedKey key = NamespacedKey.fromString(e.getString("name"));
                        int id = e.getInt("id");
                        CompoundBinaryTag element = e.getCompound("element");
                        entries.put(id, new Entry(key, element));
                    });
            registries.add(new Registry(name, entries));
        }
        return Collections.unmodifiableList(registries);
    }

    /**
     * Creates NBT codec from collection of provided registries.
     *
     * @param registries registries
     * @return codec created from provided registries
     */
    public static CompoundBinaryTag createCodec(Collection<Registry> registries) {
        CompoundBinaryTag.Builder codec = CompoundBinaryTag.builder();
        for (Registry registry : registries) {
            CompoundBinaryTag.Builder registryNBT = CompoundBinaryTag.builder();
            registryNBT.put("type", StringBinaryTag.stringBinaryTag(registry.getKey().toString()));
            var values = ListBinaryTag.builder(BinaryTagTypes.COMPOUND);
            registry.getEntries().forEach((id, entry) -> {
                CompoundBinaryTag.Builder entryNBT = CompoundBinaryTag.builder();
                entryNBT.put("name", StringBinaryTag.stringBinaryTag(entry.key().toString()));
                entryNBT.put("id", IntBinaryTag.intBinaryTag(id));
                entryNBT.put("element", entry.element());
                values.add(entryNBT.build());
            });
            registryNBT.put("value", values.build());
            codec.put(registry.getKey().toString(), registryNBT.build());
        }
        return codec.build();
    }

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
    public final Int2ObjectMap<Entry> getEntries() {
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
    public record Entry(Key key, CompoundBinaryTag element) {

        public Entry {
            Preconditions.checkNotNull(key, "Key can not be null");
            Preconditions.checkNotNull(element, "Element tag can not be null");
        }

        public Entry withElement(CompoundBinaryTag element) {
            return new Entry(key, element);
        }

    }

}
