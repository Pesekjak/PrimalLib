package org.machinemc.primallib.internal;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Event fired when player receives a packet.
 *
 * @param <T> packet type
 */
@Getter
public final class PacketEvent<T> implements Cancellable {

    private final Player player;
    private final PacketChannelHandler<T, ?> channel;
    private T packet;

    @Setter
    private boolean cancelled = false;

    public PacketEvent(Player player, PacketChannelHandler<T, ?> channel, T packet) {
        this.player = Preconditions.checkNotNull(player, "Player can not be null");
        this.channel = Preconditions.checkNotNull(channel, "Channel can not be null");
        this.packet = Preconditions.checkNotNull(packet, "Packet can not be null");
    }

    /**
     * Changes the packet of this event.
     * <p>
     * If changed the new packet will be sent down the pipeline instead of
     * the original one.
     *
     * @param packet new packet to send
     */
    public void setPacket(T packet) {
        this.packet = Preconditions.checkNotNull(packet, "Packet can not be null");
    }

}
