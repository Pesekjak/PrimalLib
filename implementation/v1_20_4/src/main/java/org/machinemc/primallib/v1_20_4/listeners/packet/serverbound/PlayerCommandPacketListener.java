package org.machinemc.primallib.v1_20_4.listeners.packet.serverbound;

import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.machinemc.primallib.event.player.PlayerLeaveBedEvent;
import org.machinemc.primallib.event.player.PlayerOpenVehicleInventoryEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;

public class PlayerCommandPacketListener implements PacketListener<ServerboundPlayerCommandPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundPlayerCommandPacket> event) {
        var packet = event.getPacket();

        Event actionEvent;

        switch (packet.getAction()) {
            case STOP_SLEEPING -> actionEvent = new PlayerLeaveBedEvent(event.getPlayer());
            case OPEN_INVENTORY -> actionEvent = new PlayerOpenVehicleInventoryEvent(event.getPlayer());
            default -> actionEvent = null;
        }

        if (actionEvent == null) return;
        actionEvent.callEvent();

        Cancellable cancellable = (Cancellable) actionEvent;
        if (cancellable.isCancelled()) event.setCancelled(true);
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundPlayerCommandPacket> packetClass() {
        return ServerboundPlayerCommandPacket.class;
    }

}
