package org.machinemc.primallib.messaging;

import com.google.common.base.Preconditions;
import io.papermc.paper.math.Position;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.util.MinecraftBuf;
import org.machinemc.primallib.util.OwnerPlugin;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Service that can manage
 */
@VersionDependant
public abstract class PluginMessageService extends AutoRegisteringService<PluginMessageService> {

    /**
     * Returns instance of plugin message service for currently running server.
     *
     * @return player info service
     */
    public static PluginMessageService get() {
        var provider = Bukkit.getServicesManager().getRegistration(PluginMessageService.class);
        Preconditions.checkNotNull(provider, "Plugin Message service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Adds new listenable channel for the player.
     *
     * @param player player
     * @param channel channel to add
     */
    public abstract void addChannel(Player player, String channel);

    /**
     * Removes channel from the player.
     *
     * @param player player
     * @param channel channel to remove
     */
    public abstract void removeChannel(Player player, String channel);

    /**
     * Sends data to a given channel for given player.
     *
     * @param player player to send the data to
     * @param channel name of the channel
     * @param data data to send
     */
    public void sendPluginMessage(Player player, String channel, byte[] data) {
        Preconditions.checkNotNull(channel, "Channel can not be null");
        player.sendPluginMessage(OwnerPlugin.get(), channel, data);
    }

    /**
     * Returns all added channels for given player.
     *
     * @param player player
     * @return registered channels for the player
     */
    public @Unmodifiable Set<String> getChannels(Player player) {
        return player.getListeningPluginChannels();
    }

    /**
     * Registers new outgoing channel.
     *
     * @param channel channel to register
     */
    public void registerOutgoingChannel(String channel) {
        Preconditions.checkNotNull(channel, "Channel can not be null");
        Messenger messenger = Bukkit.getMessenger();
        if (messenger.isOutgoingChannelRegistered(OwnerPlugin.get(), channel)) return;
        messenger.registerOutgoingPluginChannel(OwnerPlugin.get(), channel);
    }

    /**
     * Unregisters new outgoing channel.
     *
     * @param channel channel to unregister
     */
    public void unregisterOutgoingChannel(String channel) {
        Preconditions.checkNotNull(channel, "Channel can not be null");
        Messenger messenger = Bukkit.getMessenger();
        if (!messenger.isOutgoingChannelRegistered(OwnerPlugin.get(), channel)) return;
        messenger.unregisterOutgoingPluginChannel(OwnerPlugin.get(), channel);
    }

    /**
     * Registers new incoming channel.
     *
     * @param channel channel to register
     * @param listener listener
     */
    public void registerIncomingChannel(String channel, PluginMessageListener listener) {
        Preconditions.checkNotNull(channel, "Channel can not be null");
        Preconditions.checkNotNull(listener, "Listener can not be null");
        Messenger messenger = Bukkit.getMessenger();
        if (messenger.isIncomingChannelRegistered(OwnerPlugin.get(), channel)) return;
        messenger.registerIncomingPluginChannel(OwnerPlugin.get(), channel, listener);
    }

    /**
     * Unregisters new incoming channel.
     *
     * @param channel channel to unregister
     */
    public void unregisterIncomingChannel(String channel) {
        Preconditions.checkNotNull(channel, "Channel can not be null");
        Messenger messenger = Bukkit.getMessenger();
        if (!messenger.isIncomingChannelRegistered(OwnerPlugin.get(), channel)) return;
        messenger.unregisterIncomingPluginChannel(OwnerPlugin.get(), channel);
    }

    /**
     * Wrapper for minecraft brand channel.
     */
    public class Brand {

        /**
         * Identifier of the brand channel.
         */
        public static final String NAME = "minecraft:brand";

        private final String brand;

        public Brand(String brand) {
            this.brand = Preconditions.checkNotNull(brand, "Brand can not be null");
        }

        /**
         * Sends the brand channel update to players.
         *
         * @param players players to send the data to
         */
        public void send(Collection<Player> players) {
            players.forEach(this::send);
        }

        /**
         * Sends the brand channel update to players.
         *
         * @param players players to send the data to
         */
        public void send(Player... players) {
            send(List.of(players));
        }

        /**
         * Sends the brand channel update to player.
         *
         * @param player player to send the data to
         */
        public void send(Player player) {
            registerOutgoingChannel(NAME);
            addChannel(player, NAME);
            MinecraftBuf buf = MinecraftBuf.unpooled();
            buf.writeString(brand, StandardCharsets.UTF_8);
            sendPluginMessage(player, NAME, buf.array());
        }

    }

    /**
     * Wrapper for minecraft add marker channel.
     *
     * @apiNote Without shader modification color is rendered incorrectly
     * on the client, applying only alpha and green channel.
     */
    @ApiStatus.Experimental // this thing is partially broken and feels as a leftover Mojang forgot to remove
    @SuppressWarnings("UnstableApiUsage")
    public class AddMarker {

        /**
         * Identifier of the add marker channel.
         */
        public static final String NAME = "minecraft:debug/game_test_add_marker";

        private final Position position;
        private final Color color;
        private final String name;
        private final int lifetime;

        /**
         * Wrapper for minecraft add marker channel.
         *
         * @param position position of the marker in the world
         * @param color color of the marker, currently ignores blue and red channel
         * @param name name of the marker, can be empty string for no name
         * @param lifetime time in milliseconds, after which the marker will be destroyed
         */
        public AddMarker(Position position, Color color, String name, int lifetime) {
            this.position = Preconditions.checkNotNull(position, "Position can not be null");
            this.color = Preconditions.checkNotNull(color, "Color can not be null");
            this.name = Preconditions.checkNotNull(name, "Name can not be null");
            this.lifetime = lifetime;
        }

        /**
         * Sends the brand channel update to players.
         *
         * @param players players to send the data to
         */
        public void send(Collection<Player> players) {
            players.forEach(this::send);
        }

        /**
         * Sends the brand channel update to players.
         *
         * @param players players to send the data to
         */
        public void send(Player... players) {
            send(List.of(players));
        }

        /**
         * Sends the brand channel update to player.
         *
         * @param player player to send the data to
         */
        public void send(Player player) {
            registerOutgoingChannel(NAME);
            addChannel(player, NAME);
            MinecraftBuf buf = MinecraftBuf.unpooled();
            buf.writeBlockPosition(position.toBlock());
            buf.writeVarInt(color.asARGB());
            buf.writeString(name, StandardCharsets.UTF_8);
            buf.writeVarInt(lifetime);
            sendPluginMessage(player, NAME, buf.array());
        }

    }

    /**
     * Wrapper for minecraft clear markers channel.
     */
    @ApiStatus.Experimental // this thing feels as a leftover Mojang forgot to remove
    public class ClearMarkers {

        /**
         * Identifier of the brand channel.
         */
        public static final String NAME = "minecraft:debug/game_test_clear";

        /**
         * Sends the brand channel update to players.
         *
         * @param players players to send the data to
         */
        public void send(Collection<Player> players) {
            players.forEach(this::send);
        }

        /**
         * Sends the brand channel update to players.
         *
         * @param players players to send the data to
         */
        public void send(Player... players) {
            send(List.of(players));
        }

        /**
         * Sends the brand channel update to player.
         *
         * @param player player to send the data to
         */
        public void send(Player player) {
            registerOutgoingChannel(NAME);
            addChannel(player, NAME);
            sendPluginMessage(player, NAME, new byte[0]);
        }

    }

    @Override
    public Class<PluginMessageService> getRegistrationClass() {
        return PluginMessageService.class;
    }

}
