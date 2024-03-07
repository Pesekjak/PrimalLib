package org.machinemc.primallib.persistence;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.nbt.BinaryTagTypeUtils;

import java.util.BitSet;

/**
 * Persistent data type for adventure compound binary tag (NBT).
 */
@Getter
@ExtensionMethod(PersistentDataContainerExtension.class)
public class CompoundTagDataType implements PersistentDataType<PersistentDataContainer, CompoundBinaryTag> {

    private static final CompoundTagDataType INSTANCE = new CompoundTagDataType();

    private final Class<PersistentDataContainer> primitiveType = PersistentDataContainer.class;
    private final Class<CompoundBinaryTag> complexType = CompoundBinaryTag.class;

    /**
     * Returns instance of this data type.
     *
     * @return this data type
     */
    public static CompoundTagDataType get() {
        return INSTANCE;
    }

    private CompoundTagDataType() {
    }

    @Override
    public @NotNull PersistentDataContainer toPrimitive(@NotNull CompoundBinaryTag complex,
                                                        @NotNull PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();
        for (String key : complex.keySet()) {
            BinaryTag binaryTag = complex.get(key);
            Preconditions.checkNotNull(binaryTag);

            NamespacedKey namespacedKey = getNamespacedKey(key, binaryTag.type());

            container.setTag(namespacedKey, binaryTag);
        }
        return container;
    }

    @Override
    public @NotNull CompoundBinaryTag fromPrimitive(@NotNull PersistentDataContainer primitive,
                                                    @NotNull PersistentDataAdapterContext context) {
        CompoundBinaryTag.Builder compound = CompoundBinaryTag.builder();
        for (NamespacedKey namespacedKey : primitive.getKeys()) {
            Pair<String, BinaryTagType<BinaryTag>> pair = getKey(namespacedKey);
            BinaryTag tag = primitive.getTag(namespacedKey, pair.second());
            compound.put(pair.key(), tag);
        }
        return compound.build();
    }

    /**
     * Returns unique namespaced key from a String, including data about uppercase
     * characters and nbt type that can be decoded later.
     * <p>
     * This is useful for conversion between keys in Compound tags and namespaced keys because
     * they don't allow uppercase characters.
     *
     * @param key key to convert
     * @param type type of the binary tag
     * @return namespaced key
     * @see CompoundTagDataType#getKey(NamespacedKey)
     */
    private static NamespacedKey getNamespacedKey(String key, BinaryTagType<?> type) {
        BitSet data = new BitSet();
        int id = type.id();
        Preconditions.checkArgument(id < 16, "Unsupported binary tag type");

        data.set(0, (id & 0b1) != 0);
        data.set(1, (id & (0b1 << 1)) != 0);
        data.set(2, (id & (0b1 << 2)) != 0);
        data.set(3, (id & (0b1 << 3)) != 0);

        char[] chars = key.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isUpperCase(chars[i])) continue;
            data.set(i + 4);
        }

        String caseString = BaseEncoding.base32().encode(data.toByteArray())
                .toLowerCase()
                .replace('=', '_');
        return new NamespacedKey(key.toLowerCase(), caseString);
    }

    /**
     * Decodes unique key from a Namespaced key, including data about uppercase
     * characters.
     * <p>
     * This is useful for conversion between keys in Compound tags and namespaced keys because
     * they don't allow uppercase characters.
     *
     * @param namespacedKey namespacedKey to convert
     * @return key
     * @see CompoundTagDataType#getNamespacedKey(String, BinaryTagType)
     */
    private static Pair<String, BinaryTagType<BinaryTag>> getKey(NamespacedKey namespacedKey) {
        BitSet data = BitSet.valueOf(BaseEncoding.base32().decode(
                namespacedKey.getKey()
                        .toUpperCase()
                        .replace('_', '=')
        ));

        int id = (data.get(0) ? 0b1 : 0)
                | (data.get(1) ? (0b1 << 1) : 0)
                | (data.get(2) ? (0b1 << 2) : 0)
                | (data.get(3) ? (0b1 << 3) : 0);
        BinaryTagType<BinaryTag> type = BinaryTagTypeUtils.getByID(id).orElseThrow(UnsupportedOperationException::new);

        char[] chars = namespacedKey.getNamespace().toCharArray();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char next = data.get(i + 4) ? Character.toUpperCase(chars[i]) : chars[i];
            key.append(next);
        }

        return ObjectObjectImmutablePair.of(key.toString(), type);
    }

}
