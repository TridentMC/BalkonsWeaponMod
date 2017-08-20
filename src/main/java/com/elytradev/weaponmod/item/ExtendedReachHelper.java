package com.elytradev.weaponmod.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class ExtendedReachHelper {
    private static Minecraft mc = FMLClientHandler.instance().getClient();

    /**
     * This method will return the entitly or tile the mouse is hovering over up to the distance provided. It is more or less a copy/paste of the default
     * minecraft version.
     *
     * @return
     */
    public static RayTraceResult getMouseOver(float frame, float dist) {
        RayTraceResult mop = null;
        if (mc.getRenderViewEntity() != null) {
            if (mc.world != null) {
                double var2 = dist;
                mop = mc.getRenderViewEntity().rayTrace(var2, frame);
                double calcdist = var2;
                Vec3d pos = mc.getRenderViewEntity().getPositionEyes(frame);
                var2 = calcdist;
                if (mop != null) {
                    calcdist = mop.hitVec.distanceTo(pos);
                }

                Vec3d lookvec = mc.getRenderViewEntity().getLook(frame);
                Vec3d var8 = pos.addVector(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
                Entity pointedEntity = null;
                float var9 = 1.0F;
                @SuppressWarnings("unchecked")
                List<Entity> list = mc.world
                        .getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity()
                                .getEntityBoundingBox().offset(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2)
                                .expand(var9, var9, var9));
                double d = calcdist;

                for (Entity entity : list) {
                    if (entity.canBeCollidedWith()) {
                        float bordersize = entity.getCollisionBorderSize();
                        AxisAlignedBB aabb = entity.getEntityBoundingBox().expand(bordersize, bordersize, bordersize);
                        RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);

                        if (aabb.contains(pos)) {
                            if (0.0D < d || d == 0.0D) {
                                pointedEntity = entity;
                                d = 0.0D;
                            }
                        } else if (mop0 != null) {
                            double d1 = pos.distanceTo(mop0.hitVec);

                            if (d1 < d || d == 0.0D) {
                                pointedEntity = entity;
                                d = d1;
                            }
                        }
                    }
                }

                if (pointedEntity != null && (d < calcdist || mop == null)) {
                    mop = new RayTraceResult(pointedEntity);
                }
            }
        }
        return mop;
    }

}
