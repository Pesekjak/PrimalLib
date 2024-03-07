package org.machinemc.primallib.persistence;

import com.google.common.base.Preconditions;
import lombok.experimental.ExtensionMethod;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.machinemc.primallib.nbt.BinaryTagExtension;

import java.util.Map;

import static java.util.Map.entry;
import static net.kyori.adventure.nbt.BinaryTagTypes.*;

/**
 * Contains extensions for {@link PersistentDataContainer} class.
 * <p>
 * This class can be either used standalone as utils class, or
 * together with {@link ExtensionMethod} lombok
 * annotation for easier operations.
 */
@ExtensionMethod(BinaryTagExtension.class)
public final class PersistentDataContainerExtension {

    private PersistentDataContainerExtension() {
        throw new UnsupportedOperationException();
    }

    private static final Map<BinaryTagType<?>, PersistentDataType<?, ?>> DATA_TYPE_MAP = Map.ofEntries(
            entry(BYTE, PersistentDataType.BYTE),
            entry(SHORT, PersistentDataType.SHORT),
            entry(INT, PersistentDataType.INTEGER),
            entry(LONG, PersistentDataType.LONG),
            entry(FLOAT, PersistentDataType.FLOAT),
            entry(DOUBLE, PersistentDataType.DOUBLE),
            entry(BYTE_ARRAY, PersistentDataType.BYTE_ARRAY),
            entry(STRING, PersistentDataType.STRING),
            entry(LIST, ListTagDataType.get()),
            entry(COMPOUND, CompoundTagDataType.get()),
            entry(INT_ARRAY, PersistentDataType.INTEGER_ARRAY),
            entry(LONG_ARRAY, PersistentDataType.LONG_ARRAY)
    );

    /**
     * Sets binary tag to the persistent data container.
     *
     * @param container container
     * @param key key
     * @param tag tag
     */
    @SuppressWarnings("unchecked")
    public static void setTag(PersistentDataContainer container, NamespacedKey key, BinaryTag tag) {
        PersistentDataType<?, Object> dataType = (PersistentDataType<?, Object>) DATA_TYPE_MAP.get(tag.type());
        Preconditions.checkNotNull(dataType, "Unsupported tag type: " + tag.type());
        Object value = switch (tag) {
            case ListBinaryTag listTag -> listTag;
            case CompoundBinaryTag compoundTag -> compoundTag;
            default -> tag.unwrap();
        };
        container.set(key, dataType, value);
    }

    /**
     * Returns binary tag from the persistent data container.
     *
     * @param container container
     * @param key key
     */
    @SuppressWarnings("unchecked")
    public static <T extends BinaryTag> T getTag(PersistentDataContainer container, NamespacedKey key, BinaryTagType<T> type) {
        PersistentDataType<?, Object> dataType = (PersistentDataType<?, Object>) DATA_TYPE_MAP.get(type);
        Preconditions.checkNotNull(dataType, "Unsupported tag type: " + type);
        return BinaryTagExtension.wrap(container.get(key, dataType));
    }

}
