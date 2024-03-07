package org.machinemc.primallib.internal;

import com.google.common.collect.Iterators;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.util.OwnerPlugin;
import org.machinemc.primallib.util.PluginUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Implementation of PrimalLib bootstrap that delegates the plugin creation to
 * plugin's own bootstrap.
 * <p>
 * This is required by PrimalLib to register its own listeners and handlers.
 */
@SuppressWarnings("UnstableApiUsage")
public class PrimalLibBootstrap implements PluginBootstrap {

    private PluginBootstrap bootstrapper;

    @Override
    @SneakyThrows
    public void bootstrap(@NotNull BootstrapContext context) {
        try (JarFile jar = new JarFile(context.getPluginSource().toFile())) {
            List<JarEntry> entries = new ArrayList<>();
            Iterators.addAll(entries, jar.entries().asIterator());
            for (JarEntry entry : entries) {
                String name = entry.getName();
                if (!name.equals("paper-plugin.yml")) continue;
                Map<String, Object> map = new Yaml().load(jar.getInputStream(entry));
                String bootstrapperName = map.get("delegate-bootstrapper").toString();
                bootstrapper = (PluginBootstrap) Class.forName(bootstrapperName).getConstructor().newInstance();
                break;
            }
        }
        bootstrapper.bootstrap(context);
    }

    @Override
    @SneakyThrows
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        JavaPlugin plugin = bootstrapper.createPlugin(context);
        OwnerPlugin.initialize(plugin);
        PluginUtils.silentlyEnable(plugin);
        VersionBootstrap.Selector.create().select().bootstrap(plugin, context);
        PluginUtils.silentlyDisable(plugin);
        return plugin;
    }

}
