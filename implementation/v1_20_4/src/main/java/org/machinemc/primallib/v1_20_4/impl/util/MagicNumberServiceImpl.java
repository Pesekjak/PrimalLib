package org.machinemc.primallib.v1_20_4.impl.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import org.bukkit.Fluid;
import org.bukkit.GameEvent;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.machinemc.primallib.util.MagicNumberService;
import org.machinemc.primallib.v1_20_4.util.Converters;

public class MagicNumberServiceImpl extends MagicNumberService {

    @Override
    public BlockData getBlockDataFromID(int id) {
        return Converters.fromMinecraft(Block.stateById(id));
    }

    @Override
    public int getIDFromBlockData(BlockData blockData) {
        return Block.getId(Converters.toMinecraft(blockData));
    }

    @Override
    public Fluid getFluidFromID(int id) {
        return Converters.fromMinecraft(BuiltInRegistries.FLUID.byId(id));
    }

    @Override
    public int getIDFromFluid(Fluid fluid) {
        return BuiltInRegistries.FLUID.getId(Converters.toMinecraft(fluid));
    }

    @Override
    public Material getItemFromID(int id) {
        return Converters.fromMinecraft(new net.minecraft.world.item.ItemStack(BuiltInRegistries.ITEM.byId(id))).getType();
    }

    @Override
    public int getIDFromItem(Material item) {
        return BuiltInRegistries.ITEM.getId(Converters.toMinecraft(new ItemStack(item)).getItem());
    }

    @Override
    public EntityType getEntityTypeFromID(int id) {
        return Converters.fromMinecraft(BuiltInRegistries.ENTITY_TYPE.byId(id));
    }

    @Override
    public int getIDFromEntityType(EntityType entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getId(Converters.toMinecraft(entityType));
    }

    @Override
    public GameEvent getGameEventFromID(int id) {
        return Converters.fromMinecraft(BuiltInRegistries.GAME_EVENT.byId(id));
    }

    @Override
    public int getIDFromGameEvent(GameEvent gameEvent) {
        return BuiltInRegistries.GAME_EVENT.getId(Converters.toMinecraft(gameEvent));
    }

}
