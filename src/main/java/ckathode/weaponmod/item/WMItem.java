package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import cpw.mods.fml.common.registry.GameRegistry;
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
