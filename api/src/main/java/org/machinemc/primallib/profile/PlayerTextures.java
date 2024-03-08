package org.machinemc.primallib.profile;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.util.Optional;

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
                             org.bukkit.profile.PlayerTextures.SkinModel skinModel) {

    public PlayerTextures {
        Preconditions.checkNotNull(value, "Value of the skin can not be null");
        Preconditions.checkNotNull(skinURL, "Skin url can not be null");
        Preconditions.checkNotNull(skinModel, "Skin model can not be null");
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
                .filter(p -> p.name().equals("textures"))
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
        if (!property.name().equals("textures"))
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

        JsonObject textures = decoded.getAsJsonObject().getAsJsonObject("textures");
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
            skinModel = org.bukkit.profile.PlayerTextures.SkinModel.CLASSIC;
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
        return new GameProfile.Property("textures", value, signature);
    }

}
