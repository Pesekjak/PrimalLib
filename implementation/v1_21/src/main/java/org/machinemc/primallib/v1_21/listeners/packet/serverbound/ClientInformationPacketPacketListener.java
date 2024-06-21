package org.machinemc.primallib.v1_21.listeners.packet.serverbound;

import com.destroystokyo.paper.ClientOption;
import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import org.bukkit.inventory.MainHand;
import org.machinemc.primallib.event.player.PlayerClientOptionsChangeEvent;
import org.machinemc.primallib.internal.PacketEvent;
import org.machinemc.primallib.internal.PacketListener;
import org.machinemc.primallib.player.SkinPart;

public class ClientInformationPacketPacketListener implements PacketListener<ServerboundClientInformationPacket> {

    @Override
    public void onPacket(PacketEvent<ServerboundClientInformationPacket> event) throws Exception {
        ClientInformation information = event.getPacket().information();
        PlayerClientOptionsChangeEvent optionsChangeEvent = new PlayerClientOptionsChangeEvent(
                event.getPlayer(),
                information.language(),
                information.viewDistance(),
                ClientOption.ChatVisibility.valueOf(information.chatVisibility().name()),
                information.chatColors(),
                SkinPart.fromMask(information.modelCustomisation()),
                information.mainHand() == HumanoidArm.LEFT ? MainHand.LEFT : MainHand.RIGHT,
                information.allowsListing(),
                information.textFilteringEnabled()
        );
        if (!optionsChangeEvent.callEvent()) {
            event.setCancelled(true);
            return;
        }
        ClientInformation updated = new ClientInformation(
                optionsChangeEvent.getLocale(),
                optionsChangeEvent.getViewDistance(),
                ChatVisiblity.valueOf(optionsChangeEvent.getChatVisibility().name()),
                optionsChangeEvent.isChatColors(),
                SkinPart.createMask(optionsChangeEvent.getSkinParts()),
                optionsChangeEvent.getMainHand() == MainHand.LEFT ? HumanoidArm.LEFT : HumanoidArm.RIGHT,
                optionsChangeEvent.isTextFilteringEnabled(),
                optionsChangeEvent.isAllowsServerListings()
        );
        event.setPacket(new ServerboundClientInformationPacket(updated));
    }

    @Override
    public Type type() {
        return Type.SERVER_BOUND;
    }

    @Override
    public Class<ServerboundClientInformationPacket> packetClass() {
        return ServerboundClientInformationPacket.class;
    }

}
