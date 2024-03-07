package org.machinemc.primallib.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

/**
 * Utils related to Java Plugins.
 * <p>
 * This allows to spoof the Java Plugin is enabled and further registration of
 * services, listeners etc.
 */
public final class PluginUtils {

    private PluginUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Enables the plugin without calling onEnable method.
     *
     * @param plugin plugin to enable
     */
    public static void silentlyEnable(JavaPlugin plugin) throws Exception {
        silentlyEnable(plugin, true);
    }

    /**
     * Disables the plugin without calling onDisable method.
     *
     * @param plugin plugin to enable
     */
    public static void silentlyDisable(JavaPlugin plugin) throws Exception {
        silentlyEnable(plugin, false);
    }

    /**
     * Silently enables or disables the plugin.
     *
     * @param plugin plugin to enable or disable
     * @param enable whether the plugin should be enabled or disabled
     */
    public static void silentlyEnable(JavaPlugin plugin, boolean enable) throws Exception {
        Field field = JavaPlugin.class.getDeclaredField("isEnabled");
        field.setAccessible(true);
        field.set(plugin, enable);
        field.setAccessible(false);
    }

}
