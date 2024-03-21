package org.machinemc.primallib.profile;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.gson.*;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.util.UUIDUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

/**
 * Represents player's skin textures.
 * @param value texture value
 * @param signature signature of the texture
 * @param skinURL url for the skin
 * @param capeURL url for the cape
 * @param skinModel model of the skin
 */
public record PlayerTextures(String value,
                             @Nullable String signature,
                             URL skinURL,
                             @Nullable URL capeURL,
                             @Nullable org.bukkit.profile.PlayerTextures.SkinModel skinModel) {

    /**
     * Name of the textures game profile property.
     */
    public static final String TEXTURES = "textures";

    /**
     * Returns new instance of Player Textures builder.
     *
     * @return player textures builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns player textures from Bukkit player textures.
     *
     * @param bukkit Bukkit player textures
     * @return player textures
     * @deprecated Bukkit does not expose the signature data, and thus it is lost
     * during the conversion
     */
    @Deprecated
    public static PlayerTextures fromBukkit(org.bukkit.profile.PlayerTextures bukkit) {
        try {
            return builder()
                    .timestamp(Instant.ofEpochMilli(bukkit.getTimestamp()))
                    .skin(bukkit.getSkin())
                    .cape(bukkit.getCape())
                    .build();
        } catch (MalformedURLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    public PlayerTextures {
        Preconditions.checkNotNull(value, "Value of the skin can not be null");
        Preconditions.checkNotNull(skinURL, "Skin url can not be null");
    }

    /**
     * Creates player textures from GameProfile.
     *
     * @param gameProfile GameProfile
     * @return player textures from GameProfile
     * @throws MalformedURLException if the provided skin URL is malformed
     * @throws JsonSyntaxException if the value of the property is not a valid JSON format
     */
    public static Optional<PlayerTextures> create(GameProfile gameProfile) throws MalformedURLException, JsonSyntaxException {
        GameProfile.Property property = gameProfile.properties().stream()
                .filter(p -> p.name().equals(TEXTURES))
                .findFirst()
                .orElse(null);
        if (property == null) return Optional.empty();
        return Optional.of(create(property));
    }

    /**
     * Creates player textures from GameProfile property.
     *
     * @param property property
     * @return player textures from property
     * @throws MalformedURLException if the provided skin URL is malformed
     * @throws JsonSyntaxException if the value of the property is not a valid JSON format
     * @throws IllegalStateException if the provided property is not player textures property
     */
    public static PlayerTextures create(GameProfile.Property property) throws MalformedURLException, JsonSyntaxException {
        if (!property.name().equals(TEXTURES))
            throw new IllegalStateException("Provided property is not player textures property");
        return create(property.value(), property.signature());
    }

    /**
     * Creates player textures from value and signature.
     *
     * @param value value of the skin
     * @param signature signature of the skin
     * @return player textures from property
     * @throws MalformedURLException if the provided skin URL is malformed
     * @throws JsonSyntaxException if the value of the property is not a valid JSON format
     * @throws IllegalStateException if the provided property is not player textures property
     */
    public static PlayerTextures create(String value, @Nullable String signature) throws MalformedURLException, JsonSyntaxException {
        JsonElement decoded = JsonParser.parseString(new String(Base64.getDecoder().decode(value)));
        if (!decoded.isJsonObject())
            throw new JsonSyntaxException("Texture value of the skin contains malformed JSON format");

        JsonObject textures = decoded.getAsJsonObject().getAsJsonObject(TEXTURES);
        JsonObject skinJson = textures.getAsJsonObject("SKIN");

        URL skinURL = URI.create(skinJson.get("url").getAsString()).toURL();
        URL capeURL = textures.has("CAPE")
                ? URI.create(textures.getAsJsonObject("CAPE").get("url").getAsString()).toURL()
                : null;

        org.bukkit.profile.PlayerTextures.SkinModel skinModel;
        try {
            skinModel = org.bukkit.profile.PlayerTextures.SkinModel.valueOf(skinJson.get("metadata")
                    .getAsJsonObject()
                    .get("model")
                    .getAsString()
                    .toUpperCase());
        } catch (Exception exception) {
            skinModel = null;
        }

        return new PlayerTextures(value, signature, skinURL, capeURL, skinModel);
    }

    /**
     * Applies this player textures to bukkit player textures.
     *
     * @param bukkit bukkit player textures
     */
    public void apply(org.bukkit.profile.PlayerTextures bukkit) {
        bukkit.setSkin(skinURL, skinModel);
        bukkit.setCape(capeURL);
    }

    /**
     * Returns the player textures as game profile property.
     *
     * @return property for these textures
     */
    public GameProfile.Property asProperty() {
        return new GameProfile.Property(TEXTURES, value, signature);
    }

    /**
     * Returns this player textures as Bukkit player textures.
     *
     * @return player textures
     * @deprecated Bukkit does not expose the signature data, and thus it is lost
     * during the conversion
     */
    @Deprecated
    public org.bukkit.profile.PlayerTextures asBukkit() {
        GameProfile gameProfile = new GameProfile(new UUID(0, 0), "", List.of(asProperty()));
        return gameProfile.asPaper().getTextures();
    }

    /**
     * Builder class for Player Textures.
     * <p>
     * To create signed player textures use {@link PlayerTextures#create(String, String)}.
     *
     * @apiNote Signing the texture might not work because signature is for the JSON text,
     * which might have different formatting than JSON provided by the builder.
     */
    @Setter
    @Accessors(fluent = true, chain = true)
    public static class Builder {

        private static final Gson GSON = new Gson();

        @ApiStatus.Experimental
        private @Nullable String signature;

        private @Nullable Instant timestamp = Instant.now();
        private @Nullable UUID profileID = new UUID(0, 0);
        private @Nullable String name = "";

        private URL skin;
        private @Nullable URL cape;

        private @Nullable org.bukkit.profile.PlayerTextures.SkinModel skinModel;

        private Builder() {
        }

        /**
         * Creates the player textures from this builder.
         *
         * @return player textures
         * @throws MalformedURLException if the skin URL is malformed
         */
        public PlayerTextures build() throws MalformedURLException {
            return PlayerTextures.create(createProperty());
        }

        private GameProfile.Property createProperty() {
            Preconditions.checkNotNull(skin, "Skin URL can not be null");
            JsonObject value = new JsonObject();

            if (timestamp != null) value.addProperty("timestamp", timestamp.toEpochMilli());
            if (profileID != null) value.addProperty("profileId", UUIDUtils.undashedUUID(profileID));
            if (name != null) value.addProperty("profileName", name);

            value.addProperty("signatureRequired", signature != null);

            JsonObject textures = getTexturesJSON();

            value.add(TEXTURES, textures);

            String valueJSON = GSON.toJson(value);
            String valueString = BaseEncoding.base64().encode(valueJSON.getBytes(StandardCharsets.UTF_8));

            return new GameProfile.Property(TEXTURES, valueString, signature);
        }

        private JsonObject getTexturesJSON() {
            JsonObject textures = new JsonObject();

            JsonObject skin = new JsonObject();
            skin.addProperty("url", this.skin.toString());
            if (skinModel != null) {
                JsonObject metadata = new JsonObject();
                metadata.addProperty("model", skinModel.name().toLowerCase());
                skin.add("metadata", metadata);
            }
            textures.add("SKIN", skin);

            if (cape != null) {
                JsonObject cape = new JsonObject();
                cape.addProperty("url", this.cape.toString());
                textures.add("CAPE", cape);
            }
            return textures;
        }

    }

}
