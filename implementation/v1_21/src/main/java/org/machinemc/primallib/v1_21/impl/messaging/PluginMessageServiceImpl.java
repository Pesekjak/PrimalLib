package org.machinemc.primallib.v1_21.impl.messaging;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.machinemc.primallib.messaging.PluginMessageService;

public class PluginMessageServiceImpl extends PluginMessageService {

    @Override
    public void addChannel(Player player, String channel) {
        ((CraftPlayer) player).addChannel(channel);
    }

    @Override
    public void removeChannel(Player player, String channel) {
        ((CraftPlayer) player).removeChannel(channel);
    }

}
