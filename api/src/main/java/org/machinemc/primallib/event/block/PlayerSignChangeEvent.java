package org.machinemc.primallib.event.block;

import com.google.common.base.Preconditions;
import io.papermc.paper.math.BlockPosition;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.machinemc.primallib.version.VersionDependant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Is called when a {@link Player} changes a sign.
 * <p>
 * This does not check whether the sign player edited actually exist,
 * so this event can be used for sign input menus.
 * <p>
 * Lines and sign side can be changed to alter the final state.
 * <p>
 * Should only be used for packet/clientside related stuff.
 * Not intended for modifying server side state, but can modify
 * server state if the values are changed. It is recommended to
 * use {@link org.bukkit.event.block.SignChangeEvent} for server side
 * modification instead.
 *
 * @see org.machinemc.primallib.player.PlayerActionService#openSign(Player, BlockPosition, Side)
 */
@Getter
@VersionDependant
@SuppressWarnings("UnstableApiUsage")
public class PlayerSignChangeEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Side side;
    private final List<String> lines;
    private final BlockPosition position;

    @Setter
    private boolean cancelled = false;

    public PlayerSignChangeEvent(Player who, Side side, List<String> lines, BlockPosition position) {
        this(who, side, lines, position, !Bukkit.isPrimaryThread());
    }

    public PlayerSignChangeEvent(Player who, Side side, List<String> lines, BlockPosition position, boolean async) {
        super(Preconditions.checkNotNull(who, "Player can not be null"), async);
        this.side = Preconditions.checkNotNull(side, "Side can not be null");
        Preconditions.checkNotNull(lines, "Lines can not be null");
        Preconditions.checkState(lines.size() == 4, "Line collection has to have 4 elements");
        Preconditions.checkState(lines.stream().noneMatch(Objects::isNull), "Line content can not be null");
        this.lines = new ArrayList<>(lines);
        this.position = Preconditions.checkNotNull(position, "Position can not be null");
    }

    /**
     * Changes the side changed by player.
     *
     * @param side side player changed
     */
    public void setSide(Side side) {
        this.side = Preconditions.checkNotNull(side, "Side can not be null");
    }

    /**
     * Returns lines player input on the sign.
     *
     * @return player lines input
     */
    public @Unmodifiable List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

    /**
     * Changes line player input on the sign.
     *
     * @param line index of the line (between 0 and 3)
     * @param content new input content
     */
    public void setLine(int line, String content) {
        Preconditions.checkState(0 <= line && line < 4, "Line index is out of bounds");
        lines.set(line, Preconditions.checkNotNull(content, "Line content can not be null"));
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}