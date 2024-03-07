package org.machinemc.primallib.advancement;

import io.papermc.paper.advancement.AdvancementDisplay;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

public class AdvancementTest {

    @Test
    public void advancementDisplayBuilder() {
        AdvancementDisplay display = Advancement.displayBuilder()
                .frame(AdvancementDisplay.Frame.GOAL)
                .title(Component.text("Hello World!"))
                .description(Component.text("Foo Bar"))
                .icon(new ItemStack(Material.STONE))
                .displayName(Component.text("Foo"))
                .build();

        assert display.frame() == AdvancementDisplay.Frame.GOAL;
        assert display.displayName().equals(Component.text("Foo"));
        assert !display.doesAnnounceToChat();
    }

}
