package org.machinemc.primallib.profile;

import com.google.common.base.Preconditions;
import lombok.With;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Optional;
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

    public PlayerInfo(GameProfile gameProfile) {
        this(gameProfile, GameMode.SURVIVAL);
    }

    /**
     * Returns UUID of this player info.
     *
     * @return uuid
     */
    public UUID getUUID() {
        return gameProfile.uuid();
    }

    /**
     * Returns name of this player info.
     *
     * @return name
     */
    public String getName() {
        return gameProfile.name();
    }

    /**
     * Returns properties of this player info.
     *
     * @return properties
     */
    public @Unmodifiable List<GameProfile.Property> getProperties() {
        return gameProfile.properties();
    }

    /**
     * Returns player textures of this player info.
     *
     * @return player textures
     */
    public Optional<PlayerTextures> getTextures() {
        try {
            return PlayerTextures.create(gameProfile);
        } catch (Exception exception) {
            return Optional.empty();
        }
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

    /**
     * Returns whether this player info has initialized chat session.
     *
     * @return whether this player info has initialized chat session
     */
    public boolean hasInitializedChat() {
        return chatSession != null;
    }

    /**
     * Returns whether this player info has display name.
     *
     * @return whether this player info has display name
     */
    public boolean hasDisplayName() {
        return displayName != null;
    }

    /**
     * Represents different actions applicable to player infos.
     */
    public enum Action {

        /**
         * Used for add the player info to player, can be also used to override game profile.
         */
        ADD_PLAYER,

        /**
         * Initializes the chat session for the player info, in general this should not be used.
         */
        INITIALIZE_CHAT,

        /**
         * Updates the game mode of the player info.
         */
        UPDATE_GAME_MODE,

        /**
         * Updates the listed attribute of the player info.
         */
        UPDATE_LISTED,

        /**
         * Updates the latency of the player info.
         */
        UPDATE_LATENCY,

        /**
         * Updates the display name of the player info.
         */
        UPDATE_DISPLAY_NAME;

        /**
         * Returns all actions for player info.
         *
         * @return all actions
         */
        public static Action[] all() {
            return values();
        }

        /**
         * Returns all actions that update different attributes of player info.
         *
         * @return update actions
         */
        public static Action[] updates() {
            return new Action[] {UPDATE_GAME_MODE, UPDATE_LISTED, UPDATE_LATENCY, UPDATE_DISPLAY_NAME};
        }

        /**
         * Returns all actions that update different attributes of player info and the add player action.
         * <p>
         * For general use these are the actions to use for update all attributes of player infos,
         * chat sessions should not be touched.
         *
         * @return update and add player actions
         */
        public static Action[] addAndUpdate() {
            return new Action[] {ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LISTED, UPDATE_LATENCY, UPDATE_DISPLAY_NAME};
        }


    }

}
