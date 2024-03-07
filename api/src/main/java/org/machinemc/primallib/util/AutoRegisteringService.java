package org.machinemc.primallib.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

/**
 * Represents a service that can automatically register itself
 * to the Bukkit service manager.
 */
public abstract class AutoRegisteringService<T extends AutoRegisteringService<T>> {

    /**
     * Registers this service to the service manager.
     *
     * @return this
     */
    @SuppressWarnings("unchecked")
    public <Service extends AutoRegisteringService<T>> Service register() {
        if (Bukkit.getServicesManager().isProvidedFor(getRegistrationClass())) return (Service) this;
        Bukkit.getServicesManager().register(getRegistrationClass(), (T) this, OwnerPlugin.get(), ServicePriority.Normal);
        return (Service) this;
    }

    /**
     * Returns the class of this service.
     *
     * @return class of this service
     */
    public abstract Class<T> getRegistrationClass();

}
