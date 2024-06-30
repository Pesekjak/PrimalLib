package org.machinemc.primallib.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Represents object that can be used as an entity with
 * Minecraft packet operations.
 */
public interface EntityLike {

    /**
     * Returns new entity like instance from Bukkit entity.
     *
     * @param entity entity
     * @return entity like for given entity
     */
    static EntityLike fromBukkit(Entity entity) {
        return new EntityLikeImpl(entity);
    }

    /**
     * Creates new entity like object from entity ID, UUID and username.
     * <p>
     * Username should be empty in most cases and present only for
     * entity like objects of players.
     *
     * @param entityID entity ID
     * @param uuid UUID
     * @param username player username
     * @return entity like
     */
    static EntityLike of(int entityID, @Nullable UUID uuid, @Nullable String username) {
        return new EntityLikeImpl(entityID, uuid, username);
    }

    /**
     * Entity ID of the entity like object.
     * <p>
     * {@code -1} if there is no ID assigned to this object.
     * <p>
     * If this is based of a server-side entity the ID can be
     * resolved using {@link #resolveEntityID()}.
     *
     * @return entity ID
     */
    int entityID();

    /**
     * Returns entity ID of the entity like and tries to resolve it
     * if there is none assigned to this object and the entity exists
     * on the server.
     *
     * @return entity ID
     */
    default OptionalInt resolveEntityID() {
        int id = entityID();
        if (id >= 0) return OptionalInt.of(id);
        id = getEntity().map(Entity::getEntityId).orElse(-1);
        if (id == -1) return OptionalInt.empty();
        return OptionalInt.of(id);
    }

    /**
     * UUID of the entity like object.
     *
     * @return entity UUID
     */
    @Nullable UUID uuid();

    /**
     * Username of the entity like object.
     * <p>
     * Applies only to player entities.
     *
     * @return username of the entity (player)
     */
    @Nullable String username();

    /**
     * Returns entity instance of this entity like or
     * empty if it does not exist on the server.
     *
     * @return entity for this entity like
     */
    default Optional<Entity> getEntity() {
        UUID uuid = uuid();
        if (uuid != null)
            return Optional.ofNullable(Bukkit.getEntity(uuid));
        String username = username();
        if (username == null) return Optional.empty();
        return Optional.ofNullable(Bukkit.getPlayerExact(username));
    }

    /**
     * Returns the entity like as a string.
     * <p>
     * This prioritizes player names and if there is none
     * available, UUID is used instead.
     *
     * @return string representation
     */
    default String getStringRepresentation() {
        String username = username();
        if (username != null) return username;
        UUID uuid = uuid();
        Preconditions.checkNotNull(uuid);
        return uuid.toString();
    }

}

record EntityLikeImpl(int entityID, @Nullable UUID uuid, @Nullable String username) implements EntityLike {

    public EntityLikeImpl(Entity entity) {
        this(entity.getEntityId(), entity.getUniqueId(), entity instanceof Player player ? player.getName() : null);
    }

    public EntityLikeImpl {
        Preconditions.checkState(
                uuid != null || username != null,
                "UUID or username has to be provided"
        );
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }

}
