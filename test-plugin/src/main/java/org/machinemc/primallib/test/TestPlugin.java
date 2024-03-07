package org.machinemc.primallib.test;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.machinemc.primallib.version.MinecraftVersion;

public class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("Running PrimalLib on version " + MinecraftVersion.get().getCodeName());
        getServer().getPluginManager().registerEvents(this, this);
    }

}
