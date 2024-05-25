package org.machinemc.primallib.v1_20_6.listeners.packet.clientbound;

import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import org.machinemc.primallib.event.player.PlayerLoginEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;

public class LoginPacketListener implements PacketListener<ClientboundLoginPacket> {

    @Override
    public void onPacket(PacketEvent<ClientboundLoginPacket> event) {
        var packet = event.getPacket();
        CommonPlayerSpawnInfo spawnInfo = packet.commonPlayerSpawnInfo();
        PlayerLoginEvent playerLoginEvent = new PlayerLoginEvent(
                event.getPlayer(),
                packet.hardcore(),
                packet.reducedDebugInfo(),
                spawnInfo.isDebug(),
                spawnInfo.isFlat(),
                packet.enforcesSecureChat()
        );
        playerLoginEvent.callEvent();
        spawnInfo = new CommonPlayerSpawnInfo(
                spawnInfo.dimensionType(),
                spawnInfo.dimension(),
                spawnInfo.seed(),
                spawnInfo.gameType(),
                spawnInfo.previousGameType(),

                playerLoginEvent.isDebug(),
                playerLoginEvent.isFlat(),

                spawnInfo.lastDeathLocation(),
                spawnInfo.portalCooldown()
        );
        packet = new ClientboundLoginPacket(
                packet.playerId(),

                playerLoginEvent.isHardcore(),

                packet.levels(),
                packet.maxPlayers(),
                packet.chunkRadius(),
                packet.simulationDistance(),

                playerLoginEvent.isReducedDebugInfo(),

                packet.showDeathScreen(),
                packet.doLimitedCrafting(),
                spawnInfo,
                playerLoginEvent.isEnforcesSecureChat()
        );
        event.setPacket(packet);
    }

    @Override
    public Type type() {
        return Type.CLIENT_BOUND;
    }

    @Override
    public Class<ClientboundLoginPacket> packetClass() {
        return ClientboundLoginPacket.class;
    }

}
