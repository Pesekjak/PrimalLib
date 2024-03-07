package org.machinemc.primallib.internal;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Packet channel logic shared across all versions.
 *
 * @param <T> packet type
 */
public abstract class PacketChannelHandler<T> extends ChannelDuplexHandler {

    /**
     * Unique name for the channel that can be used for channel registration.
     * <p>
     * This name is unique for each source jar, this ensures compatibility across
     * multiple plugins shading in PrimalLib classes.
     */
    public static final String NAME;

    static {
        String uniqueName;
        try {
            File source = new File(PacketChannelHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            uniqueName = "primal_lib_" + source.getName().hashCode();
        } catch (URISyntaxException exception) {
            uniqueName = "primal_lib_unknown"; // should not happen
        }
        NAME = uniqueName;
    }

    private final List<T> toSkip = Collections.synchronizedList(new ArrayList<>());

    private final Multimap<Class<?>, PacketListener<?>> clientBoundListeners = Multimaps.newMultimap(
            new ConcurrentHashMap<>(),
            () -> Collections.synchronizedList(new ArrayList<>())
    );

    private final Multimap<Class<?>, PacketListener<?>> serverBoundListeners = Multimaps.newMultimap(
            new ConcurrentHashMap<>(),
            () -> Collections.synchronizedList(new ArrayList<>())
    );

    /**
     * Checks whether provided packet listener is valid and can be registered.
     *
     * @param packetListener packet listener to check
     */
    private void ensureValidListener(PacketListener<?> packetListener) {
        Preconditions.checkNotNull(packetListener, "Packet listener can not be null");
        Preconditions.checkNotNull(packetListener.type(), "Type of the packet listener can not be null");
        Preconditions.checkNotNull(packetListener.packetClass(), "Packet class for the packet listener can not be null");
    }

    /**
     * Registers new listener for this channel.
     *
     * @param packetListener listener to register
     */
    public void registerListener(PacketListener<?> packetListener) {
        ensureValidListener(packetListener);

        var map = packetListener.type() == PacketListener.Type.CLIENT_BOUND
                ? clientBoundListeners
                : serverBoundListeners;

        map.put(packetListener.packetClass(), packetListener);
    }

    /**
     * Unregisters listener for this channel.
     *
     * @param packetListener listener to register
     */
    public void unregisterListener(PacketListener<?> packetListener) {
        ensureValidListener(packetListener);

        var map = packetListener.type() == PacketListener.Type.CLIENT_BOUND
                ? clientBoundListeners
                : serverBoundListeners;

        map.remove(packetListener.packetClass(), packetListener);
    }

    /**
     * Sends packet to the player owning this channel handler.
     *
     * @param packet packet to send
     * @param skipListeners whether the packet should be ignored by the packet listeners
     */
    public void sendPacket(T packet, boolean skipListeners) {
        if (skipListeners) toSkip.add(packet);
        sendPacket(packet);
    }

    /**
     * Sends packet to the player owning this channel handler.
     *
     * @param packet packet to send
     * @apiNote this method should not be exposed or used, use
     *          {@link #sendPacket(Object, boolean)} instead.
     *          This method needs to implement the logic of
     *          sending the packet.
     */
    protected abstract void sendPacket(T packet);

    /**
     * Returns owner player of this handler.
     *
     * @return player of this channel
     */
    protected abstract Player getOwner();

    // Client-bound packets (S -> C)
    @Override
    @SuppressWarnings("unchecked")
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        T packet = (T) msg;

        if (toSkip.contains(packet)) {
            super.write(ctx, packet, promise);
            return;
        }

        PacketEvent<?> event = new PacketEvent<>(getOwner(), this, packet);
        fireListeners(event, PacketListener.Type.CLIENT_BOUND);

        if (event.isCancelled()) return;
        super.write(ctx, event.getPacket(), promise);
    }

    // Server-bound packets (C -> S)
    @Override
    @SuppressWarnings("unchecked")
    public void channelRead(@NotNull ChannelHandlerContext ctx, @NotNull Object msg) throws Exception {
        T packet = (T) msg;

        if (toSkip.contains(packet)) {
            super.channelRead(ctx, packet);
            return;
        }

        PacketEvent<?> event = new PacketEvent<>(getOwner(), this, packet);
        fireListeners(event, PacketListener.Type.SERVER_BOUND);

        if (event.isCancelled()) return;
        super.channelRead(ctx, event.getPacket());
    }

    @SuppressWarnings("unchecked")
    private <E> void fireListeners(PacketEvent<E> packetEvent, PacketListener.Type direction) throws Exception {
        List<Class<?>> all = ClassUtils.collectParents(packetEvent.getPacket().getClass());

        var map = direction == PacketListener.Type.CLIENT_BOUND
                ? clientBoundListeners
                : serverBoundListeners;

        for (Class<?> parent : all)
            for (PacketListener<?> listener : map.get(parent))
                ((PacketListener<E>) listener).onPacket(packetEvent);
    }

}
