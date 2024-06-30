package org.machinemc.primallib.v1_21.impl.scoreboard;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.scoreboard.Team;
import org.machinemc.primallib.scoreboard.TeamService;
import org.machinemc.primallib.v1_21.internal.PacketChannelHandlerImpl;
import org.machinemc.primallib.v1_21.util.Converters;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TeamServiceImpl extends TeamService implements Listener {

    private final Map<Player, Map<String, Team>> cachedTeams = new ConcurrentHashMap<>();
    private final Map<Player, Map<String, List<EntityLike>>> cachedEntries = new ConcurrentHashMap<>();

    public void setTeam(Player player, ClientboundSetPlayerTeamPacket packet) {
        ClientboundSetPlayerTeamPacket.Action teamAction = packet.getTeamAction();
        ClientboundSetPlayerTeamPacket.Action playerAction = packet.getPlayerAction();

        // team parameters update
        if (teamAction == null && playerAction == null) {
            if (!cachedTeams.get(player).containsKey(packet.getName())) return; // updating non existing team
            updateTeam(player, packet);
            return;
        }

        if (teamAction == ClientboundSetPlayerTeamPacket.Action.ADD)
            updateTeam(player, packet);
        else if (teamAction == ClientboundSetPlayerTeamPacket.Action.REMOVE)
            removeTeam(player, packet);

        if (playerAction == ClientboundSetPlayerTeamPacket.Action.ADD)
            addEntries(player, packet);
        else if (playerAction == ClientboundSetPlayerTeamPacket.Action.REMOVE)
            removeEntries(player, packet);
    }

    private void updateTeam(Player player, ClientboundSetPlayerTeamPacket packet) {
        ClientboundSetPlayerTeamPacket.Parameters params = packet.getParameters().orElseThrow();
        Team team = new Team(
                packet.getName(),
                Converters.fromMinecraft(params.getDisplayName()),
                Converters.fromMinecraft(params.getColor()))
                .withPrefix(Converters.fromMinecraft(params.getPlayerPrefix()))
                .withSuffix(Converters.fromMinecraft(params.getPlayerSuffix()))
                .withNameTagVisibility(fromMinecraft(net.minecraft.world.scores.Team.Visibility.byName(params.getNametagVisibility())))
                .withCollisionRule(fromMinecraft(net.minecraft.world.scores.Team.CollisionRule.byName(params.getCollisionRule())))
                .withAllowFriendlyFire((params.getOptions() & 0x01) != 0)
                .withSeeFriendlyInvisible((params.getOptions() & 0x02) != 0);
        cachedTeams.get(player).put(team.name(), team);
    }

    private void removeTeam(Player player, ClientboundSetPlayerTeamPacket packet) {
        cachedTeams.get(player).remove(packet.getName());
        cachedEntries.get(player).remove(packet.getName());
    }

    private void addEntries(Player player, ClientboundSetPlayerTeamPacket packet) {
        List<EntityLike> entities = cachedEntries.get(player).computeIfAbsent(packet.getName(), name -> new CopyOnWriteArrayList<>());
        for (String entry : packet.getPlayers())
            entities.add(entityLikeFromString(entry));
    }

    private void removeEntries(Player player, ClientboundSetPlayerTeamPacket packet) {
        List<EntityLike> entities = cachedEntries.get(player).get(packet.getName());
        if (entities == null) return;
        for (String entry : packet.getPlayers())
            entities.remove(entityLikeFromString(entry));
    }

    private static EntityLike entityLikeFromString(String string) {
        if (string.contains("-")) {
            try {
                return EntityLike.of(-1, UUID.fromString(string), null);
            } catch (Exception ignored) { }
        }
        return EntityLike.of(-1, null, string);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        cachedTeams.put(event.getPlayer(), new ConcurrentHashMap<>());
        cachedEntries.put(event.getPlayer(), new ConcurrentHashMap<>());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        cachedTeams.remove(event.getPlayer());
        cachedEntries.remove(event.getPlayer());
    }

    @Override
    public void createTeam(Player player, Team team) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(toMinecraft(team), true),
                false
        );
    }

    @Override
    public void removeTeam(Player player, String name) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                ClientboundSetPlayerTeamPacket.createRemovePacket(new PlayerTeam(new Scoreboard(), name)),
                false
        );
    }

    @Override
    public void updateTeam(Player player, Team team) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(toMinecraft(team), false),
                false
        );
    }

    @Override
    public void addToTeam(Player player, Team team, EntityLike... entities) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                ClientboundSetPlayerTeamPacket.createMultiplePlayerPacket(
                        toMinecraft(team),
                        Arrays.stream(entities).map(EntityLike::getStringRepresentation).toList(),
                        ClientboundSetPlayerTeamPacket.Action.ADD
                ),
                false
        );
    }

    @Override
    public void removeFromTeam(Player player, Team team, EntityLike... entities) {
        PacketChannelHandlerImpl.sendPacket(
                player,
                ClientboundSetPlayerTeamPacket.createMultiplePlayerPacket(
                        toMinecraft(team),
                        Arrays.stream(entities).map(EntityLike::getStringRepresentation).toList(),
                        ClientboundSetPlayerTeamPacket.Action.REMOVE
                ),
                false
        );
    }

    @Override
    public List<Team> getTeams(Player player) {
        return ImmutableList.copyOf(cachedTeams.get(player).values());
    }

    @Override
    public Optional<Team> getTeam(Player player, String name) {
        return Optional.ofNullable(cachedTeams.get(player).get(name));
    }

    @Override
    public @Unmodifiable List<EntityLike> getTeamEntries(Player player, Team team) {
        return ImmutableList.copyOf(cachedEntries.get(player).get(team.name()));
    }

    private static PlayerTeam toMinecraft(Team team) {
        PlayerTeam playerTeam = new PlayerTeam(new Scoreboard(), team.name());
        playerTeam.setDisplayName(Converters.toMinecraft(team.displayName()));
        playerTeam.setPlayerPrefix(Converters.toMinecraft(team.prefix()));
        playerTeam.setPlayerSuffix(Converters.toMinecraft(team.suffix()));
        playerTeam.setNameTagVisibility(toMinecraft(team.nameTagVisibility()));
        playerTeam.setCollisionRule(toMinecraft(team.collisionRule()));
        playerTeam.setColor(Converters.toMinecraft(team.color()));
        playerTeam.setAllowFriendlyFire(team.allowFriendlyFire());
        playerTeam.setSeeFriendlyInvisibles(team.seeFriendlyInvisible());
        return playerTeam;
    }

    private static Team.Visibility fromMinecraft(@Nullable net.minecraft.world.scores.Team.Visibility visibility) {
        if (visibility == null) return Team.Visibility.ALWAYS;
        return switch (visibility) {
            case ALWAYS -> Team.Visibility.ALWAYS;
            case NEVER -> Team.Visibility.NEVER;
            case HIDE_FOR_OTHER_TEAMS -> Team.Visibility.HIDE_FOR_OTHER;
            case HIDE_FOR_OWN_TEAM -> Team.Visibility.HIDE_FOR_OWN;
        };
    }

    private static net.minecraft.world.scores.Team.Visibility toMinecraft(@Nullable Team.Visibility visibility) {
        if (visibility == null) return net.minecraft.world.scores.Team.Visibility.ALWAYS;
        return switch (visibility) {
            case ALWAYS -> net.minecraft.world.scores.Team.Visibility.ALWAYS;
            case HIDE_FOR_OTHER -> net.minecraft.world.scores.Team.Visibility.HIDE_FOR_OTHER_TEAMS;
            case HIDE_FOR_OWN -> net.minecraft.world.scores.Team.Visibility.HIDE_FOR_OWN_TEAM;
            case NEVER -> net.minecraft.world.scores.Team.Visibility.NEVER;
        };
    }

    private static Team.Collisions fromMinecraft(@Nullable net.minecraft.world.scores.Team.CollisionRule collisionRule) {
        if (collisionRule == null) return Team.Collisions.ALWAYS;
        return switch (collisionRule) {
            case ALWAYS -> Team.Collisions.ALWAYS;
            case NEVER -> Team.Collisions.NEVER;
            case PUSH_OTHER_TEAMS -> Team.Collisions.PUSH_OTHER_TEAMS;
            case PUSH_OWN_TEAM -> Team.Collisions.PUSH_OWN_TEAM;
        };
    }

    private static net.minecraft.world.scores.Team.CollisionRule toMinecraft(@Nullable Team.Collisions collisions) {
        if (collisions == null) return net.minecraft.world.scores.Team.CollisionRule.ALWAYS;
        return switch (collisions) {
            case ALWAYS -> net.minecraft.world.scores.Team.CollisionRule.ALWAYS;
            case PUSH_OTHER_TEAMS -> net.minecraft.world.scores.Team.CollisionRule.PUSH_OTHER_TEAMS;
            case PUSH_OWN_TEAM -> net.minecraft.world.scores.Team.CollisionRule.PUSH_OWN_TEAM;
            case NEVER -> net.minecraft.world.scores.Team.CollisionRule.NEVER;
        };
    }

}
