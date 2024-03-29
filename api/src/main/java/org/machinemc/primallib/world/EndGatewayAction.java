package org.machinemc.primallib.world;

import org.bukkit.Material;

/**
 * Action which triggers the purple beam emitted by the end gateway when
 * an entity passes through it.
 */
public final class EndGatewayAction implements BlockAction {

    @Override
    public Material[] supports() {
        return new Material[] {Material.END_GATEWAY};
    }

}
