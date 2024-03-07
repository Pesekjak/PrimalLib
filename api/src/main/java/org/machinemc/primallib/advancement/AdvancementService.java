package org.machinemc.primallib.advancement;

import com.google.common.base.Preconditions;
import io.papermc.paper.advancement.AdvancementDisplay;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.intellij.lang.annotations.Subst;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.util.OwnerPlugin;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service that can manage client-side advancements for players.
 */
@VersionDependant
public abstract class AdvancementService extends AutoRegisteringService<AdvancementService> {

    /**
     * Returns instance of advancement service for currently running server.
     *
     * @return advancement service
     */
    public static AdvancementService get() {
        var provider = Bukkit.getServicesManager().getRegistration(AdvancementService.class);
        Preconditions.checkNotNull(provider, "Advancement service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Shows custom advancement toast to the player.
     *
     * @param player player to show the toast to
     * @param text text of the advancement toast, if too long it is split between
     *             multiple messages
     * @param icon icon of the toast
     * @param frame frame for the toast, affects displayed text and played sound
     */
    public void showToast(Player player, Component text, ItemStack icon, AdvancementDisplay.Frame frame) {
        Preconditions.checkNotNull(text, "Text of toast can not be null");
        Preconditions.checkNotNull(icon, "Icon of toast can not be null");
        Preconditions.checkNotNull(frame, "Frame of toast can not be null");
        AdvancementDisplay display = Advancement.displayBuilder()
                .frame(frame)
                .title(text)
                .description(Component.empty())
                .icon(icon)
                .displayName(text)
                .doesShowToast(true)
                .doesAnnounceToChat(false)
                .isHidden(false)
                .build();
        @Subst("") String random = String.valueOf(Math.abs(ThreadLocalRandom.current().nextInt()));
        Advancement advancement = new Advancement(Key.key("primal_lib", "t_" + random), null, display, AdvancementCriteria.single());
        sendAdvancements(player, advancement);
        Bukkit.getScheduler().runTaskLater(OwnerPlugin.get(), () -> complete(player, advancement, Date.from(Instant.now())), 1);
        Bukkit.getScheduler().runTaskLater(OwnerPlugin.get(), () -> removeAdvancements(player, advancement), 2);
    }

    /**
     * Sends advancements to the player.
     * <p>
     * This affects only the visuals of the advancement screen for the player.
     *
     * @param player player to send the advancements for
     * @param advancements advancements
     */
    public void sendAdvancements(Player player, Advancement... advancements) {
        sendAdvancements(player, List.of(advancements));
    }

    /**
     * Sends advancements to the player.
     * <p>
     * This affects only the visuals of the advancement screen for the player.
     *
     * @param player player to send the advancements for
     * @param advancements advancements
     */
    public abstract void sendAdvancements(Player player, Collection<Advancement> advancements);

    /**
     * Removes advancements from player.
     * <p>
     * This affects only the visuals of the advancement screen for the player.
     * If real, removed advancement is completed, player is still being rewarded
     * even if the advancement has been removed.
     *
     * @param player player to remove the advancements from
     * @param advancements advancements
     */
    public void removeAdvancements(Player player, Advancement... advancements) {
        removeAdvancements(player, Arrays.stream(advancements).map(Advancement::key).toList());
    }

    /**
     * Removes advancements with given key from player.
     * <p>
     * This affects only the visuals of the advancement screen for the player.
     * If real, removed advancement is completed, player is still being rewarded
     * even if the advancement has been removed.
     *
     * @param player player to remove the advancements from
     * @param names keys of the advancements
     */
    public void removeAdvancements(Player player, Key... names) {
        removeAdvancements(player, List.of(names));
    }

    /**
     * Removes advancements with given key from player.
     * <p>
     * This affects only the visuals of the advancement screen for the player.
     * If real, removed advancement is completed, player is still being rewarded
     * even if the advancement has been removed.
     *
     * @param player player to remove the advancements from
     * @param names keys of the advancements
     */
    public abstract void removeAdvancements(Player player, Collection<Key> names);

    /**
     * Returns all advancements currently visible by the player.
     *
     * @return advancements of the player
     */
    public abstract List<Advancement> getAdvancements(Player player);

    /**
     * Returns advancement with given key currently visible by the player.
     *
     * @param player player
     * @param key key of the advancement
     * @return advancement
     */
    public abstract Optional<Advancement> getAdvancement(Player player, Key key);

    /**
     * Completes advancement for the player.
     *
     * @param player player
     * @param advancement advancement to complete
     * @param date date of completion
     */
    public void complete(Player player, Advancement advancement, Date date) {
        AdvancementProgress progress = AdvancementProgress.from(advancement.criteria(), advancement.criteria().required(), date);
        sendProgress(player, advancement, progress);
    }

    /**
     * Updates progress of player's advancement.
     *
     * @param player player to update the progress for
     * @param advancement advancement
     * @param progress new progress
     */
    public void sendProgress(Player player, Advancement advancement, AdvancementProgress progress) {
        sendProgress(player, advancement.key(), progress);
    }

    /**
     * Updates progress of player's advancement.
     *
     * @param player player to update the progress for
     * @param name key of the advancement
     * @param progress new progress
     */
    public void sendProgress(Player player, Key name, AdvancementProgress progress) {
        sendProgress(player, Map.of(name, progress));
    }

    /**
     * Updates progress of player's advancements.
     *
     * @param player player to update the progress for
     * @param progress map of advancement keys and their new progress
     */
    public abstract void sendProgress(Player player, Map<Key, AdvancementProgress> progress);

    @Override
    public Class<AdvancementService> getRegistrationClass() {
        return AdvancementService.class;
    }

}
