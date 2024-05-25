package org.machinemc.primallib.event.server;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.With;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Is called when a {@link Player} receives server data update.
 * <p>
 * Should only be used for packet/clientside related stuff.
 * Not intended for modifying server side state.
 */
@Getter
@VersionDependant
public class PlayerServerDataEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private ServerData serverData;

    public PlayerServerDataEvent(Player who, ServerData serverData) {
        this(who, serverData, !Bukkit.isPrimaryThread());
    }

    public PlayerServerDataEvent(Player who, ServerData serverData, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.serverData = Preconditions.checkNotNull(serverData, "Server data can not be null");
    }

    /**
     * Changes server data sent to client.
     *
     * @param serverData new server data
     */
    public void setServerData(ServerData serverData) {
        this.serverData = Preconditions.checkNotNull(serverData, "Server data can not be null");
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Represents server data sent to client.
     *
     * @param motd motd of the server
     * @param iconData favicon data of the server
     */
    @With
    public record ServerData(Component motd, byte @Nullable [] iconData) {

        public ServerData {
            Preconditions.checkNotNull(motd, "MOTD can not be null");
        }

    }

}
