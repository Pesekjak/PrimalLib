package org.machinemc.primallib.v1_21.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.netty.buffer.Unpooled;
import io.papermc.paper.advancement.AdvancementDisplay;
import io.papermc.paper.adventure.PaperAdventure;
import io.papermc.paper.math.BlockPosition;
import io.papermc.paper.math.Position;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import net.kyori.adventure.text.Component;
import net.minecraft.advancements.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.*;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import org.bukkit.craftbukkit.entity.CraftVillager;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftNBTTagConfigSerializer;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.machinemc.primallib.advancement.Advancement;
import org.machinemc.primallib.advancement.AdvancementCriteria;
import org.machinemc.primallib.entity.ArmadilloState;
import org.machinemc.primallib.entity.VillagerData;
import org.machinemc.primallib.event.configuration.Registry;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.particle.ConfiguredParticle;
import org.machinemc.primallib.profile.ChatSession;
import org.machinemc.primallib.profile.GameProfile;
import org.machinemc.primallib.profile.PlayerInfo;

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

    @SuppressWarnings("UnstableApiUsage")
    public static BlockPosition fromMinecraft(BlockPos blockPos) {
        return Position.block(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @SuppressWarnings("UnstableApiUsage")
    public static BlockPos toMinecraft(BlockPosition position) {
        return new BlockPos(position.blockX(), position.blockY(), position.blockZ());
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

    public static BlockData fromMinecraft(BlockState blockState) {
        if (blockState == null) return null;
        return CraftBlockData.createData(blockState);
    }

    @SuppressWarnings("UnstableApiUsage")
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

    public static ParticleOptions toMinecraft(ConfiguredParticle particle) {
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

    public static PlayerInfo fromMinecraft(ClientboundPlayerInfoUpdatePacket.Entry entry,
                                    @Nullable PlayerInfo parent,
                                    Collection<ClientboundPlayerInfoUpdatePacket.Action> actions) {
        if (entry == null) return null;

        if (parent != null) Preconditions.checkState(entry.profileId().equals(parent.getUUID()));
        if (parent == null && entry.profile() == null) throw new IllegalArgumentException();

        GameProfile gp = entry.profile() != null
                ? fromMinecraft(entry.profile())
                : parent.gameProfile();

        PlayerInfo playerInfo = new PlayerInfo(gp);

        if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER))
            playerInfo = playerInfo.withGameProfile(Converters.fromMinecraft(entry.profile()));

        if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.INITIALIZE_CHAT))
            playerInfo = playerInfo.withChatSession(Converters.fromMinecraft(entry.chatSession()));

        if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE))
            playerInfo = playerInfo.withGameMode(Converters.fromMinecraft(entry.gameMode()));

        if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED))
            playerInfo = playerInfo.withListed(entry.listed());

        if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY))
            playerInfo = playerInfo.withLatency(entry.latency());

        if (actions.contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME))
            playerInfo = playerInfo.withDisplayName(Converters.fromMinecraft(entry.displayName()));

        return playerInfo;
    }

    public static ClientboundPlayerInfoUpdatePacket.Entry toMinecraft(PlayerInfo playerInfo) {
        if (playerInfo == null) return null;
        UUID uuid = playerInfo.getUUID();
        com.mojang.authlib.GameProfile gameProfile = Converters.toMinecraft(playerInfo.gameProfile());
        boolean listed = playerInfo.listed();
        int latency = playerInfo.latency();
        GameType gameType = Converters.toMinecraft(playerInfo.gameMode());
        net.minecraft.network.chat.Component displayName = playerInfo.hasDisplayName()
                ? Converters.toMinecraft(playerInfo.displayName())
                : null;
        RemoteChatSession.Data session = playerInfo.hasInitializedChat()
                ? Converters.toMinecraft(playerInfo.chatSession())
                : null;
        return new ClientboundPlayerInfoUpdatePacket.Entry(uuid, gameProfile, listed, latency, gameType, displayName, session);
    }

    public static PlayerInfo.Action fromMinecraft(ClientboundPlayerInfoUpdatePacket.Action action) {
        if (action == null) return null;
        return switch (action) {
            case ADD_PLAYER -> PlayerInfo.Action.ADD_PLAYER;
            case INITIALIZE_CHAT -> PlayerInfo.Action.INITIALIZE_CHAT;
            case UPDATE_GAME_MODE -> PlayerInfo.Action.UPDATE_GAME_MODE;
            case UPDATE_LISTED -> PlayerInfo.Action.UPDATE_LISTED;
            case UPDATE_LATENCY -> PlayerInfo.Action.UPDATE_LATENCY;
            case UPDATE_DISPLAY_NAME -> PlayerInfo.Action.UPDATE_DISPLAY_NAME;
        };
    }

    public static ClientboundPlayerInfoUpdatePacket.Action toMinecraft(PlayerInfo.Action action) {
        if (action == null) return null;
        return switch (action) {
            case ADD_PLAYER -> ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER;
            case INITIALIZE_CHAT -> ClientboundPlayerInfoUpdatePacket.Action.INITIALIZE_CHAT;
            case UPDATE_GAME_MODE -> ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE;
            case UPDATE_LISTED -> ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED;
            case UPDATE_LATENCY -> ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY;
            case UPDATE_DISPLAY_NAME -> ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME;
        };
    }

    public static Registry.Entry fromMinecraft(RegistrySynchronization.PackedRegistryEntry entry) {
        if (entry == null) return null;
        Key name = Converters.fromMinecraft(entry.id());
        CompoundBinaryTag tag = entry.data().map(t -> Converters.fromMinecraft((CompoundTag) t)).orElse(null);
        return new Registry.Entry(name, tag);
    }

    public static RegistrySynchronization.PackedRegistryEntry toMinecraft(Registry.Entry entry) {
        if (entry == null) return null;
        return new RegistrySynchronization.PackedRegistryEntry(
                Converters.toMinecraft(entry.key()),
                entry.element() != null
                        ? Optional.of(Converters.toMinecraft(entry.element()))
                        : Optional.empty()
        );
    }

    public static VillagerData fromMinecraft(net.minecraft.world.entity.npc.VillagerData data) {
        if (data == null) return null;
        return new VillagerData(
                CraftVillager.CraftType.minecraftToBukkit(data.getType()),
                CraftVillager.CraftProfession.minecraftToBukkit(data.getProfession()),
                data.getLevel()
        );
    }

    public static net.minecraft.world.entity.npc.VillagerData toMinecraft(VillagerData data) {
        if (data == null) return null;
        return new net.minecraft.world.entity.npc.VillagerData(
                CraftVillager.CraftType.bukkitToMinecraft(data.type()),
                CraftVillager.CraftProfession.bukkitToMinecraft(data.profession()),
                data.level()
        );
    }

    public static Pose fromMinecraft(net.minecraft.world.entity.Pose pose) {
        if (pose == null) return null;
        return switch (pose) {
            case STANDING -> Pose.STANDING;
            case FALL_FLYING -> Pose.FALL_FLYING;
            case SLEEPING -> Pose.SLEEPING;
            case SWIMMING -> Pose.SWIMMING;
            case SPIN_ATTACK -> Pose.SPIN_ATTACK;
            case CROUCHING -> Pose.SNEAKING;
            case LONG_JUMPING -> Pose.LONG_JUMPING;
            case DYING -> Pose.DYING;
            case CROAKING -> Pose.CROAKING;
            case USING_TONGUE -> Pose.USING_TONGUE;
            case SITTING -> Pose.SITTING;
            case ROARING -> Pose.ROARING;
            case SNIFFING -> Pose.SNIFFING;
            case EMERGING -> Pose.EMERGING;
            case DIGGING -> Pose.DIGGING;
            case SLIDING -> Pose.SLIDING;
            case SHOOTING -> Pose.SHOOTING;
            case INHALING -> Pose.INHALING;
        };
    }

    public static net.minecraft.world.entity.Pose toMinecraft(Pose pose) {
        if (pose == null) return null;
        return switch (pose) {
            case STANDING -> net.minecraft.world.entity.Pose.STANDING;
            case FALL_FLYING -> net.minecraft.world.entity.Pose.FALL_FLYING;
            case SLEEPING -> net.minecraft.world.entity.Pose.SLEEPING;
            case SWIMMING -> net.minecraft.world.entity.Pose.SWIMMING;
            case SPIN_ATTACK -> net.minecraft.world.entity.Pose.SPIN_ATTACK;
            case SNEAKING -> net.minecraft.world.entity.Pose.CROUCHING;
            case LONG_JUMPING -> net.minecraft.world.entity.Pose.LONG_JUMPING;
            case DYING -> net.minecraft.world.entity.Pose.DYING;
            case CROAKING -> net.minecraft.world.entity.Pose.CROAKING;
            case USING_TONGUE -> net.minecraft.world.entity.Pose.USING_TONGUE;
            case SITTING -> net.minecraft.world.entity.Pose.SITTING;
            case ROARING -> net.minecraft.world.entity.Pose.ROARING;
            case SNIFFING -> net.minecraft.world.entity.Pose.SNIFFING;
            case EMERGING -> net.minecraft.world.entity.Pose.EMERGING;
            case DIGGING -> net.minecraft.world.entity.Pose.DIGGING;
            case SLIDING -> net.minecraft.world.entity.Pose.SLIDING;
            case SHOOTING -> net.minecraft.world.entity.Pose.SHOOTING;
            case INHALING -> net.minecraft.world.entity.Pose.INHALING;
        };
    }

    public static CatVariant toMinecraft(Cat.Type type) {
        if (type == null) return null;
        return BuiltInRegistries.CAT_VARIANT.get(toMinecraft(type.getKey()));
    }

    public static Cat.Type fromMinecraft(CatVariant variant) {
        if (variant == null) return null;
        Key key = fromMinecraft(BuiltInRegistries.CAT_VARIANT.getKey(variant));
        return Arrays.stream(Cat.Type.values())
                .filter(type -> type.key().equals(key))
                .findFirst()
                .orElse(null);
    }

    public static WolfVariant toMinecraft(Wolf.Variant variant) {
        if (variant == null) return null;
        net.minecraft.core.Registry<WolfVariant> wolfVariants = ((CraftServer) Bukkit.getServer()).getHandle()
                .getServer()
                .registryAccess()
                .registry(Registries.WOLF_VARIANT)
                .orElseThrow();
        return wolfVariants.get(toMinecraft(variant.key()));
    }

    public static Wolf.Variant fromMinecraft(WolfVariant variant) {
        if (variant == null) return null;
        net.minecraft.core.Registry<WolfVariant> wolfVariants = ((CraftServer) Bukkit.getServer()).getHandle()
                .getServer()
                .registryAccess()
                .registry(Registries.WOLF_VARIANT)
                .orElseThrow();
        Key key = fromMinecraft(wolfVariants.getKey(variant));
        Wolf.Variant[] variants = new Wolf.Variant[] {
                Wolf.Variant.PALE, Wolf.Variant.SPOTTED,
                Wolf.Variant.SNOWY, Wolf.Variant.BLACK,
                Wolf.Variant.ASHEN , Wolf.Variant.RUSTY,
                Wolf.Variant.WOODS, Wolf.Variant.CHESTNUT,
                Wolf.Variant.STRIPED
        };
        for (Wolf.Variant next : variants)
            if (next.key().equals(key)) return next;
        return null;
    }

    public static FrogVariant toMinecraft(Frog.Variant variant) {
        if (variant == null) return null;
        return BuiltInRegistries.FROG_VARIANT.get(toMinecraft(variant.getKey()));
    }

    public static Frog.Variant fromMinecraft(FrogVariant variant) {
        if (variant == null) return null;
        Key key = fromMinecraft(BuiltInRegistries.FROG_VARIANT.getKey(variant));
        return Arrays.stream(Frog.Variant.values())
                .filter(type -> type.key().equals(key))
                .findFirst()
                .orElse(null);
    }

    public static PaintingVariant toMinecraft(Art variant) {
        if (variant == null) return null;
        net.minecraft.core.Registry<PaintingVariant> paintingVariants = ((CraftServer) Bukkit.getServer()).getHandle()
                .getServer()
                .registryAccess()
                .registry(Registries.PAINTING_VARIANT)
                .orElseThrow();
        return paintingVariants.get(toMinecraft(variant.key()));
    }

    public static Art fromMinecraft(PaintingVariant variant) {
        if (variant == null) return null;
        net.minecraft.core.Registry<PaintingVariant> paintingVariants = ((CraftServer) Bukkit.getServer()).getHandle()
                .getServer()
                .registryAccess()
                .registry(Registries.PAINTING_VARIANT)
                .orElseThrow();
        Key key = fromMinecraft(paintingVariants.getKey(variant));
        return Arrays.stream(Art.values())
                .filter(type -> type.key().equals(key))
                .findFirst()
                .orElse(null);
    }

    public static ArmadilloState fromMinecraft(Armadillo.ArmadilloState state) {
        if (state == null) return null;
        return switch (state) {
            case IDLE -> ArmadilloState.IDLE;
            case ROLLING -> ArmadilloState.ROLLING;
            case SCARED -> ArmadilloState.SCARED;
            case UNROLLING -> ArmadilloState.UNROLLING;
        };
    }

    public static Armadillo.ArmadilloState toMinecraft(ArmadilloState state) {
        if (state == null) return null;
        return switch (state) {
            case IDLE -> Armadillo.ArmadilloState.IDLE;
            case ROLLING -> Armadillo.ArmadilloState.ROLLING;
            case SCARED -> Armadillo.ArmadilloState.SCARED;
            case UNROLLING -> Armadillo.ArmadilloState.UNROLLING;
        };
    }

    public static Sniffer.State fromMinecraft(net.minecraft.world.entity.animal.sniffer.Sniffer.State state) {
        if (state == null) return null;
        return switch (state) {
            case IDLING -> Sniffer.State.IDLING;
            case FEELING_HAPPY -> Sniffer.State.FEELING_HAPPY;
            case SCENTING -> Sniffer.State.SCENTING;
            case SNIFFING -> Sniffer.State.SNIFFING;
            case SEARCHING -> Sniffer.State.SEARCHING;
            case DIGGING -> Sniffer.State.DIGGING;
            case RISING -> Sniffer.State.RISING;
        };
    }

    public static net.minecraft.world.entity.animal.sniffer.Sniffer.State toMinecraft(Sniffer.State state) {
        if (state == null) return null;
        return switch (state) {
            case IDLING -> net.minecraft.world.entity.animal.sniffer.Sniffer.State.IDLING;
            case FEELING_HAPPY -> net.minecraft.world.entity.animal.sniffer.Sniffer.State.FEELING_HAPPY;
            case SCENTING -> net.minecraft.world.entity.animal.sniffer.Sniffer.State.SCENTING;
            case SNIFFING -> net.minecraft.world.entity.animal.sniffer.Sniffer.State.SNIFFING;
            case SEARCHING -> net.minecraft.world.entity.animal.sniffer.Sniffer.State.SEARCHING;
            case DIGGING -> net.minecraft.world.entity.animal.sniffer.Sniffer.State.DIGGING;
            case RISING -> net.minecraft.world.entity.animal.sniffer.Sniffer.State.RISING;
        };
    }

    private static BiMap<EntityDataSerializer<?>, Serializer<?>> serializerMap;

    static {
        Map<EntityDataSerializer<?>, Serializer<?>> serializerMap = new HashMap<>();
        serializerMap.put(EntityDataSerializers.BYTE, Serializer.BYTE);
        serializerMap.put(EntityDataSerializers.INT, Serializer.INT);
        serializerMap.put(EntityDataSerializers.LONG, Serializer.LONG);
        serializerMap.put(EntityDataSerializers.FLOAT, Serializer.FLOAT);
        serializerMap.put(EntityDataSerializers.STRING, Serializer.STRING);
        serializerMap.put(EntityDataSerializers.COMPONENT, Serializer.COMPONENT);
        serializerMap.put(EntityDataSerializers.OPTIONAL_COMPONENT, Serializer.OPTIONAL_COMPONENT);
        serializerMap.put(EntityDataSerializers.ITEM_STACK, Serializer.SLOT);
        serializerMap.put(EntityDataSerializers.BLOCK_STATE, Serializer.BLOCK_STATE);
        serializerMap.put(EntityDataSerializers.OPTIONAL_BLOCK_STATE, Serializer.OPTIONAL_BLOCK_STATE);
        serializerMap.put(EntityDataSerializers.PARTICLE, Serializer.PARTICLE);
        serializerMap.put(EntityDataSerializers.PARTICLES, Serializer.PARTICLES);
        serializerMap.put(EntityDataSerializers.BOOLEAN, Serializer.BOOLEAN);
        serializerMap.put(EntityDataSerializers.ROTATIONS, Serializer.ROTATIONS);
        serializerMap.put(EntityDataSerializers.BLOCK_POS, Serializer.POSITION);
        serializerMap.put(EntityDataSerializers.OPTIONAL_BLOCK_POS, Serializer.OPTIONAL_POSITION);
        serializerMap.put(EntityDataSerializers.DIRECTION, Serializer.DIRECTION);
        serializerMap.put(EntityDataSerializers.OPTIONAL_UUID, Serializer.OPTIONAL_UUID);
        serializerMap.put(EntityDataSerializers.OPTIONAL_GLOBAL_POS, Serializer.OPTIONAL_GLOBAL_POSITION);
        serializerMap.put(EntityDataSerializers.COMPOUND_TAG, Serializer.NBT);
        serializerMap.put(EntityDataSerializers.VILLAGER_DATA, Serializer.VILLAGER_DATA);
        serializerMap.put(EntityDataSerializers.OPTIONAL_UNSIGNED_INT, Serializer.OPTIONAL_INT);
        serializerMap.put(EntityDataSerializers.POSE, Serializer.POSE);
        serializerMap.put(EntityDataSerializers.CAT_VARIANT, Serializer.CAT_VARIANT);
        serializerMap.put(EntityDataSerializers.WOLF_VARIANT, Serializer.WOLF_VARIANT);
        serializerMap.put(EntityDataSerializers.FROG_VARIANT, Serializer.FROG_VARIANT);
        serializerMap.put(EntityDataSerializers.PAINTING_VARIANT, Serializer.PAINTING_VARIANT);
        serializerMap.put(EntityDataSerializers.ARMADILLO_STATE, Serializer.ARMADILLO_STATE);
        serializerMap.put(EntityDataSerializers.SNIFFER_STATE, Serializer.SNIFFER_STATE);
        serializerMap.put(EntityDataSerializers.VECTOR3, Serializer.VECTOR3);
        serializerMap.put(EntityDataSerializers.QUATERNION, Serializer.QUATERNION);
        Converters.serializerMap = HashBiMap.create(serializerMap);
    }

    public static EntityDataSerializer<?> toMinecraft(Serializer<?> serializer) {
        return serializerMap.inverse().get(serializer);
    }

    public static Serializer<?> fromMinecraft(EntityDataSerializer<?> serializer) {
        return serializerMap.get(serializer);
    }

}
