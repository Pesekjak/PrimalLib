package org.machinemc.primallib.generator.metadata;

import com.google.gson.*;
import net.minecraft.DetectedVersion;
import net.minecraft.SharedConstants;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.entity.vehicle.*;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.version.ProtocolVersion;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Data generator that generates information about metadata values for each entity type.
 */
public final class DataGenerator {

    /**
     * Targeted protocol version, needs to be changed for each new implementation.
     */
    public static final ProtocolVersion TARGET_VERSION = ProtocolVersion.PROTOCOL_1_20_3;

    // NMS entity data serializer <-> PrimalLib serializer
    private static final Map<EntityDataSerializer<?>, Serializer<?>> serializerMap = new HashMap<>();

    // NMS entity classes
    private static final Set<Class<? extends Entity>> entityClasses = new HashSet<>();

    // Entity names <-> Entity data
    private final Map<String, EntityTypeData> entityTypeData = new TreeMap<>();

    // Application entry point
    public static void main(String[] args) throws Exception {
        var gen = new DataGenerator();
        System.out.println("Running Metadata Data Generator for version " + TARGET_VERSION);
        gen.run();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String data = gson.toJson(gen.getData());
        File file = new File("metadata-data-" + ProtocolVersion.PROTOCOL_1_20_3 + ".json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(data);
        writer.close();
        System.out.println("Finished, data saved to " + file.getAbsolutePath());
    }

    /**
     * Runs the data generator and stores the result in {@link #getData()}.
     */
    public void run() {
        entityClasses.forEach(this::generateForEntityClass);
    }

    /**
     * Generates entity type data information for given entity class.
     *
     * @param entityClass entity class
     */
    public void generateForEntityClass(Class<? extends Entity> entityClass) {
        String name = entityClass.getSimpleName();
        if (entityTypeData.containsKey(name)) return;

        Class<?> superClass = entityClass.getSuperclass();
        if (!Entity.class.isAssignableFrom(superClass)) superClass = null;

        EntityTypeData entityData = new EntityTypeData(name, superClass != null ? superClass.getSimpleName() : null);
        Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> EntityDataAccessor.class.isAssignableFrom(f.getType()))
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .peek(f -> f.setAccessible(true))
                .forEach(f -> {
                    EntityDataAccessor<?> accessor;
                    try {
                        accessor = (EntityDataAccessor<?>) f.get(null);
                    } catch (IllegalAccessException exception) {
                        throw new RuntimeException(exception);
                    }
                    String fieldName = f.getName();
                    int id = accessor.getId();
                    Serializer<?> serializer = serializerMap.get(accessor.getSerializer());

                    entityData.fields().add(new EntityData.Field<>(fieldName, id, serializer));
                });
        entityTypeData.put(name, entityData);
    }

    /**
     * Returns JSON with all loaded entity data types.
     *
     * @return JSON with all entity data types
     * @see #run()
     */
    public JsonObject getData() {
        JsonObject json = new JsonObject();
        json.addProperty("_version", ProtocolVersion.PROTOCOL_1_20_3.name());
        for (EntityTypeData data : entityTypeData.values())
            json.add(data.name(), data.serialize());
        return json;
    }

    static {
        // Saves system output
        PrintStream out = System.out;

        // Suppresses the warning and error messages
        // from Bootstrap
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));

        // Loads some required parts of the server,
        // can be different for each Minecraft version
        SharedConstants.setVersion(DetectedVersion.BUILT_IN);
        Bootstrap.bootStrap();

        // Sets system output back to allow logging,
        // which is disabled due to Bootstrap
        System.setOut(out);

        // Maps NMS serializers to PrimalLib serializers,
        // all NMS serializers need to have a counterpart, but
        // not primal lib serializers need to be registered
        serializerMap.put(EntityDataSerializers.BYTE, Serializer.BYTE);
        serializerMap.put(EntityDataSerializers.INT, Serializer.INT);
        serializerMap.put(EntityDataSerializers.LONG, Serializer.LONG);
        serializerMap.put(EntityDataSerializers.FLOAT, Serializer.FLOAT);
        serializerMap.put(EntityDataSerializers.STRING, Serializer.STRING);
        serializerMap.put(EntityDataSerializers.COMPONENT, Serializer.COMPONENT);
        serializerMap.put(EntityDataSerializers.OPTIONAL_COMPONENT, Serializer.OPTIONAL_COMPONENT);
        serializerMap.put(EntityDataSerializers.ITEM_STACK, Serializer.SLOT);
        serializerMap.put(EntityDataSerializers.BOOLEAN, Serializer.BOOLEAN);
        serializerMap.put(EntityDataSerializers.ROTATIONS, Serializer.ROTATIONS);
        serializerMap.put(EntityDataSerializers.BLOCK_POS, Serializer.POSITION);
        serializerMap.put(EntityDataSerializers.OPTIONAL_BLOCK_POS, Serializer.OPTIONAL_POSITION);
        serializerMap.put(EntityDataSerializers.DIRECTION, Serializer.DIRECTION);
        serializerMap.put(EntityDataSerializers.OPTIONAL_UUID, Serializer.OPTIONAL_UUID);
        serializerMap.put(EntityDataSerializers.BLOCK_STATE, Serializer.BLOCK_STATE);
        serializerMap.put(EntityDataSerializers.OPTIONAL_BLOCK_STATE, Serializer.OPTIONAL_BLOCK_STATE);
        serializerMap.put(EntityDataSerializers.COMPOUND_TAG, Serializer.NBT);
        serializerMap.put(EntityDataSerializers.PARTICLE, Serializer.PARTICLE);
        serializerMap.put(EntityDataSerializers.VILLAGER_DATA, Serializer.VILLAGER_DATA);
        serializerMap.put(EntityDataSerializers.OPTIONAL_UNSIGNED_INT, Serializer.OPTIONAL_INT);
        serializerMap.put(EntityDataSerializers.POSE, Serializer.POSE);
        serializerMap.put(EntityDataSerializers.CAT_VARIANT, Serializer.CAT_VARIANT);
        serializerMap.put(EntityDataSerializers.FROG_VARIANT, Serializer.FROG_VARIANT);
        serializerMap.put(EntityDataSerializers.OPTIONAL_GLOBAL_POS, Serializer.OPTIONAL_GLOBAL_POSITION);
        serializerMap.put(EntityDataSerializers.PAINTING_VARIANT, Serializer.PAINTING_VARIANT);
        serializerMap.put(EntityDataSerializers.SNIFFER_STATE, Serializer.SNIFFER_STATE);
        serializerMap.put(EntityDataSerializers.VECTOR3, Serializer.VECTOR3);
        serializerMap.put(EntityDataSerializers.QUATERNION, Serializer.QUATERNION);
    }
    
    static {
        // Loads all NMS entity types,
        // the whole list can be seen in net.minecraft.world.entity.EntityType
        entityClasses.add(Allay.class);
        entityClasses.add(AreaEffectCloud.class);
        entityClasses.add(ArmorStand.class);
        entityClasses.add(Arrow.class);
        entityClasses.add(Axolotl.class);
        entityClasses.add(Bat.class);
        entityClasses.add(Bee.class);
        entityClasses.add(Blaze.class);
        entityClasses.add(Display.BlockDisplay.class);
        entityClasses.add(Boat.class);
        entityClasses.add(Breeze.class);
        entityClasses.add(Camel.class);
        entityClasses.add(Cat.class);
        entityClasses.add(CaveSpider.class);
        entityClasses.add(ChestBoat.class);
        entityClasses.add(MinecartChest.class);
        entityClasses.add(Chicken.class);
        entityClasses.add(Cod.class);
        entityClasses.add(MinecartCommandBlock.class);
        entityClasses.add(Cow.class);
        entityClasses.add(Creeper.class);
        entityClasses.add(Dolphin.class);
        entityClasses.add(Donkey.class);
        entityClasses.add(DragonFireball.class);
        entityClasses.add(Drowned.class);
        entityClasses.add(ThrownEgg.class);
        entityClasses.add(ElderGuardian.class);
        entityClasses.add(EndCrystal.class);
        entityClasses.add(EnderDragon.class);
        entityClasses.add(ThrownEnderpearl.class);
        entityClasses.add(EnderMan.class);
        entityClasses.add(Endermite.class);
        entityClasses.add(Evoker.class);
        entityClasses.add(EvokerFangs.class);
        entityClasses.add(ThrownExperienceBottle.class);
        entityClasses.add(ExperienceOrb.class);
        entityClasses.add(EyeOfEnder.class);
        entityClasses.add(FallingBlockEntity.class);
        entityClasses.add(FireworkRocketEntity.class);
        entityClasses.add(Fox.class);
        entityClasses.add(Frog.class);
        entityClasses.add(MinecartFurnace.class);
        entityClasses.add(Ghast.class);
        entityClasses.add(Giant.class);
        entityClasses.add(GlowItemFrame.class);
        entityClasses.add(GlowSquid.class);
        entityClasses.add(Goat.class);
        entityClasses.add(Guardian.class);
        entityClasses.add(Hoglin.class);
        entityClasses.add(MinecartHopper.class);
        entityClasses.add(Horse.class);
        entityClasses.add(Husk.class);
        entityClasses.add(Illusioner.class);
        entityClasses.add(Interaction.class);
        entityClasses.add(IronGolem.class);
        entityClasses.add(ItemEntity.class);
        entityClasses.add(Display.ItemDisplay.class);
        entityClasses.add(ItemFrame.class);
        entityClasses.add(LargeFireball.class);
        entityClasses.add(LeashFenceKnotEntity.class);
        entityClasses.add(LightningBolt.class);
        entityClasses.add(Llama.class);
        entityClasses.add(LlamaSpit.class);
        entityClasses.add(MagmaCube.class);
        entityClasses.add(Marker.class);
        entityClasses.add(Minecart.class);
        entityClasses.add(MushroomCow.class);
        entityClasses.add(Mule.class);
        entityClasses.add(Ocelot.class);
        entityClasses.add(Painting.class);
        entityClasses.add(Panda.class);
        entityClasses.add(Parrot.class);
        entityClasses.add(Phantom.class);
        entityClasses.add(Pig.class);
        entityClasses.add(Piglin.class);
        entityClasses.add(PiglinBrute.class);
        entityClasses.add(Pillager.class);
        entityClasses.add(PolarBear.class);
        entityClasses.add(ThrownPotion.class);
        entityClasses.add(Pufferfish.class);
        entityClasses.add(Rabbit.class);
        entityClasses.add(Ravager.class);
        entityClasses.add(Salmon.class);
        entityClasses.add(Sheep.class);
        entityClasses.add(Shulker.class);
        entityClasses.add(ShulkerBullet.class);
        entityClasses.add(Silverfish.class);
        entityClasses.add(Skeleton.class);
        entityClasses.add(SkeletonHorse.class);
        entityClasses.add(Slime.class);
        entityClasses.add(SmallFireball.class);
        entityClasses.add(Sniffer.class);
        entityClasses.add(SnowGolem.class);
        entityClasses.add(Snowball.class);
        entityClasses.add(MinecartSpawner.class);
        entityClasses.add(SpectralArrow.class);
        entityClasses.add(Spider.class);
        entityClasses.add(Squid.class);
        entityClasses.add(Stray.class);
        entityClasses.add(Strider.class);
        entityClasses.add(Tadpole.class);
        entityClasses.add(Display.TextDisplay.class);
        entityClasses.add(PrimedTnt.class);
        entityClasses.add(MinecartTNT.class);
        entityClasses.add(TraderLlama.class);
        entityClasses.add(ThrownTrident.class);
        entityClasses.add(TropicalFish.class);
        entityClasses.add(Turtle.class);
        entityClasses.add(Vex.class);
        entityClasses.add(Villager.class);
        entityClasses.add(Vindicator.class);
        entityClasses.add(WanderingTrader.class);
        entityClasses.add(Warden.class);
        entityClasses.add(WindCharge.class);
        entityClasses.add(Witch.class);
        entityClasses.add(WitherBoss.class);
        entityClasses.add(WitherSkeleton.class);
        entityClasses.add(WitherSkull.class);
        entityClasses.add(Wolf.class);
        entityClasses.add(Zoglin.class);
        entityClasses.add(Zombie.class);
        entityClasses.add(ZombieHorse.class);
        entityClasses.add(ZombieVillager.class);
        entityClasses.add(ZombifiedPiglin.class);
        entityClasses.add(Player.class);
        entityClasses.add(FishingHook.class);
    }

}
