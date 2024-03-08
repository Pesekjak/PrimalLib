package org.machinemc.primallib.profile;

import com.google.common.base.Preconditions;
import lombok.With;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents information about player session.
 *
 * @param gameProfile game profile
 * @param chatSession chat session
 * @param gameMode gamemode, can lead to unexpected behaviour if it does not match the
 *                 real gamemode, spectator makes the name in tab transparent
 * @param listed whether the player should be visible in tab list
 * @param latency latency, affects number of displayed connection bars
 * @param displayName displayed name in the tab list
 */
@With
public record PlayerInfo(GameProfile gameProfile,
                         @Nullable ChatSession chatSession,
                         GameMode gameMode,
                         boolean listed,
                         int latency,
                         @Nullable Component displayName) {

    /**
     * Latency value for no connection tab icon.
     */
    public static final int NO_CONNECTION = -1;

    /**
     * Latency value for one connection bar icon.
     */
    public static final int ONE_BAR = 1000;

    /**
     * Latency value for two connection bars icon.
     */
    public static final int TWO_BARS = 999;

    /**
     * Latency value for three connection bars icon.
     */
    public static final int THREE_BARS = 599;

    /**
     * Latency value for four connection bars icon.
     */
    public static final int FOUR_BARS = 299;

    /**
     * Latency value for five connection bars icon.
     */
    public static final int FIVE_BARS = 149;

    public PlayerInfo {
        Preconditions.checkNotNull(gameMode, "GameProfile can not be null");
        Preconditions.checkNotNull(gameMode, "GameMode can not be null");
    }

    public PlayerInfo(GameProfile gameProfile, GameMode gameMode, boolean listed, int latency, @Nullable Component displayName) {
        this(gameProfile, null, gameMode, listed, latency, displayName);
    }

    public PlayerInfo(GameProfile gameProfile, GameMode gameMode, boolean listed, int latency) {
        this(gameProfile, gameMode, listed, latency, null);
    }

    public PlayerInfo(GameProfile gameProfile, GameMode gameMode, boolean listed) {
        this(gameProfile, gameMode, listed, FIVE_BARS);
    }

    public PlayerInfo(GameProfile gameProfile, GameMode gameMode) {
        this(gameProfile, gameMode, true);
    }

    /**
     * Returns copy of this player info with different name.
     *
     * @param name name
     * @return copy of this game profile
     */
    @Contract(pure = true)
    public PlayerInfo withName(String name) {
        return withGameProfile(gameProfile.withName(name));
    }

    /**
     * Returns copy of this player info with different UUID.
     *
     * @param uuid uuid
     * @return copy of this game profile
     */
    @Contract(pure = true)
    public PlayerInfo withUUID(UUID uuid) {
        return withGameProfile(gameProfile.withUuid(uuid));
    }

    /**
     * Returns copy of this player info with different player textures.
     *
     * @param playerTextures player textures
     * @return copy of this game profile
     */
    @Contract(pure = true)
    public PlayerInfo withTextures(PlayerTextures playerTextures) {
        return withGameProfile(gameProfile.removeProperty("textures").addProperty(playerTextures.asProperty()));
    }

}
