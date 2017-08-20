package ckathode.weaponmod.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public interface ICustomProjectileMaterials {
    public int[] getAllMaterialIDs();

    public int getMaterialID(ItemStack itemstack);

    @SideOnly(Side.CLIENT)
    public float[] getColorFromMaterialID(int id);
}
