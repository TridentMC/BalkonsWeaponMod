package com.elytradev.weaponmod;

import com.elytradev.weaponmod.entity.EntityCannon;
import com.elytradev.weaponmod.entity.EntityDummy;

import com.elytradev.weaponmod.entity.projectile.*;
import com.elytradev.weaponmod.render.*;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class WMClientProxy extends WMCommonProxy {
    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        WMClientEventHandler eventhandler = new WMClientEventHandler();
        FMLCommonHandler.instance().bus().register(eventhandler);
        MinecraftForge.EVENT_BUS.register(eventhandler);
        MinecraftForge.EVENT_BUS.register(new GuiOverlayReloaded(FMLClientHandler.instance().getClient()));

    }

    @Override
    public void registerPackets(WMMessagePipeline pipeline) {
        super.registerPackets(pipeline);
    }

    @Override
    public void registerIcons() {
    }

    @Override
    public void registerRenderers(WeaponModConfig config) {
        LongItemRenderer longrender = new LongItemRenderer();
        //StabItemRenderer stabrender = new StabItemRenderer();

        if (config.isEnabled("halberd")) {
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdWood, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdStone, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdSteel, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdDiamond, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdGold, longrender);
        }

        if (config.isEnabled("knife")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderKnife());
            /*
			MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.knifeWood.itemID, stabrender);
			MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.knifeStone.itemID, stabrender);
			MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.knifeSteel.itemID, stabrender);
			MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.knifeDiamond.itemID, stabrender);
			MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.knifeGold.itemID, stabrender);*/
        }

        if (config.isEnabled("spear")) {
            RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderSpear());

            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearWood, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearStone, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearSteel, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearDiamond, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearGold, longrender);
        }

        if (config.isEnabled("javelin")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, new RenderJavelin());
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.javelin, longrender);
        }

        if (config.isEnabled("firerod")) {
            //MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.fireRod.itemID, stabrender);
        }

        if (config.isEnabled("musket")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityMusketBullet.class, new RenderMusketBullet());
        }
        if (config.isEnabled("crossbow")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, new RenderCrossbowBolt());
            //MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.crossbow.itemID, new CrossbowItemRenderer());
        }
        if (config.isEnabled("blowgun")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityBlowgunDart.class, new RenderBlowgunDart());
        }
        if (config.isEnabled("dynamite")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new RenderDynamite());
        }
        if (config.isEnabled("flail")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityFlail.class, new RenderFlail());
        }
        if (config.isEnabled("cannon")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityCannon.class, new RenderCannon());
            RenderingRegistry.registerEntityRenderingHandler(EntityCannonBall.class, new RenderCannonBall());
        }
        if (config.isEnabled("blunderbuss")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityBlunderShot.class, new RenderBlunderShot());
        }
        if (config.isEnabled("dummy")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, new RenderDummy());
        }
        if (config.isEnabled("boomerang")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, new RenderBoomerang());
        }
    }
}
