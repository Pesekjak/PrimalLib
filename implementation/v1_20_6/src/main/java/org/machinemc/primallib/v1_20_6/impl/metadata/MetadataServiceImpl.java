package org.machinemc.primallib.v1_20_6.impl.metadata;

import io.papermc.paper.math.Position;
import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Rotations;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import org.bukkit.Art;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.machinemc.primallib.entity.ArmadilloState;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.entity.VillagerData;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.MetadataService;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.particle.ConfiguredParticle;
import org.machinemc.primallib.v1_20_6.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_20_6.util.Converters;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings({"unchecked", "UnstableApiUsage"})
public class MetadataServiceImpl extends MetadataService {

    @Override
    public void sendMetadata(Player player, EntityLike target, EntityData data) {
        List<SynchedEntityData.DataValue<?>> packedItems = new ArrayList<>();
        data.getMap().forEach((field, value) ->
                packedItems.add(new SynchedEntityData.DataValue<>(
                        field.index(),
                        (EntityDataSerializer<Object>) Converters.toMinecraft(field.serializer()),
                        convert(field.serializer(), value)
                ))
        );
        PacketChannelHandlerImpl.sendPacket(player, new ClientboundSetEntityDataPacket(target.entityID(), packedItems), false);
    }

    private static final Map<Serializer<?>, Function<Object, Object>> converters;

    static {
        converters = new HashMap<>();
        converters.put(Serializer.BYTE, b -> b);
        converters.put(Serializer.INT, i -> i);
        converters.put(Serializer.LONG, l -> l);
        converters.put(Serializer.FLOAT, f -> f);
        converters.put(Serializer.STRING, s -> s);
        converters.put(Serializer.COMPONENT, c -> Converters.toMinecraft((Component) c));
        converters.put(Serializer.OPTIONAL_COMPONENT, c -> ((Optional<Component>) c).map(Converters::toMinecraft));
        converters.put(Serializer.SLOT, slot -> Converters.toMinecraft((ItemStack) slot));
        converters.put(Serializer.BLOCK_STATE, blockState -> Converters.toMinecraft((BlockData) blockState));
        converters.put(Serializer.OPTIONAL_BLOCK_STATE, blockState -> ((Optional<BlockData>) blockState).map(Converters::toMinecraft));
        converters.put(Serializer.PARTICLE, particle -> Converters.toMinecraft((ConfiguredParticle) particle));
        converters.put(Serializer.PARTICLES, particles -> ((List<ConfiguredParticle>) particles).stream().map(Converters::toMinecraft).toList());
        converters.put(Serializer.BOOLEAN, b -> b);
        converters.put(Serializer.ROTATIONS, rotations -> {
            EulerAngle angle = (EulerAngle) rotations;
            return new Rotations(
                    (float) Math.toDegrees(angle.getX()),
                    (float) Math.toDegrees(angle.getY()),
                    (float) Math.toDegrees(angle.getZ())
            );
        });
        converters.put(Serializer.POSITION, pos -> Converters.toMinecraft(((Position) pos).toBlock()));
        converters.put(Serializer.OPTIONAL_POSITION, pos -> ((Optional<Position>) pos).map(Position::toBlock).map(Converters::toMinecraft));
        converters.put(Serializer.DIRECTION, direction -> {
            BlockFace face = (BlockFace) direction;
            return switch (face) {
                case UP -> Direction.UP;
                case NORTH -> Direction.NORTH;
                case SOUTH -> Direction.SOUTH;
                case WEST -> Direction.WEST;
                case EAST -> Direction.EAST;
                default -> Direction.DOWN;
            };
        });
        converters.put(Serializer.OPTIONAL_UUID, uuid -> uuid);
        converters.put(Serializer.OPTIONAL_GLOBAL_POSITION, pos -> ((Optional<Pair<World, Position>>) pos)
                .map(p -> new GlobalPos(
                        ((CraftWorld) p.first()).getHandle().dimension(),
                        Converters.toMinecraft(p.second().toBlock())
                ))
        );
        converters.put(Serializer.NBT, tag -> Converters.toMinecraft((CompoundBinaryTag) tag));
        converters.put(Serializer.VILLAGER_DATA, data -> Converters.toMinecraft((VillagerData) data));
        converters.put(Serializer.OPTIONAL_INT, i -> {
            Optional<Integer> value = (Optional<Integer>) i;
            return value.map(OptionalInt::of).orElseGet(OptionalInt::empty);
        });
        converters.put(Serializer.POSE, pose -> Converters.toMinecraft((Pose) pose));
        converters.put(Serializer.CAT_VARIANT, type -> Holder.direct(Converters.toMinecraft((Cat.Type) type)));
        converters.put(Serializer.WOLF_VARIANT, variant -> Holder.direct(Converters.toMinecraft((Wolf.Variant) variant)));
        converters.put(Serializer.FROG_VARIANT, variant -> Holder.direct(Converters.toMinecraft((Frog.Variant) variant)));
        converters.put(Serializer.PAINTING_VARIANT, variant -> Holder.direct(Converters.toMinecraft((Art) variant)));
        converters.put(Serializer.ARMADILLO_STATE, state -> Converters.toMinecraft((ArmadilloState) state));
        converters.put(Serializer.SNIFFER_STATE, state -> Converters.toMinecraft((Sniffer.State) state));
        converters.put(Serializer.VECTOR3, v -> v);
        converters.put(Serializer.QUATERNION, q -> q);
    }

    public Object convert(Serializer<?> serializer, Object from) {
        return converters.get(serializer).apply(from);
    }

}
