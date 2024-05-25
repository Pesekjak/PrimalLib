package org.machinemc.primallib.v1_20_6.util.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Data required for correct packet manipulation during the client reconfiguration process.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class PlayerReconfigurationData {

    private final Player player;

    private State state = State.NONE;

    /**
     * Packets that have been filtered while player was in reconfiguration.
     */
    private final List<Packet<?>> eaten = new ArrayList<>();

    /**
     * During the configuration, the active connection packet listener is
     * replaced with {@link PaperServerConfigurationPacketListener}.
     * <p>
     * This is the old packet listener reference that will be used later to replace
     * the paper configuration listener to return everything back to previous state.
     */
    private @Nullable ServerGamePacketListener oldListener;

    /**
     * State of the player reconfiguration process.
     */
    public enum State {

        /**
         * Player does not expect any special action.
         */
        NONE,

        /**
         * Client awaits configuration request.
         */
        AWAITING_CONFIGURATION_REQ,

        /**
         * Server awaits configuration acknowledgement from the client.
         */
        AWAITING_CONFIGURATION_ACK,

        /**
         * Client is currently in configuration.
         */
        IN_CONFIGURATION

    }

}
