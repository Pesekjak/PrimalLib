package org.machinemc.primallib.event.configuration;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Is called when a {@link Player} receives a {@link Registry}.
 * <p>
 * Should only be used for packet/clientside related stuff.
 * Not intended for modifying server side state.
 */
@Getter
@VersionDependant
public class PlayerRegistryDataEvent extends PlayerEvent {

    /**
     * Trim material registry key.
     */
    public static Key TRIM_MATERIAL = Key.key("minecraft:trim_material");

    /**
     * Trim pattern registry key.
     */
    public static Key TRIM_PATTERN = Key.key("minecraft:trim_pattern");

    /**
     * Biome registry key.
     */
    public static Key BIOME = Key.key("minecraft:worldgen/biome");

    /**
     * Chat type registry key.
     */
    public static Key CHAT_TYPE = Key.key("minecraft:chat_type");

    /**
     * Damage type registry key.
     */
    public static Key DAMAGE_TYPE = Key.key("minecraft:damage_type");

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Registry registry;

    public PlayerRegistryDataEvent(Player who, Registry registry) {
        this(who, registry, !Bukkit.isPrimaryThread());
    }

    public PlayerRegistryDataEvent(Player who, Registry registry, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.registry = registry;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
