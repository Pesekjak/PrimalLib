package org.machinemc.primallib.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

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
     *
     * @return entity ID
     */
    int entityID();

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

}

record EntityLikeImpl(int entityID, @Nullable UUID uuid, @Nullable String username) implements EntityLike {

    public EntityLikeImpl(Entity entity) {
        this(entity.getEntityId(), entity.getUniqueId(), entity instanceof Player player ? player.getName() : null);
    }

}
