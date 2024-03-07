package org.machinemc.primallib.internal;

/**
 * Represents a packet listener.
 *
 * @param <T> packet type
 */
public interface PacketListener<T> {

    /**
     * Fired when player receives a packet.
     * <p>
     * Player, channel and packet data are contained within the
     * provided packet event.
     *
     * @param event packet event
     */
    void onPacket(PacketEvent<T> event) throws Exception;

    /**
     * Returns type of this packet listener.
     *
     * @return type of this listener
     */
    Type type();

    /**
     * Class of the packet this listener is listening to.
     *
     * @return packet class of this listener
     */
    Class<T> packetClass();

    enum Type {

        /**
         * Used for packets from server to client.
         */
        CLIENT_BOUND,

        /**
         * Used for packets from client to server.
         */
        SERVER_BOUND

    }

}
