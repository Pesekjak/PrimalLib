package org.machinemc.primallib.particle;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.common.base.Preconditions;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static java.util.Map.entry;

/**
 * Represents a particle with pre-configured options.
 *
 * @param particle particle type
 * @param options particle options
 */
public record ConfiguredParticle(Particle particle, @Nullable Object options) {

    /**
     * Creates new configured particle from particle builder.
     *
     * @param builder particle builder
     * @return configured particle
     */
    public static ConfiguredParticle fromBuilder(ParticleBuilder builder) {
        Object data = builder.data();
        if (data == null) return new ConfiguredParticle(builder.particle());
        return new ConfiguredParticle(builder.particle(), data);
    }

    public ConfiguredParticle(Particle particle) {
        this(particle, getDefaultOptions(particle));
    }

    public ConfiguredParticle {
        Preconditions.checkNotNull(particle, "Particle can not be null");
        if (particle.getDataType() != Void.class) {
            Preconditions.checkNotNull(options, "Options for '%s' particle can not be null".formatted(particle.getKey()));
            if (!particle.getDataType().isAssignableFrom(options.getClass()))
                throw new IllegalArgumentException("Unsupported options for particle '%s'".formatted(particle.getKey()));
        }
    }

    /**
     * Converts this configured particle to the particle builder.
     *
     * @return particle builder for this particle
     */
    @Contract(pure = true)
    public ParticleBuilder asBuilder() {
        return new ParticleBuilder(particle).data(options);
    }

    private static final Map<Class<?>, Object> DEFAULT_OPTIONS = Map.ofEntries(
            entry(Particle.DustOptions.class, new Particle.DustOptions(Color.BLACK, 1)),
            entry(ItemStack.class, new ItemStack(Material.STONE)),
            entry(BlockData.class, Bukkit.createBlockData(Material.STONE)),
            entry(Particle.DustTransition.class, new Particle.DustTransition(Color.BLACK, Color.BLACK, 1)),
            entry(Vibration.class, new Vibration.Destination.BlockDestination(Bukkit.getWorlds().getFirst().getSpawnLocation())),
            entry(Float.class, 0f),
            entry(Integer.class, 0)
    );

    private static Object getDefaultOptions(Particle particle) {
        return DEFAULT_OPTIONS.get(particle.getDataType());
    }

}
