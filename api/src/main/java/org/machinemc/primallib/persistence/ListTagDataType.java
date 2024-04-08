package org.machinemc.primallib.persistence;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;
import net.kyori.adventure.nbt.IntBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.nbt.BinaryTagTypeUtils;

import java.util.ArrayList;
import java.util.List;

import static org.machinemc.primallib.persistence.PersistentDataContainerExtension.*;

/**
 * Persistent data type for adventure compound binary tag (NBT).
 */
@Getter
public class ListTagDataType implements PersistentDataType<PersistentDataContainer, ListBinaryTag> {

    private static final ListTagDataType INSTANCE = new ListTagDataType();

    private final Class<PersistentDataContainer> primitiveType = PersistentDataContainer.class;
    private final Class<ListBinaryTag> complexType = ListBinaryTag.class;

    /**
     * Returns instance of this data type.
     *
     * @return this data type
     */
    public static ListTagDataType get() {
        return INSTANCE;
    }

    private ListTagDataType() {
    }

    @Override
    public @NotNull PersistentDataContainer toPrimitive(@NotNull ListBinaryTag complex,
                                                        @NotNull PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();
        setTag(container, NamespacedKey.minecraft("type"), IntBinaryTag.intBinaryTag(complex.elementType().id()));
        setTag(container, NamespacedKey.minecraft("size"), IntBinaryTag.intBinaryTag(complex.size()));
        for (int i = 0; i < complex.size(); i++) {
            setTag(container, NamespacedKey.minecraft(String.valueOf(i)), complex.get(i));
        }
        return container;
    }

    @Override
    public @NotNull ListBinaryTag fromPrimitive(@NotNull PersistentDataContainer primitive,
                                                @NotNull PersistentDataAdapterContext context) {
        Integer id = primitive.get(NamespacedKey.minecraft("type"), INTEGER);
        Integer size = primitive.get(NamespacedKey.minecraft("size"), INTEGER);
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(size);
        BinaryTagType<BinaryTag> type = BinaryTagTypeUtils.getByID(id).orElseThrow(UnsupportedOperationException::new);
        List<BinaryTag> tags = new ArrayList<>();
        for (int i = 0; i < size; i++)
            tags.add(getTag(primitive, NamespacedKey.minecraft(String.valueOf(i)), type));
        return ListBinaryTag.listBinaryTag(type, tags);
    }

}
