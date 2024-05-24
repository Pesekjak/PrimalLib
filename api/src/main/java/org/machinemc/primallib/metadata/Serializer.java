package org.machinemc.primallib.metadata;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.papermc.paper.math.Position;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import org.bukkit.Art;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.machinemc.primallib.entity.ArmadilloState;
import org.machinemc.primallib.entity.VillagerData;
import org.machinemc.primallib.particle.ConfiguredParticle;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Serializer for different possible value types of entity metadata.
 *
 * @param <T> type
 */
@Getter
@SuppressWarnings("UnstableApiUsage")
public final class Serializer<T> {

    /**
     * Serializer for byte values.
     */
    public static final Serializer<Byte> BYTE = create(Byte.class);

    /**
     * Serializer for integer values.
     */
    public static final Serializer<Integer> INT = create(Integer.class);

    /**
     * Serializer for long values.
     */
    public static final Serializer<Long> LONG = create(Long.class);

    /**
     * Serializer for float values.
     */
    public static final Serializer<Float> FLOAT = create(Float.class);

    /**
     * Serializer for string values.
     */
    public static final Serializer<String> STRING = create(String.class);

    /**
     * Serializer for text component values.
     */
    public static final Serializer<Component> COMPONENT = create(Component.class);

    /**
     * Serializer for nullable text component values.
     */
    public static final Serializer<Component> OPTIONAL_COMPONENT = create(Optional.class);

    /**
     * Serializer for slot values.
     */
    public static final Serializer<ItemStack> SLOT = create(ItemStack.class);

    /**
     * Serializer for block state values.
     */
    public static final Serializer<BlockData> BLOCK_STATE = create(BlockData.class);

    /**
     * Serializer for optional block state values.
     */
    public static final Serializer<Optional<BlockData>> OPTIONAL_BLOCK_STATE = create(Optional.class);

    /**
     * Serializer for particle values.
     */
    public static final Serializer<ConfiguredParticle> PARTICLE = create(ConfiguredParticle.class);

    /**
     * Serializer for multiple particle values.
     */
    public static final Serializer<List<ConfiguredParticle>> PARTICLES = create(List.class);

    /**
     * Serializer for boolean values.
     */
    public static final Serializer<Boolean> BOOLEAN = create(Boolean.class);

    /**
     * Serializer for rotation values.
     */
    public static final Serializer<EulerAngle> ROTATIONS = create(EulerAngle.class);

    /**
     * Serializer for position values.
     */
    public static final Serializer<Position> POSITION = create(Position.class);

    /**
     * Serializer for optional position values.
     */
    public static final Serializer<Optional<Position>> OPTIONAL_POSITION = create(Optional.class);

    /**
     * Serializer for optional direction values.
     *
     * @apiNote supports only faces that are aligned with one of
     * the unit axes in 3D Cartesian space
     */
    public static final Serializer<BlockFace> DIRECTION = create(BlockFace.class);

    /**
     * Serializer for optional UUID values.
     */
    public static final Serializer<Optional<UUID>> OPTIONAL_UUID = create(Optional.class);

    /**
     * Serializer for global position values.
     *
     * @apiNote currently unused by the protocol, subject to change
     */
    @SuppressWarnings("all")
    public static final Serializer<Optional<Pair<World, Position>>> OPTIONAL_GLOBAL_POSITION = create(Optional.class);

    /**
     * Serializer for NBT values.
     */
    public static final Serializer<CompoundBinaryTag> NBT = create(CompoundBinaryTag.class);

    /**
     * Serializer for villager data values.
     */
    public static final Serializer<VillagerData> VILLAGER_DATA = create(VillagerData.class);

    /**
     * Serializer for optional integer values.
     *
     * @apiNote the integer values are unsigned
     */
    public static final Serializer<Optional<Integer>> OPTIONAL_INT = create(Optional.class);

    /**
     * Serializer for pose values.
     */
    public static final Serializer<Pose> POSE = create(Pose.class);

    /**
     * Serializer for cat variant values.
     */
    public static final Serializer<Cat.Type> CAT_VARIANT = create(Cat.Type.class);

    /**
     * Serializer for wolf variant values.
     */
    public static final Serializer<Wolf.Variant> WOLF_VARIANT = create(Wolf.Variant.class);

    /**
     * Serializer for frog variant values.
     */
    public static final Serializer<Frog.Variant> FROG_VARIANT = create(Frog.Variant.class);

    /**
     * Serializer for painting variant values.
     */
    public static final Serializer<Art> PAINTING_VARIANT = create(Art.class);

    /**
     * Serializer for armadillo state values.
     */
    public static final Serializer<ArmadilloState> ARMADILLO_STATE = create(ArmadilloState.class);

    /**
     * Serializer for sniffer state values.
     */
    public static final Serializer<Sniffer.State> SNIFFER_STATE = create(Sniffer.State.class);

    /**
     * Serializer for vector3 values.
     */
    public static final Serializer<Vector3f> VECTOR3 = create(Vector3f.class);

    /**
     * Serializer for quaternion values.
     */
    public static final Serializer<Quaternionf> QUATERNION = create(Quaternionf.class);

    private static final BiMap<Serializer<?>, String> serializers;

    static {
        var all = Arrays.stream(Serializer.class.getFields())
                .filter(field -> field.getType().equals(Serializer.class))
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> Modifier.isFinal(field.getModifiers()))
                .map(field -> {
                    try {
                        return (Serializer<?>) field.get(null);
                    } catch (IllegalAccessException exception) {
                        throw new RuntimeException(exception);
                    }
                })
                .toList();
        serializers = HashBiMap.create(all.size());
        Arrays.stream(Serializer.class.getFields())
                .filter(f -> Serializer.class.isAssignableFrom(f.getType()))
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .forEach(f -> {
                    try {
                        Serializer<?> serializer = (Serializer<?>) f.get(null);
                        if (!all.contains(serializer)) return;
                        serializers.put(serializer, f.getName().toLowerCase());
                    } catch (IllegalAccessException ignored) { }
                });
    }

    private final Class<T> type;

    /**
     * Returns serializer by its name which corresponds to the
     * name of the serializer field.
     *
     * @param name name of the serializer
     * @return serializer with given name
     */
    public static Serializer<?> getByName(String name) {
        return Preconditions.checkNotNull(serializers.inverse().get(name.toLowerCase()), "Non-existing serializer");
    }

    /**
     * Returns name of a serializer which corresponds to the
     * name of the serializer field.
     *
     * @param serializer serializer
     * @return name of the serializer
     */
    public static String getName(Serializer<?> serializer) {
        return Preconditions.checkNotNull(serializers.get(serializer), "Invalid serializer");
    }

    @SuppressWarnings("unchecked")
    private static <T> Serializer<T> create(Class<?> type) {
        return (Serializer<T>) new Serializer<>(type);
    }

    private Serializer(Class<T> type) {
        this.type = type;
    }

    /**
     * @return whether the values using this serializer can be empty (optional).
     */
    public boolean isOptional() {
        return type.equals(Optional.class);
    }

    /**
     * @return all serializers
     */
    public static Serializer<?>[] values() {
        return serializers.keySet().toArray(new Serializer[0]);
    }

}
