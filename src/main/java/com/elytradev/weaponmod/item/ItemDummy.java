package com.elytradev.weaponmod.item;

import com.elytradev.weaponmod.entity.EntityDummy;
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

public class ItemDummy extends WMItem {
    public ItemDummy(String id) {
        super(id);
        maxStackSize = 1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (world.isRemote) return super.onItemRightClick(world, player, hand);
        ItemStack stack = player.getHeldItem(hand);

        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d = player.prevPosX + (player.posX - player.prevPosX) * f;
        double d1 = (player.prevPosY + (player.posY - player.prevPosY) * f + 1.62D) - player.getYOffset();
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
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
                int x = traceResult.getBlockPos().getX();
                int y = traceResult.getBlockPos().getY();
                int z = traceResult.getBlockPos().getZ();
                if (world.getBlockState(traceResult.getBlockPos()).getBlock() == Blocks.SNOW) {
                    y--;
                }
                EntityDummy entitydummy = new EntityDummy(world, x + 0.5F, y + 1.0F, z + 0.5F);
                entitydummy.rotationYaw = player.rotationYaw;
                world.spawnEntity(entitydummy);
                if (!player.capabilities.isCreativeMode || !world.isRemote) {
                    stack.shrink(1);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }
}

