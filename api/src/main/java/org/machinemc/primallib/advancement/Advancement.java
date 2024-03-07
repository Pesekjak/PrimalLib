package org.machinemc.primallib.advancement;

import com.google.common.base.Preconditions;
import io.papermc.paper.advancement.AdvancementDisplay;
import lombok.With;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a advancement.
 *
 * @param key key of the advancement
 * @param parent parent of the advancement
 * @param display display of the advancement
 */
@With
public record Advancement(Key key,
                          @Nullable Key parent,
                          @Nullable AdvancementDisplay display,
                          AdvancementCriteria criteria,
                          float x,
                          float y) implements Keyed {

    /**
     * Creates new advancement from bukkit one.
     *
     * @param bukkit bukkit advancement
     * @return advancement
     * @deprecated defaults coordinates to 0;0 which is incorrect position
     */
    @Deprecated
    public static Advancement fromBukkit(org.bukkit.advancement.Advancement bukkit) {
        return fromBukkit(bukkit, 0, 0);
    }

    /**
     * Creates new advancement from bukkit one.
     *
     * @param bukkit bukkit advancement
     * @param x x-coordinate of the advancement
     * @param y y-coordinate of the advancement
     * @return advancement
     * @deprecated Bukkit does not store information about how individual criteria are grouped together
     */
    @Deprecated
    public static Advancement fromBukkit(org.bukkit.advancement.Advancement bukkit, float x, float y) {
        org.bukkit.advancement.Advancement parent = bukkit.getParent();
        return new Advancement(
                bukkit.key(),
                parent != null ? parent.key() : null,
                bukkit.getDisplay(),
                AdvancementCriteria.fromBukkit(bukkit),
                x,
                y
        );
    }

    /**
     * Creates new advancement display builder.
     *
     * @return new display builder
     */
    public static DisplayBuilder displayBuilder() {
        return new DisplayBuilder();
    }

    public Advancement(Key key,
                       @Nullable Key parent,
                       @Nullable AdvancementDisplay display,
                       AdvancementCriteria criteria) {
        this(key, parent, display, criteria, 0, 0);
    }

    public Advancement {
        Preconditions.checkNotNull(key, "Key can not be null");
    }

    /**
     * @return whether the advancement is root advancement of advancement screen
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * Returns name of the tab this advancement is in.
     *
     * @param player player to get the root advancement from
     * @return name of the advancement tab
     */
    public Optional<Component> tabName(Player player) {
        Advancement root = root(player).orElse(null);
        if (root == null) return Optional.empty();
        return Optional.ofNullable(root.display).map(AdvancementDisplay::title);
    }

    /**
     * Returns icon of the tab this advancement is in.
     *
     * @param player player to get the root advancement from
     * @return icon of the advancement tab
     */
    public Optional<ItemStack> tabIcon(Player player) {
        Advancement root = root(player).orElse(null);
        if (root == null) return Optional.empty();
        return Optional.ofNullable(root.display).map(AdvancementDisplay::icon);
    }

    /**
     * Returns background of the tab this advancement is in.
     *
     * @param player player to get the root advancement from
     * @return background of the advancement tab
     */
    public Optional<NamespacedKey> tabBackground(Player player) {
        Advancement root = root(player).orElse(null);
        if (root == null) return Optional.empty();
        return Optional.ofNullable(root.display).map(AdvancementDisplay::backgroundPath);
    }

    /**
     * Returns root advancement of the tab this advancement is in.
     *
     * @param player player to get the root advancement from
     * @return root advancement for this advancement
     */
    public Optional<Advancement> root(Player player) {
        AdvancementService service = AdvancementService.get();
        Advancement next = service.getAdvancement(player, this.key).orElse(null);
        if (next == null) return Optional.empty();
        while (next != null) {
            if (next.isRoot()) return Optional.of(next);
            next = service.getAdvancement(player, next.parent).orElse(null);
        }
        return Optional.empty();
    }

    /**
     * Exposed implementation of advancement display builder.
     */
    public static class DisplayBuilder extends AdvancementDisplayImpl.AdvancementDisplayImplBuilder {

        private DisplayBuilder() {
        }

    }

}
