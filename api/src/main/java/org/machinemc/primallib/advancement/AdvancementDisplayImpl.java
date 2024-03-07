package org.machinemc.primallib.advancement;

import com.google.common.base.Preconditions;
import io.papermc.paper.advancement.AdvancementDisplay;
import lombok.AccessLevel;
import lombok.Builder;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of Paper's advancement display.
 */
@Builder(access = AccessLevel.PUBLIC)
record AdvancementDisplayImpl(Frame frame,
                              Component title,
                              Component description,
                              ItemStack icon,
                              boolean doesShowToast,
                              boolean doesAnnounceToChat,
                              boolean isHidden,
                              @Nullable NamespacedKey backgroundPath,
                              Component displayName) implements AdvancementDisplay {

    public AdvancementDisplayImpl {
        Preconditions.checkNotNull(frame, "Frame can not be null");
        Preconditions.checkNotNull(title, "Title can not be null");
        Preconditions.checkNotNull(description, "Description can not be null");
        Preconditions.checkNotNull(icon, "Icon can not be null");
        Preconditions.checkNotNull(displayName, "Display name can not be null");
    }

    /**
     * Advancement display builder.
     */
    public static class AdvancementDisplayImplBuilder {

        public AdvancementDisplay build() {
            return new AdvancementDisplayImpl(
                    frame, title, description, icon, doesShowToast, doesAnnounceToChat, isHidden, backgroundPath, displayName
            );
        }

    }

}
