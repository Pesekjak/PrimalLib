package org.machinemc.primallib.event.player;

import com.destroystokyo.paper.ClientOption;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.MainHand;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.player.SkinPart;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Called when the player changes their client settings.
 * <p>
 * Compare to the one provided by Paper API, this event can have values changed and can
 * be cancelled to modify the future server-side state.
 */
@Getter
@Setter
@VersionDependant
public class PlayerClientOptionsChangeEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private String locale;
    private int viewDistance;
    private ClientOption.ChatVisibility chatVisibility;
    private boolean chatColors;
    private SkinPart[] skinParts;
    private MainHand mainHand;
    private boolean allowsServerListings;
    private boolean textFilteringEnabled;

    private boolean cancelled = false;

    public PlayerClientOptionsChangeEvent(Player who,
                                          String locale,
                                          int viewDistance,
                                          ClientOption.ChatVisibility chatVisibility,
                                          boolean chatColors,
                                          SkinPart[] skinParts,
                                          MainHand mainHand,
                                          boolean allowsServerListings,
                                          boolean textFilteringEnabled) {
        this(
                who,
                locale,
                viewDistance,
                chatVisibility,
                chatColors,
                skinParts,
                mainHand,
                allowsServerListings,
                textFilteringEnabled,
                !Bukkit.isPrimaryThread()
        );
    }

    public PlayerClientOptionsChangeEvent(Player who,
                                          String locale,
                                          int viewDistance,
                                          ClientOption.ChatVisibility chatVisibility,
                                          boolean chatColors,
                                          SkinPart[] skinParts,
                                          MainHand mainHand,
                                          boolean allowsServerListings,
                                          boolean textFilteringEnabled,
                                          boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.locale = Preconditions.checkNotNull(locale, "Locale can not be null");
        this.viewDistance = viewDistance;
        this.chatVisibility = Preconditions.checkNotNull(chatVisibility, "Chat visibility can not be null");
        this.chatColors = chatColors;
        this.skinParts = skinParts;
        this.mainHand = mainHand;
        this.allowsServerListings = allowsServerListings;
        this.textFilteringEnabled = textFilteringEnabled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
