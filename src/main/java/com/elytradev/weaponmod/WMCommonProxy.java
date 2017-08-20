package com.elytradev.weaponmod;

import com.elytradev.weaponmod.network.MsgCannonFire;
import com.elytradev.weaponmod.network.MsgExplosion;
import com.elytradev.weaponmod.network.WMMessagePipeline;
import net.minecraftforge.common.MinecraftForge;

public class WMCommonProxy {
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new WMCommonEventHandler());
    }

    public void registerPackets(WMMessagePipeline pipeline) {
        pipeline.registerPacket(MsgCannonFire.class);
        pipeline.registerPacket(MsgExplosion.class);
    }

    public void registerIcons() {
    }

    public void registerRenderers(WeaponModConfig config) {
    }
}
