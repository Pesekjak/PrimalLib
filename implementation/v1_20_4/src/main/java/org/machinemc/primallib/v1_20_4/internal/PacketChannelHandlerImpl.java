package org.machinemc.primallib.v1_20_4.internal;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.machinemc.primallib.internal.PacketChannelHandler;

import java.net.InetSocketAddress;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class PacketChannelHandlerImpl extends PacketChannelHandler<Packet<?>> {

    private final CraftPlayer owner;

    @SuppressWarnings("ConstantConditions") // player connection can be null
    public static Optional<PacketChannelHandlerImpl> get(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;

        Channel channel;
        var connection = craftPlayer.getHandle().connection;

        if (connection != null && craftPlayer.isConnected()) {
            channel = connection.connection.channel;
        } else {
            InetSocketAddress address = player.getAddress();
            channel = MinecraftServer.getServer().getConnection().getConnections().stream()
                    .filter(c -> c.getRemoteAddress() == address)
                    .map(c -> c.channel)
                    .findAny().orElse(null);
        }

        if (channel == null) return Optional.empty();

        return Optional.of((PacketChannelHandlerImpl) channel.pipeline().get(PacketChannelHandler.NAME));
    }

    public static boolean sendPacket(Player player, Packet<?> packet, boolean skipListeners) {
        var handler = PacketChannelHandlerImpl.get(player).orElse(null);
        if (handler == null) return false;
        handler.sendPacket(packet, skipListeners);
        return true;
    }

    @Override
    protected void sendPacket(Packet<?> packet) {
        owner.getHandle().connection.send(packet);
    }

}
