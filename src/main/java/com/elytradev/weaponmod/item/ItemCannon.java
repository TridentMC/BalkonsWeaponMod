package com.elytradev.weaponmod.item;

import com.elytradev.weaponmod.entity.EntityCannon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemCannon extends WMItem {
    public ItemCannon(String id) {
        super(id);
        maxStackSize = 1;
    }

    @Override
    public int getItemEnchantability() {
        return 10;
    }

    @Override
    public ActionResult onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand) {
        if (world.isRemote) return super.onItemRightClick(world, entityplayer, hand);
        ItemStack stack = entityplayer.getHeldItem(hand);
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * f;
        double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * f + 1.6200000000000001D) - entityplayer.getYOffset();
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * f;
        Vec3d vec3d = new Vec3d(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3d vec3d1 = vec3d.addVector(f7 * d3, f8 * d3, f9 * d3);
        RayTraceResult traceResult = world.rayTraceBlocks(vec3d, vec3d1, true);
        if (traceResult != null) {
            if (traceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                int i = traceResult.getBlockPos().getX();
                int j = traceResult.getBlockPos().getY();
                int k = traceResult.getBlockPos().getZ();
                if (!world.isRemote) {
                    if (world.getBlockState(traceResult.getBlockPos()).getBlock() == Blocks.SNOW) {
                        j--;
                    }
                    world.spawnEntity(new EntityCannon(world, i + 0.5F, j + 1.0F, k + 0.5F));
                }
                if (!entityplayer.capabilities.isCreativeMode || !world.isRemote) {
                    stack.shrink(1);
                }
            }
        }
        return new ActionResult(EnumActionResult.PASS, stack);
    }
}
