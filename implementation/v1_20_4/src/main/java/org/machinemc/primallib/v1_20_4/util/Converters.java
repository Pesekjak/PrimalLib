package org.machinemc.primallib.v1_20_4.util;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.netty.buffer.Unpooled;
import io.papermc.paper.advancement.AdvancementDisplay;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import net.kyori.adventure.text.Component;
import net.minecraft.advancements.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R3.CraftFluid;
import org.bukkit.craftbukkit.v1_20_R3.CraftGameEvent;
import org.bukkit.craftbukkit.v1_20_R3.CraftParticle;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntityType;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftNBTTagConfigSerializer;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftNamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.machinemc.primallib.advancement.Advancement;
import org.machinemc.primallib.advancement.AdvancementCriteria;
import org.machinemc.primallib.particle.ConfiguredParticle;
import org.machinemc.primallib.profile.ChatSession;
import org.machinemc.primallib.profile.GameProfile;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

/**
 * Converters between NMS and Bukkit/Paper/PrimalLib classes.
 * <p>
 * {@code fromMinecraft(...)} methods convert NMS classes to Bukkit counterparts.
 * <p>
 * {@code toMinecraft(...)} methods convert Bukkit classes to NMS counterparts.
 */
public final class Converters {

    private Converters() {
        throw new UnsupportedOperationException();
    }

    public static NamespacedKey fromMinecraft(ResourceLocation resourceLocation) {
        if (resourceLocation == null) return null;
        return CraftNamespacedKey.fromMinecraft(resourceLocation);
    }

    public static ResourceLocation toMinecraft(Key key) {
        if (key == null) return null;
        return CraftNamespacedKey.toMinecraft(new NamespacedKey(key.namespace(), key.value()));
    }

    public static EntityType fromMinecraft(net.minecraft.world.entity.EntityType<?> entityType) {
        if (entityType == null) return null;
        return CraftEntityType.minecraftToBukkit(entityType);
    }

    public static net.minecraft.world.entity.EntityType<?> toMinecraft(EntityType entityType) {
        if (entityType == null) return null;
        return CraftEntityType.bukkitToMinecraft(entityType);
    }

    public static Component fromMinecraft(net.minecraft.network.chat.Component component) {
        if (component == null) return null;
        return PaperAdventure.asAdventure(component);
    }

    public static net.minecraft.network.chat.Component toMinecraft(Component component) {
        if (component == null) return null;
        return PaperAdventure.asVanilla(component);
    }

    public static ItemStack fromMinecraft(net.minecraft.world.item.ItemStack itemStack) {
        if (itemStack == null) return null;
        return CraftItemStack.asCraftMirror(itemStack);
    }

    public static net.minecraft.world.item.ItemStack toMinecraft(ItemStack itemStack) {
        if (itemStack == null) return null;
        return CraftItemStack.asNMSCopy(itemStack);
    }

    public static BlockData fromMinecraft(net.minecraft.world.level.block.state.BlockState blockState) {
        if (blockState == null) return null;
        return CraftBlockData.createData(blockState);
    }

    public static BlockState toMinecraft(BlockData blockData) {
        if (blockData == null) return null;
        return CraftBlockData.newData(null, blockData.getAsString()).getState();
    }

    public static Fluid fromMinecraft(net.minecraft.world.level.material.Fluid fluid) {
        if (fluid == null) return null;
        return CraftFluid.minecraftToBukkit(fluid);
    }

    public static net.minecraft.world.level.material.Fluid toMinecraft(Fluid fluid) {
        if (fluid == null) return null;
        return CraftFluid.bukkitToMinecraft(fluid);
    }

    public static GameEvent fromMinecraft(net.minecraft.world.level.gameevent.GameEvent gameEvent) {
        if (gameEvent == null) return null;
        return CraftGameEvent.minecraftToBukkit(gameEvent);
    }

    public static net.minecraft.world.level.gameevent.GameEvent toMinecraft(GameEvent gameEvent) {
        if (gameEvent == null) return null;
        return CraftGameEvent.bukkitToMinecraft(gameEvent);
    }

    public static CompoundBinaryTag fromMinecraft(CompoundTag compoundTag) {
        if (compoundTag == null) return null;
        try {
            return TagStringIO.get().asCompound(CraftNBTTagConfigSerializer.serialize(compoundTag));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static CompoundTag toMinecraft(CompoundBinaryTag compoundBinaryTag) {
        if (compoundBinaryTag == null) return null;
        try {
            return (CompoundTag) CraftNBTTagConfigSerializer.deserialize(TagStringIO.get().asString(compoundBinaryTag));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static GameMode fromMinecraft(GameType gameType) {
        if (gameType == null) return null;
        return GameMode.getByValue(gameType.getId());
    }

    @SuppressWarnings("UnstableApiUsage")
    public static GameType toMinecraft(GameMode gameMode) {
        if (gameMode == null) return null;
        return GameType.byId(gameMode.getValue());
    }

    public static ConfiguredParticle fromMinecraft(ParticleOptions particleOptions) {
        if (particleOptions == null) return null;
        return fromMinecraft(particleOptions, null);
    }

    public static ConfiguredParticle fromMinecraft(ParticleOptions particleOptions, @Nullable World world) {
        if (particleOptions == null) return null;
        Particle particle = CraftParticle.minecraftToBukkit(particleOptions.getType());
        Object options = switch (particleOptions) {
            case ItemParticleOption itemParticle -> fromMinecraft(itemParticle.getItem());
            case BlockParticleOption blockParticle -> fromMinecraft(blockParticle.getState());
            case DustParticleOptions dustParticle -> {
                Vector3f color = dustParticle.getColor();
                yield new Particle.DustOptions(
                        Color.fromRGB((int) (color.x * 255), (int) (color.y * 255), (int) (color.z * 255)),
                        dustParticle.getScale()
                );
            }
            case DustColorTransitionOptions dustColorTransition -> {
                final Vector3f from = dustColorTransition.getFromColor();
                final Vector3f to = dustColorTransition.getToColor();
                yield new Particle.DustTransition(
                        Color.fromRGB((int) (from.x * 255), (int) (from.y * 255), (int) (from.z * 255)),
                        Color.fromRGB((int) (to.x * 255), (int) (to.y * 255), (int) (to.z * 255)),
                        dustColorTransition.getScale()
                );
            }
            case VibrationParticleOption vibrationParticle -> {
                CraftWorld craftWorld = (CraftWorld) (world != null ? world : Bukkit.getWorlds().getFirst());
                Vec3 destination = vibrationParticle.getDestination().getPosition(craftWorld.getHandle()).orElse(Vec3.ZERO);
                Location location = new Location(craftWorld, destination.x, destination.y, destination.z);
                yield new Vibration(new Vibration.Destination.BlockDestination(location), vibrationParticle.getArrivalInTicks());
            }
            case SculkChargeParticleOptions sculkChargeParticle -> sculkChargeParticle.roll();
            case ShriekParticleOption shriekParticle -> shriekParticle.getDelay();
            default -> null;
        };

        return new ConfiguredParticle(particle, options);
    }

    public static net.minecraft.core.particles.ParticleOptions toMinecraft(ConfiguredParticle particle) {
        if (particle == null) return null;
        return CraftParticle.createParticleParam(particle.particle(), particle.options());
    }

    public static Advancement fromMinecraft(AdvancementHolder holder) {
        if (holder == null) return null;
        DisplayInfo displayInfo = holder.value().display().orElse(null);

        Key key = fromMinecraft(holder.id());
        Key parent = holder.value().parent().map(Converters::fromMinecraft).orElse(null);
        var display = displayInfo != null ? displayInfo.paper : null;
        var criteria = AdvancementCriteria.of(holder.value().requirements().requirements());
        float x = displayInfo != null ? displayInfo.getX() : 0;
        float y = displayInfo != null ? displayInfo.getY() : 0;
        return new Advancement(key, parent, display, criteria, x, y);
    }

    public static AdvancementHolder toMinecraft(Advancement advancement) {
        if (advancement == null) return null;
        ResourceLocation resourceLocation = toMinecraft(advancement.key());

        Optional<ResourceLocation> parent = advancement.parent() != null ? Optional.of(toMinecraft(advancement.parent().key())) : Optional.empty();

        Optional<DisplayInfo> display = advancement.display() != null
                ? Optional.of(toMinecraft(advancement.display(), advancement.x(), advancement.y()))
                : Optional.empty();

        Optional<net.minecraft.network.chat.Component> displayName = advancement.display() != null
                ? Optional.of(toMinecraft(advancement.display().displayName()))
                : Optional.empty();

        net.minecraft.advancements.Advancement minecraftAdvancement = new net.minecraft.advancements.Advancement(
                parent,
                display,
                AdvancementRewards.EMPTY,
                Collections.emptyMap(),
                new AdvancementRequirements(advancement.criteria().requirements()),
                false,
                displayName
        );

        return new AdvancementHolder(resourceLocation, minecraftAdvancement);
    }

    public static AdvancementDisplay fromMinecraft(DisplayInfo displayInfo) {
        if (displayInfo == null) return null;
        return displayInfo.paper;
    }

    public static DisplayInfo toMinecraft(AdvancementDisplay advancementDisplay, float x, float y) {
        if (advancementDisplay == null) return null;
        NamespacedKey background = advancementDisplay.backgroundPath();
        DisplayInfo displayInfo = new DisplayInfo(
                toMinecraft(advancementDisplay.icon()),
                toMinecraft(advancementDisplay.title()),
                toMinecraft(advancementDisplay.description()),
                background != null ? Optional.of(toMinecraft(background)) : Optional.empty(),
                switch (advancementDisplay.frame()) {
                    case GOAL -> AdvancementType.GOAL;
                    case TASK -> AdvancementType.TASK;
                    case CHALLENGE -> AdvancementType.CHALLENGE;
                },
                advancementDisplay.doesShowToast(),
                advancementDisplay.doesAnnounceToChat(),
                advancementDisplay.isHidden()
        );
        displayInfo.setLocation(x, y);
        return displayInfo;
    }

    public static org.machinemc.primallib.advancement.AdvancementProgress fromMinecraft(AdvancementProgress progress) {
        if (progress == null) return null;
        var completed = ImmutableList.copyOf(progress.getCompletedCriteria());
        var required = new ArrayList<>(completed);
        required.addAll(ImmutableList.copyOf(progress.getRemainingCriteria()));

        List<org.machinemc.primallib.advancement.Criterion> criteria = completed.stream()
                .map(c -> {
                    CriterionProgress criterionProgress = progress.getCriterion(c);
                    if (criterionProgress == null) return null;
                    Instant obtained = criterionProgress.getObtained();
                    if (obtained == null) return null;
                    return new org.machinemc.primallib.advancement.Criterion(c, Date.from(obtained));
                })
                .filter(Objects::nonNull)
                .toList();

        return org.machinemc.primallib.advancement.AdvancementProgress.from(new HashSet<>(criteria), required);
    }

    public static AdvancementProgress toMinecraft(org.machinemc.primallib.advancement.AdvancementProgress progress) {
        if (progress == null) return null;
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());

        Map<String, CriterionProgress> criteriaProgresses = new HashMap<>();
        progress.completedCriteria().forEach(c -> criteriaProgresses.put(c.name(), new CriterionProgress(c.achieved().toInstant())));

        buf.writeMap(
                criteriaProgresses,
                FriendlyByteBuf::writeUtf,
                (friendlyByteBuf, criterionProgress) -> criterionProgress.serializeToNetwork(friendlyByteBuf)
        );

        return AdvancementProgress.fromNetwork(buf);
    }

    public static GameProfile fromMinecraft(com.mojang.authlib.GameProfile gameProfile) {
        if (gameProfile == null) return null;
        UUID uuid = gameProfile.getId();
        String name = gameProfile.getName();
        List<GameProfile.Property> properties = gameProfile.getProperties().entries().stream()
                .map(Map.Entry::getValue)
                .map(entry -> new GameProfile.Property(entry.name(), entry.value(), entry.signature()))
                .toList();
        return new GameProfile(uuid, name, properties);
    }

    public static com.mojang.authlib.GameProfile toMinecraft(GameProfile gameProfile) {
        if (gameProfile == null) return null;
        var minecraftProfile = new com.mojang.authlib.GameProfile(gameProfile.uuid(), gameProfile.name());
        PropertyMap propertyMap = minecraftProfile.getProperties();
        gameProfile.properties().stream()
                .map(property -> new Property(property.name(), property.value(), property.signature()))
                .forEach(property -> propertyMap.put(property.name(), property));
        return minecraftProfile;
    }

    public static ChatSession fromMinecraft(RemoteChatSession.Data chatSession) {
        if (chatSession == null) return null;
        ProfilePublicKey.Data publicKeyData = chatSession.profilePublicKey();
        return new ChatSession(chatSession.sessionId(), publicKeyData.expiresAt(), publicKeyData.key(), publicKeyData.keySignature());
    }

    public static RemoteChatSession.Data toMinecraft(ChatSession chatSession) {
        if (chatSession == null) return null;
        return new RemoteChatSession.Data(chatSession.id(), new ProfilePublicKey.Data(chatSession.expiresAt(), chatSession.key(), chatSession.signature()));
    }

}
