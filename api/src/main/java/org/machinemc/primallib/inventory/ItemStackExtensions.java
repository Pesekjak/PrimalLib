package org.machinemc.primallib.inventory;

import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.machinemc.primallib.profile.GameProfile;
import org.machinemc.primallib.profile.PlayerTextures;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Contains extensions for {@link org.bukkit.inventory.ItemStack} class.
 * <p>
 * This class can be either used standalone as utils class, or
 * together with {@link lombok.experimental.ExtensionMethod} lombok
 * annotation for easier operations.
 */
public final class ItemStackExtensions {

    private ItemStackExtensions() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns NBT of provided ItemStack.
     *
     * @param itemStack item stack
     * @return NBT of the item stack
     */
    @Contract(pure = true)
    public static CompoundBinaryTag getNBT(ItemStack itemStack) throws IOException {
        GZIPInputStream gzipIS = new GZIPInputStream(new ByteArrayInputStream(itemStack.serializeAsBytes()));
        CompoundBinaryTag tag = BinaryTagIO.reader().read(gzipIS);
        gzipIS.close();
        return tag;
    }

    /**
     * Creates Item stack from its NBT.
     *
     * @param nbt NBT of the item.
     * @return item stack from given NBT
     */
    @Contract(pure = true)
    public static ItemStack createItem(CompoundBinaryTag nbt) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        GZIPOutputStream gzipOS = new GZIPOutputStream(os);
        BinaryTagIO.writer().write(nbt, gzipOS);
        gzipOS.close();
        return ItemStack.deserializeBytes(os.toByteArray());
    }

    /**
     * Changes head texture of this item stack to a new one.
     * <p>
     * If different item stack than player head is provided, nothing happens.
     *
     * @param itemStack item stack
     * @param playerTextures new head texture
     * @return provided item stack with the new texture
     */
    public static ItemStack setHeadTexture(ItemStack itemStack, PlayerTextures playerTextures) {
        return setHeadTexture(itemStack, playerTextures.value());
    }

    /**
     * Changes head texture of this item stack to a new one.
     * <p>
     * If different item stack than player head is provided, nothing happens.
     *
     * @param itemStack item stack
     * @param texture new head texture
     * @return provided item stack with the new texture
     */
    public static ItemStack setHeadTexture(ItemStack itemStack, String texture) {
        ItemMeta meta = itemStack.getItemMeta();
        if (!(meta instanceof SkullMeta skullMeta)) return itemStack;

        GameProfile gameProfile = new GameProfile(
                UUID.nameUUIDFromBytes(texture.getBytes(StandardCharsets.UTF_8)),
                "",
                List.of(new GameProfile.Property(PlayerTextures.TEXTURES, texture, null)));

        skullMeta.setPlayerProfile(gameProfile.asPaper());

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    /**
     * Updates the item meta of an item stack.
     *
     * @param itemStack item stack to update the item meta for
     * @param function function returning the modified meta
     * @return whether successfully applied the item meta
     * @param <Meta> type of meta for the item
     */
    @SuppressWarnings("unchecked")
    public static <Meta extends ItemMeta> boolean modifyItemMeta(ItemStack itemStack, BiFunction<ItemStack, Meta, ItemMeta> function) {
        Meta meta = (Meta) itemStack.getItemMeta();
        return itemStack.setItemMeta(function.apply(itemStack, meta));
    }

}
