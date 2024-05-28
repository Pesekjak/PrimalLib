package org.machinemc.primallib.metadata;

import org.bukkit.entity.*;
import org.machinemc.primallib.metadata.model.*;
import org.machinemc.primallib.metadata.model.boss.WitherEntityDataModel;
import org.machinemc.primallib.metadata.model.boss.dragon.EnderDragonEntityDataModel;
import org.machinemc.primallib.metadata.model.decoration.*;
import org.machinemc.primallib.metadata.model.decoration.painting.PaintingEntityDataModel;
import org.machinemc.primallib.metadata.model.mob.*;
import org.machinemc.primallib.metadata.model.passive.*;
import org.machinemc.primallib.metadata.model.player.PlayerEntityDataModel;
import org.machinemc.primallib.metadata.model.projectile.*;
import org.machinemc.primallib.metadata.model.projectile.thrown.*;
import org.machinemc.primallib.metadata.model.vehicle.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utilities for operations with entity metadata.
 */
@SuppressWarnings("UnstableApiUsage")
public final class MetadataUtils {

    private static final Map<EntityType, Class<? extends EntityDataModel>> models = new ConcurrentHashMap<>();
    
    static {
        models.put(EntityType.ITEM, ItemEntityDataModel.class);
        models.put(EntityType.EXPERIENCE_ORB, ExperienceOrbEntityDataModel.class);
        models.put(EntityType.AREA_EFFECT_CLOUD, AreaEffectCloudEntityDataModel.class);
        models.put(EntityType.ELDER_GUARDIAN, ElderGuardianEntityDataModel.class);
        models.put(EntityType.WITHER_SKELETON, WitherSkeletonEntityDataModel.class);
        models.put(EntityType.STRAY, StrayEntityDataModel.class);
        models.put(EntityType.EGG, EggEntityDataModel.class);
        models.put(EntityType.LEASH_KNOT, LeashKnotEntityDataModel.class);
        models.put(EntityType.PAINTING, PaintingEntityDataModel.class);
        models.put(EntityType.ARROW, ArrowEntityDataModel.class);
        models.put(EntityType.SNOWBALL, SnowballEntityDataModel.class);
        models.put(EntityType.FIREBALL, FireballEntityDataModel.class);
        models.put(EntityType.SMALL_FIREBALL, SmallFireballEntityDataModel.class);
        models.put(EntityType.ENDER_PEARL, EnderPearlEntityDataModel.class);
        models.put(EntityType.EYE_OF_ENDER, EyeOfEnderEntityDataModel.class);
        models.put(EntityType.POTION, PotionEntityDataModel.class);
        models.put(EntityType.EXPERIENCE_BOTTLE, ExperienceBottleEntityDataModel.class);
        models.put(EntityType.ITEM_FRAME, ItemFrameEntityDataModel.class);
        models.put(EntityType.WITHER_SKULL, WitherSkullEntityDataModel.class);
        models.put(EntityType.TNT, TntEntityDataModel.class);
        models.put(EntityType.FALLING_BLOCK, FallingBlockEntityDataModel.class);
        models.put(EntityType.FIREWORK_ROCKET, FireworkRocketEntityDataModel.class);
        models.put(EntityType.HUSK, HuskEntityDataModel.class);
        models.put(EntityType.SPECTRAL_ARROW, SpectralArrowEntityDataModel.class);
        models.put(EntityType.SHULKER_BULLET, ShulkerBulletEntityDataModel.class);
        models.put(EntityType.DRAGON_FIREBALL, DragonFireballEntityDataModel.class);
        models.put(EntityType.ZOMBIE_VILLAGER, ZombieVillagerEntityDataModel.class);
        models.put(EntityType.SKELETON_HORSE, SkeletonHorseEntityDataModel.class);
        models.put(EntityType.ZOMBIE_HORSE, ZombieHorseEntityDataModel.class);
        models.put(EntityType.ARMOR_STAND, ArmorStandEntityDataModel.class);
        models.put(EntityType.DONKEY, DonkeyEntityDataModel.class);
        models.put(EntityType.MULE, MuleEntityDataModel.class);
        models.put(EntityType.EVOKER_FANGS, EvokerFangsEntityDataModel.class);
        models.put(EntityType.EVOKER, EvokerEntityDataModel.class);
        models.put(EntityType.VEX, VexEntityDataModel.class);
        models.put(EntityType.VINDICATOR, VindicatorEntityDataModel.class);
        models.put(EntityType.ILLUSIONER, IllusionerEntityDataModel.class);
        models.put(EntityType.COMMAND_BLOCK_MINECART, CommandBlockMinecartEntityDataModel.class);
        models.put(EntityType.BOAT, BoatEntityDataModel.class);
        models.put(EntityType.MINECART, MinecartEntityDataModel.class);
        models.put(EntityType.CHEST_MINECART, ChestMinecartEntityDataModel.class);
        models.put(EntityType.FURNACE_MINECART, FurnaceMinecartEntityDataModel.class);
        models.put(EntityType.TNT_MINECART, TntMinecartEntityDataModel.class);
        models.put(EntityType.HOPPER_MINECART, HopperMinecartEntityDataModel.class);
        models.put(EntityType.SPAWNER_MINECART, SpawnerMinecartEntityDataModel.class);
        models.put(EntityType.CREEPER, CreeperEntityDataModel.class);
        models.put(EntityType.SKELETON, SkeletonEntityDataModel.class);
        models.put(EntityType.SPIDER, SpiderEntityDataModel.class);
        models.put(EntityType.GIANT, GiantEntityDataModel.class);
        models.put(EntityType.ZOMBIE, ZombieEntityDataModel.class);
        models.put(EntityType.SLIME, SlimeEntityDataModel.class);
        models.put(EntityType.GHAST, GhastEntityDataModel.class);
        models.put(EntityType.ZOMBIFIED_PIGLIN, ZombifiedPiglinEntityDataModel.class);
        models.put(EntityType.ENDERMAN, EndermanEntityDataModel.class);
        models.put(EntityType.CAVE_SPIDER, CaveSpiderEntityDataModel.class);
        models.put(EntityType.SILVERFISH, SilverfishEntityDataModel.class);
        models.put(EntityType.BLAZE, BlazeEntityDataModel.class);
        models.put(EntityType.MAGMA_CUBE, MagmaCubeEntityDataModel.class);
        models.put(EntityType.ENDER_DRAGON, EnderDragonEntityDataModel.class);
        models.put(EntityType.WITHER, WitherEntityDataModel.class);
        models.put(EntityType.BAT, BatEntityDataModel.class);
        models.put(EntityType.WITCH, WitherEntityDataModel.class);
        models.put(EntityType.ENDERMITE, EndermiteEntityDataModel.class);
        models.put(EntityType.GUARDIAN, GuardianEntityDataModel.class);
        models.put(EntityType.SHULKER, ShulkerEntityDataModel.class);
        models.put(EntityType.PIG, PiglinEntityDataModel.class);
        models.put(EntityType.SHEEP, SheepEntityDataModel.class);
        models.put(EntityType.COW, CowEntityDataModel.class);
        models.put(EntityType.CHICKEN, ChickenEntityDataModel.class);
        models.put(EntityType.SQUID, SquidEntityDataModel.class);
        models.put(EntityType.WOLF, WolfEntityDataModel.class);
        models.put(EntityType.MOOSHROOM, MooshroomEntityDataModel.class);
        models.put(EntityType.SNOW_GOLEM, SnowGolemEntityDataModel.class);
        models.put(EntityType.OCELOT, OcelotEntityDataModel.class);
        models.put(EntityType.IRON_GOLEM, IronGolemEntityDataModel.class);
        models.put(EntityType.HORSE, HorseEntityDataModel.class);
        models.put(EntityType.RABBIT, RabbitEntityDataModel.class);
        models.put(EntityType.POLAR_BEAR, PolarBearEntityDataModel.class);
        models.put(EntityType.LLAMA, LlamaEntityDataModel.class);
        models.put(EntityType.LLAMA_SPIT, LlamaSpitEntityDataModel.class);
        models.put(EntityType.PARROT, ParrotEntityDataModel.class);
        models.put(EntityType.VILLAGER, VillagerEntityDataModel.class);
        models.put(EntityType.END_CRYSTAL, EndCrystalEntityDataModel.class);
        models.put(EntityType.TURTLE, TurtleEntityDataModel.class);
        models.put(EntityType.PHANTOM, PhantomEntityDataModel.class);
        models.put(EntityType.TRIDENT, TridentEntityDataModel.class);
        models.put(EntityType.COD, CodEntityDataModel.class);
        models.put(EntityType.SALMON, SalmonEntityDataModel.class);
        models.put(EntityType.PUFFERFISH, PufferfishEntityDataModel.class);
        models.put(EntityType.TROPICAL_FISH, TropicalFishEntityDataModel.class);
        models.put(EntityType.DROWNED, DrownedEntityDataModel.class);
        models.put(EntityType.DOLPHIN, DolphinEntityDataModel.class);
        models.put(EntityType.CAT, CatEntityDataModel.class);
        models.put(EntityType.PANDA, PandaEntityDataModel.class);
        models.put(EntityType.PILLAGER, PillagerEntityDataModel.class);
        models.put(EntityType.RAVAGER, RavagerEntityDataModel.class);
        models.put(EntityType.TRADER_LLAMA, TraderLlamaEntityDataModel.class);
        models.put(EntityType.WANDERING_TRADER, WanderingTraderEntityDataModel.class);
        models.put(EntityType.FOX, FoxEntityDataModel.class);
        models.put(EntityType.BEE, BeeEntityDataModel.class);
        models.put(EntityType.HOGLIN, HoglinEntityDataModel.class);
        models.put(EntityType.PIGLIN, PiglinEntityDataModel.class);
        models.put(EntityType.STRIDER, StriderEntityDataModel.class);
        models.put(EntityType.ZOGLIN, ZoglinEntityDataModel.class);
        models.put(EntityType.PIGLIN_BRUTE, PiglinBruteEntityDataModel.class);
        models.put(EntityType.AXOLOTL, AxolotlEntityDataModel.class);
        models.put(EntityType.GLOW_ITEM_FRAME, GlowItemFrameEntityDataModel.class);
        models.put(EntityType.GLOW_SQUID, GlowSquidEntityDataModel.class);
        models.put(EntityType.GOAT, GoatEntityDataModel.class);
        models.put(EntityType.MARKER, MarkerEntityDataModel.class);
        models.put(EntityType.ALLAY, AllayEntityDataModel.class);
        models.put(EntityType.CHEST_BOAT, ChestBoatEntityDataModel.class);
        models.put(EntityType.FROG, FrogEntityDataModel.class);
        models.put(EntityType.TADPOLE, TadpoleEntityDataModel.class);
        models.put(EntityType.WARDEN, WardenEntityDataModel.class);
        models.put(EntityType.CAMEL, CamelEntityDataModel.class);
        models.put(EntityType.BLOCK_DISPLAY, BlockDisplayEntityDataModel.class);
        models.put(EntityType.INTERACTION, InteractionEntityDataModel.class);
        models.put(EntityType.ITEM_DISPLAY, ItemDisplayEntityDataModel.class);
        models.put(EntityType.SNIFFER, SnifferEntityDataModel.class);
        models.put(EntityType.TEXT_DISPLAY, TextDisplayEntityDataModel.class);
        models.put(EntityType.BREEZE, BreezeEntityDataModel.class);
        models.put(EntityType.WIND_CHARGE, WindChargeEntityDataModel.class);
        models.put(EntityType.BREEZE_WIND_CHARGE, BreezeWindChargeEntityDataModel.class);
        models.put(EntityType.ARMADILLO, ArmadilloEntityDataModel.class);
        models.put(EntityType.BOGGED, BoggedEntityDataModel.class);
        models.put(EntityType.OMINOUS_ITEM_SPAWNER, OminousItemSpawnerEntityDataModel.class);
        models.put(EntityType.FISHING_BOBBER, FishingBobberEntityDataModel.class);
        models.put(EntityType.LIGHTNING_BOLT, LightningEntityDataModel.class);
        models.put(EntityType.PLAYER, PlayerEntityDataModel.class);
    }
    
    private MetadataUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns entity data model type for given entity type.
     *
     * @param type entity type
     * @return entity data model type
     */
    public static Class<? extends EntityDataModel> getEntityDataModel(EntityType type) {
        return models.get(type);
    }

    /**
     * Returns map of all entity data fields for given entity data model.
     *
     * @param model entity data model
     * @return fields of the entity data model
     */
    public static Map<String, EntityData.Field<?>> getFields(Class<? extends EntityDataModel> model) {
        Map<String, EntityData.Field<?>> map = new HashMap<>();
        List<Field> fields = Arrays.stream(model.getFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()))
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f -> Modifier.isFinal(f.getModifiers()))
                .filter(f -> f.getType().equals(EntityData.Field.class))
                .toList();
        for (Field field : fields) {
            try {
                String name = field.getName();
                int index = 2;
                while (map.containsKey(name))
                    name = field.getName() + index++;
                EntityData.Field<?> dataField = (EntityData.Field<?>) field.get(null);
                if (!dataField.isValid()) continue;
                map.put(name, dataField);
            } catch (IllegalAccessException ignored) { }
        }

        List<Map.Entry<String, EntityData.Field<?>>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        map = new LinkedHashMap<>();
        for (Map.Entry<String, EntityData.Field<?>> entry : list)
            map.put(entry.getKey(), entry.getValue());

        return map;
    }

}
