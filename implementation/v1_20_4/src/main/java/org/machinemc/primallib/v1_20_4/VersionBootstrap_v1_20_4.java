package org.machinemc.primallib.v1_20_4;

import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.internal.VersionBootstrap;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.v1_20_4.impl.advancement.AdvancementServiceImpl;
import org.machinemc.primallib.v1_20_4.impl.messaging.PluginMessageServiceImpl;
import org.machinemc.primallib.v1_20_4.impl.player.PlayerActionServiceImpl;
import org.machinemc.primallib.v1_20_4.impl.profile.PlayerInfoServiceImpl;
import org.machinemc.primallib.v1_20_4.impl.util.MagicNumberServiceImpl;
import org.machinemc.primallib.v1_20_4.listeners.bukkit.PlayerLoginListener;
import org.machinemc.primallib.v1_20_4.listeners.packet.clientbound.*;
import org.machinemc.primallib.v1_20_4.listeners.packet.serverbound.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class VersionBootstrap_v1_20_4 implements VersionBootstrap {

    @Override
    public void bootstrap(JavaPlugin plugin, PluginProviderContext context) {

        // Services
        List<AutoRegisteringService<?>> services = new ArrayList<>();
        services.add(new AdvancementServiceImpl().register());
        services.add(new PluginMessageServiceImpl().register());
        services.add(new PlayerActionServiceImpl().register());
        services.add(new PlayerInfoServiceImpl().register());
        services.add(new MagicNumberServiceImpl().register());

        services.stream()
                .filter(service -> service instanceof Listener)
                .forEach(service -> Bukkit.getPluginManager().registerEvents((Listener) service, plugin));

        // Packet Listeners
        List<PacketListener<?>> listeners = new ArrayList<>();

        // Client-bound
        listeners.add(new LoginPacketListener());
        listeners.add(new PlayerInfoRemovePacketListener());
        listeners.add(new PlayerInfoUpdatePacketListener());
        listeners.add(new RegistryDataPacketListener());
        listeners.add(new ServerDataPacketListener());
        listeners.add(new UpdateAdvancementsPacketListener());
        listeners.add(new UpdateTagsPacketListener());

        // Server-bound
        listeners.add(new PaddleBoatPacketListener());
        listeners.add(new PlayerCommandPacketListener());
        listeners.add(new PlayerInputPacketListener());
        listeners.add(new SeenAdvancementsPacketListener());
        listeners.add(new SignUpdatePacketListener());

        // Bukkit Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(listeners), plugin); // registers packet listeners

    }

}
