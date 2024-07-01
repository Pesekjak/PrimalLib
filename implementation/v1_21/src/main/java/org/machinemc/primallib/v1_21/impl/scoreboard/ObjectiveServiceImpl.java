package org.machinemc.primallib.v1_21.impl.scoreboard;

import com.google.common.collect.ImmutableList;
import io.papermc.paper.util.PaperScoreboardFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.craftbukkit.scoreboard.CraftScoreboardTranslations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.RenderType;
import org.jetbrains.annotations.Unmodifiable;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.scoreboard.Objective;
import org.machinemc.primallib.scoreboard.ObjectiveService;
import org.machinemc.primallib.v1_21.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_21.util.Converters;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObjectiveServiceImpl extends ObjectiveService implements Listener {

    private final Map<Player, Map<String, Objective>> cachedObjectives = new ConcurrentHashMap<>();
    private final Map<Player, Map<String, List<Objective.Entry>>> cachedEntries = new ConcurrentHashMap<>();

    public void setObjective(Player player, ClientboundSetObjectivePacket packet) {
        if (packet.getMethod() == ClientboundSetObjectivePacket.METHOD_REMOVE) {
            cachedObjectives.get(player).remove(packet.getObjectiveName());
            cachedEntries.get(player).remove(packet.getObjectiveName());
            return;
        }

        Objective objective = new Objective(
                packet.getObjectiveName(),
                Converters.fromMinecraft(packet.getDisplayName()),
                fromMinecraft(packet.getRenderType()),
                packet.getNumberFormat().map(PaperScoreboardFormat::asPaper).orElse(null)
        );

        Map<String, Objective> objectives = cachedObjectives.computeIfAbsent(player, p -> new ConcurrentHashMap<>());
        objectives.put(objective.name(), objective);
    }

    public void setScore(Player player, ClientboundSetScorePacket packet) {
        if (!cachedObjectives.get(player).containsKey(packet.objectiveName())) return; // updating entry on non-existing objective

        Objective.Entry entry = new Objective.Entry(
                EntityLike.entityLikeFromString(packet.owner()),
                packet.score(),
                packet.display().map(Converters::fromMinecraft).orElse(null),
                packet.numberFormat().map(PaperScoreboardFormat::asPaper).orElse(null)
        );

        List<Objective.Entry> entries = cachedEntries.get(player).computeIfAbsent(packet.objectiveName(), name -> new CopyOnWriteArrayList<>());
        entries.add(entry);
    }

    public void resetScore(Player player, ClientboundResetScorePacket packet) {
        // resets score for all objectives
        if (packet.objectiveName() == null) {
            cachedEntries.get(player).clear();
            return;
        }

        List<Objective.Entry> scores = cachedEntries.get(player).get(packet.objectiveName());
        if (scores == null) return;
        scores.removeIf(entry -> entry.source().getStringRepresentation().equals(packet.owner()));
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        cachedObjectives.put(event.getPlayer(), new ConcurrentHashMap<>());
        cachedEntries.put(event.getPlayer(), new ConcurrentHashMap<>());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        cachedObjectives.remove(event.getPlayer());
        cachedEntries.remove(event.getPlayer());
    }

    @Override
    public void updateEntry(Player player, Objective objective, Objective.Entry entry) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                new ClientboundSetScorePacket(
                        entry.source().getStringRepresentation(),
                        objective.name(),
                        entry.score(),
                        Optional.ofNullable(Converters.toMinecraft(objective.displayName())),
                        entry.numberFormat() != null
                                ? Optional.of(PaperScoreboardFormat.asVanilla(entry.numberFormat()))
                                : Optional.empty()
                ),
                false
        );
    }

    @Override
    public void resetScore(Player player, Objective objective, EntityLike... targets) {
        for (EntityLike target : targets)
            PacketChannelHandlerImpl.sendPacket(
                    player,
                    new ClientboundResetScorePacket(target.getStringRepresentation(), objective.name()),
                    false
            );
    }

    @Override
    public void displayObjective(Player player, Objective objective, DisplaySlot slot) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                new ClientboundSetDisplayObjectivePacket(CraftScoreboardTranslations.fromBukkitSlot(slot), toMinecraft(objective)),
                false
        );
    }

    @Override
    public void resetObjectiveDisplay(Player player, DisplaySlot slot) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                new ClientboundSetDisplayObjectivePacket(
                        CraftScoreboardTranslations.fromBukkitSlot(slot),
                        toMinecraft(new Objective(""))),
                false
        );
    }

    @Override
    public void createObjective(Player player, Objective objective) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                new ClientboundSetObjectivePacket(toMinecraft(objective), ClientboundSetObjectivePacket.METHOD_ADD),
                false
        );
    }

    @Override
    public void removeObjective(Player player, String name) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                new ClientboundSetObjectivePacket(toMinecraft(new Objective(name)), ClientboundSetObjectivePacket.METHOD_REMOVE),
                false
        );
    }

    @Override
    public void updateObjective(Player player, Objective objective) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                new ClientboundSetObjectivePacket(toMinecraft(objective), ClientboundSetObjectivePacket.METHOD_CHANGE),
                false
        );
    }

    @Override
    public List<Objective> getObjectives(Player player) {
        return ImmutableList.copyOf(cachedObjectives.get(player).values());
    }

    @Override
    public Optional<Objective> getObjective(Player player, String name) {
        return Optional.ofNullable(cachedObjectives.get(player).get(name));
    }

    @Override
    public @Unmodifiable List<Objective.Entry> getObjectiveEntries(Player player, Objective objective) {
        return ImmutableList.copyOf(cachedEntries.get(player).get(objective.name()));
    }

    private static net.minecraft.world.scores.Objective toMinecraft(Objective objective) {
        Component displayName = Converters.toMinecraft(objective.displayName());
        return new net.minecraft.world.scores.Objective(
                new Scoreboard(),
                objective.name(),
                ObjectiveCriteria.DUMMY,
                displayName != null ? displayName : Component.literal(objective.name()),
                toMinecraft(objective.renderType()), // default placeholder
                false,
                PaperScoreboardFormat.asVanilla(objective.numberFormat())
        );
    }

    private static RenderType fromMinecraft(ObjectiveCriteria.RenderType renderType) {
        return switch (renderType) {
            case INTEGER -> RenderType.INTEGER;
            case HEARTS -> RenderType.HEARTS;
        };
    }

    private static ObjectiveCriteria.RenderType toMinecraft(RenderType renderType) {
        return switch (renderType) {
            case INTEGER -> ObjectiveCriteria.RenderType.INTEGER;
            case HEARTS -> ObjectiveCriteria.RenderType.HEARTS;
        };
    }

}
