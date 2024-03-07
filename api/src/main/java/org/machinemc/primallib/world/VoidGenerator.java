package org.machinemc.primallib.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Void generator implementation.
 */
public class VoidGenerator extends ChunkGenerator {

    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo,
                                @NotNull Random random,
                                int chunkX, int chunkZ,
                                @NotNull ChunkGenerator.ChunkData chunkData) {
        if (chunkX != 0 | chunkZ != 0) return;
        chunkData.setBlock(0, 63, 0, Material.BEDROCK);
    }

    @Override
    public boolean canSpawn(@NotNull World world, int x, int z) {
        return true;
    }

    @Override
    public Location getFixedSpawnLocation(@NotNull World world, @NotNull Random random) {
        return new Location(world, 0, 64, 0);
    }

}
