package org.machinemc.primallib.v1_20_6.internal;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.protocol.BundlePacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.machinemc.primallib.internal.PacketChannelHandler;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class PacketChannelHandlerImpl extends PacketChannelHandler<Packet<?>, BundlePacket<?>> {

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
        owner.getHandle().connection.connection.send(packet);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<BundlePacket<?>> getBundlePacketType() {
        return (Class<BundlePacket<?>>) (Class<?>) BundlePacket.class;
    }

    @Override
    protected List<Packet<?>> unwrap(BundlePacket<?> bundlePacket) {
        return Lists.newArrayList(bundlePacket.subPackets());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected BundlePacket<?> bundle(List<Packet<?>> packets) {
        return new ClientboundBundlePacket((Iterable<Packet<? super ClientGamePacketListener>>) (Iterable<?>) packets);
    }

}
