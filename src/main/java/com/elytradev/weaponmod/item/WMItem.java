package com.elytradev.weaponmod.item;

import com.elytradev.weaponmod.BalkonsWeaponMod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class WMItem extends Item {
    public WMItem(String id) {
        GameRegistry.registerItem(this, id, BalkonsWeaponMod.MOD_ID);
        setUnlocalizedName(id);
        setTextureName("weaponmod:" + id);
        setCreativeTab(CreativeTabs.tabCombat);
    }

}
