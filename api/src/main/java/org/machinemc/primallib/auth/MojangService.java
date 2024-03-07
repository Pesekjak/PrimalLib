package org.machinemc.primallib.auth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.util.UUIDUtils;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Util class for easier operations with Mojang API.
 */
public final class MojangService {

    private static final String USER_PROFILE_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String MINECRAFT_PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    private MojangService() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns online UUID of registered account.
     *
     * @param username username of the account
     * @return online UUID of account, empty if the account doesn't exist
     */
    public static CompletableFuture<Optional<UUID>> getUUID(@Nullable String username) {
        if (username == null) return CompletableFuture.completedFuture(Optional.empty());
        String url = String.format(USER_PROFILE_URL, username);
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder(new URI(url))
                        .GET()
                        .build();
                try (HttpClient client = HttpClient.newHttpClient()) {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (!(response.statusCode() == HttpURLConnection.HTTP_OK)) return Optional.empty();
                    JsonObject json = (JsonObject) JsonParser.parseString(response.body());
                    return UUIDUtils.parseUUID(json.get("id").getAsString());
                }
            } catch (Exception ignored) { }
            return Optional.empty();
        });
    }

    /**
     * Returns game profile of registered account.
     *
     * @param uuid online uuid of the account
     * @return game profile of the registered account, empty if the account doesn't exist
     */
    public CompletableFuture<Optional<GameProfile>> getGameProfile(@Nullable UUID uuid) {
        if (uuid == null) return CompletableFuture.completedFuture(Optional.empty());
        String url = String.format(MINECRAFT_PROFILE_URL, uuid);
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder(new URI(url))
                        .GET().build();
                try (HttpClient client = HttpClient.newHttpClient()) {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (!(response.statusCode() == HttpURLConnection.HTTP_OK)) return Optional.empty();
                    JsonObject json = (JsonObject) JsonParser.parseString(response.body());
                    GameProfile gameProfile = parseGameProfile(json);
                    return Optional.of(gameProfile);
                }
            } catch (Exception ignored) { }
            return Optional.empty();
        });
    }

    /**
     * Returns game profile of registered account.
     *
     * @param username username of the account
     * @return game profile of the registered account, empty if the account doesn't exist
     */
    public CompletableFuture<Optional<GameProfile>> getGameProfile(@Nullable String username) {
        if (username == null) return CompletableFuture.completedFuture(Optional.empty());
        return getUUID(username).thenCompose(uuid -> getGameProfile(uuid.orElse(null)));
    }

    private static GameProfile parseGameProfile(JsonObject json) {
        UUID authUUID = UUIDUtils.parseUUID(json.get("id").getAsString()).orElseThrow();
        String authUsername = json.get("name").getAsString();
        List<GameProfile.Property> properties = new ArrayList<>();
        json.getAsJsonArray("properties").asList().stream()
                .map(JsonElement::getAsJsonObject)
                .forEach(property -> {
                    String name = property.get("name").getAsString();
                    String value = property.get("value").getAsString();
                    String signature = property.has("signature")
                            ? property.get("signature").getAsString()
                            : null;
                    properties.add(new GameProfile.Property(name, value, signature));
                });
        return new GameProfile(authUUID, authUsername, properties);
    }

}
