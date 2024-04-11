package org.machinemc.primallib.entity;

import com.google.common.base.Preconditions;
import lombok.With;
import org.bukkit.entity.Villager;

/**
 * Represents packed data of a villager.
 *
 * @param type type of the villager
 * @param profession profession of the villager
 * @param level level of the villager
 */
@With
public record VillagerData(Villager.Type type, Villager.Profession profession, int level) {

    public VillagerData {
        Preconditions.checkNotNull(type, "Villager type can not be null");
        Preconditions.checkNotNull(profession, "Villager profession can not be null");
        level = Math.max(1, level);
    }

    public VillagerData() {
        this(Villager.Type.PLAINS, Villager.Profession.NONE, 1);
    }

    public VillagerData(Villager villager) {
        this(villager.getVillagerType(), villager.getProfession(), villager.getVillagerLevel());
    }

    /**
     * Applies this villager data to existing villager.
     *
     * @param villager villager to apply the data to
     */
    public void apply(Villager villager) {
        Preconditions.checkNotNull(villager, "Villager can not be null");
        villager.setVillagerType(type);
        villager.setProfession(profession);
        villager.setVillagerLevel(level);
    }

}
