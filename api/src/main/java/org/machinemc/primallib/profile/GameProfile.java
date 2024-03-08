package org.machinemc.primallib.profile;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import lombok.With;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Mojang game profile.
 *
 * @param uuid id of the profile
 * @param name name of the profile
 * @param properties properties
 * @see MojangService
 */
@With
public record GameProfile(UUID uuid, String name, List<Property> properties) {

    /**
     * Returns new game profile from UUID, name and textures.
     *
     * @param uuid uuid
     * @param name name
     * @param textures textures of the game profile
     * @return game profile
     */
    public static GameProfile withTextures(UUID uuid, String name, PlayerTextures textures) {
        return new GameProfile(uuid, name, List.of(textures.asProperty()));
    }

    /**
     * Creates a new Mojang game profile.
     *
     * @param uuid the UUID for the profile
     * @param name the profile's username
     * @param properties properties for the profile
     */
    public GameProfile {
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(name, "Name can not be null");
        Preconditions.checkNotNull(properties, "Properties can not be null");
        properties = ImmutableList.copyOf(properties);
    }

    /**
     * Creates a new GameProfile with the properties of this one and the specified properties.
     *
     * @param properties the properties to add
     * @return new GameProfile
     */
    @Contract(pure = true)
    public GameProfile addProperties(Iterable<Property> properties) {
        List<Property> newProperties = ImmutableList.<Property>builder()
                .addAll(this.properties)
                .addAll(properties)
                .build();
        return withProperties(newProperties);
    }

    /**
     * Creates a new GameProfile with the properties of this one and the specified property.
     *
     * @param property the property to add
     * @return new GameProfile
     */
    @Contract(pure = true)
    public GameProfile addProperty(Property property) {
        return addProperties(Collections.singleton(property));
    }

    /**
     * Returns copy of this game profile without properties with given name.
     *
     * @param name name of the properties to remove
     * @return copy of this game profile
     */
    @Contract(pure = true)
    public GameProfile removeProperty(String name) {
        return withProperties(properties.stream().filter(property -> !property.name.equals(name)).toList());
    }


    /**
     * Creates offline mode GameProfile from a player's nickname the same way Notchian server does.
     *
     * @param username player's nickname
     * @return GameProfile suitable for offline mode player
     */
    public static GameProfile forOfflinePlayer(String username) {
        Preconditions.checkNotNull(username, "Username can not be null");
        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
        return new GameProfile(uuid, username, Collections.emptyList());
    }

    /**
     * Represents a Mojang profile property.
     *
     * @param name name of the property
     * @param value value of the property
     * @param signature signature of the property, can be null if the property is unsigned
     */
    public record Property(String name, String value, @Nullable String signature) {

        public Property(String name, String value) {
            this(name, value, null);
        }

        public Property {
            Preconditions.checkNotNull(name, "Name of the property can not be null");
            Preconditions.checkNotNull(value, "Value of the property can not be null");
        }

    }
}
