package com.elytradev.weaponmod;

import net.minecraftforge.common.MinecraftForge;

public class WMCommonProxy {
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new WMCommonEventHandler());
    }

    public void registerIcons() {
    }

    public void registerRenderers(WeaponModConfig config) {
    }
}
