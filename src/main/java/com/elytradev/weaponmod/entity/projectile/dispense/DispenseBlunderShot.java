package com.elytradev.weaponmod.entity.projectile.dispense;

import com.elytradev.weaponmod.entity.projectile.EntityBlunderShot;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;

import java.util.Random;

public class DispenseBlunderShot extends BehaviorDefaultDispenseItem {
    private Random rand;

    public DispenseBlunderShot() {
        rand = new Random();
    }

    @Override
    public ItemStack dispenseStack(IBlockSource blocksource, ItemStack itemstack) {
        EnumFacing face = EnumFacing.getFront(blocksource.getBlockState().getBlock().getMetaFromState(blocksource.getBlockState()));

        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        EntityBlunderShot.fireFromDispenser(blocksource.getWorld(), pos.getX() + face.getFrontOffsetX(), pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), face.getFrontOffsetX(), face.getFrontOffsetY(), face.getFrontOffsetZ());
        itemstack.splitStack(1);
        return itemstack;
    }

    @Override
    protected void playDispenseSound(IBlockSource blocksource) {
        //TODO: idk how sounds work
        //blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.explode", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.6F));
    }

    @Override
    protected void spawnDispenseParticles(IBlockSource blocksource, EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        blocksource.getWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + face.getFrontOffsetX(), pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
    }
}
