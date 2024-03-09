package org.machinemc.primallib.v1_20_4.listeners.bukkit;

import lombok.RequiredArgsConstructor;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.machinemc.primallib.internal.PacketChannelHandler;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.v1_20_4.internal.PacketChannelHandlerImpl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collection;

@RequiredArgsConstructor
public class PlayerLoginListener implements Listener {

    private final Collection<PacketListener<?>> packetListeners;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        CraftPlayer player = (CraftPlayer) event.getPlayer();
        InetAddress address = event.getAddress();

        // On player login, player does not linked connection instance to itself,
        // this is the only way how to access it during login phase.
        Connection playerConnection = MinecraftServer.getServer().getConnection().getConnections().stream()
                .filter(connection -> connection.getRemoteAddress() instanceof InetSocketAddress)
                .filter(connection -> ((InetSocketAddress) connection.getRemoteAddress()).getAddress() == address)
                .findAny().orElse(null);

        if (playerConnection == null) return;

        if (playerConnection.channel.pipeline().get(PacketChannelHandler.NAME) != null) return;

        var handler = new PacketChannelHandlerImpl(player);
        playerConnection.channel.pipeline().addBefore("packet_handler", PacketChannelHandler.NAME, handler);

        packetListeners.forEach(handler::registerListener);
    }

}
