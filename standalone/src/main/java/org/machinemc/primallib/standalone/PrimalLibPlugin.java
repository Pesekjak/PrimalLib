package org.machinemc.primallib.standalone;

import org.bukkit.plugin.java.JavaPlugin;
import org.machinemc.primallib.version.MinecraftVersion;

public class PrimalLibPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Running PrimalLib on version " + MinecraftVersion.get().getCodeName());
    }

}
