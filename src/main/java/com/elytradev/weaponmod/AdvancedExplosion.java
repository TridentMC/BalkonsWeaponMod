package com.elytradev.weaponmod;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AdvancedExplosion extends Explosion {
    protected static final Random rand = new Random();
    public World worldObj;
    protected boolean blocksCalculated;
    private float explosionSize;

    public AdvancedExplosion(World world, Entity exploder, double d, double d1, double d2, float f, List<BlockPos> affectedBlocks) {
        super(world, exploder, d, d1, d2, f, false, true);
        this.worldObj = world;
        this.explosionSize = f;
        this.setAffectedBlockPositions(affectedBlocks);
    }

    public void setAffectedBlockPositions(List<BlockPos> list) {
        setAffectedBlockPositions(list);
        blocksCalculated = true;
    }

    public void doEntityExplosion() {
        doEntityExplosion(DamageSource.causeExplosionDamage(this));
    }

    public void doEntityExplosion(DamageSource damagesource) {
        float size = explosionSize * 2F;
        int i0 = MathHelper.floor(getPosition().x - size - 1.0D);
        int i1 = MathHelper.floor(getPosition().x + size + 1.0D);
        int j0 = MathHelper.floor(getPosition().y - size - 1.0D);
        int j1 = MathHelper.floor(getPosition().y + size + 1.0D);
        int k0 = MathHelper.floor(getPosition().z - size - 1.0D);
        int k1 = MathHelper.floor(getPosition().z + size + 1.0D);
        @SuppressWarnings("unchecked")
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(getExplosivePlacedBy(), new AxisAlignedBB(i0, j0, k0, i1, j1, k1));
        Vec3d vec31 = new Vec3d(getPosition().x, getPosition().y, getPosition().z);

        double dx;
        double dy;
        double dz;

        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            double dr = entity.getDistance(getPosition().x, getPosition().y, getPosition().z) / size;

            if (dr <= 1.0D) {
                dx = entity.posX - getPosition().x;
                dy = entity.posY - getPosition().y;
                dz = entity.posZ - getPosition().z;
                double d = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);

                if (d != 0D) {
                    dx /= d;
                    dy /= d;
                    dz /= d;
                    double dens = worldObj.getBlockDensity(vec31, entity.getEntityBoundingBox());
                    double var36 = (1.0D - dr) * dens;
                    int damage = (int) ((var36 * var36 + var36) / 2.0D * 8.0D * size + 1D);
                    entity.attackEntityFrom(damagesource, damage);
                    entity.motionX += dx * var36;
                    entity.motionY += dy * var36;
                    entity.motionZ += dz * var36;
                }
            }
        }
    }

    public void doBlockExplosion() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (BlockPos pos : getAffectedBlockPositions()) {
            IBlockState state = worldObj.getBlockState(pos);
            if (state.getBlock() != null) {
                Block block = state.getBlock();
                if (block.canDropFromExplosion(this)) {
                    block.dropBlockAsItemWithChance(worldObj, pos, state, 1F / explosionSize, 0);
                }

                worldObj.setBlockToAir(pos);
                block.onBlockDestroyedByExplosion(worldObj, pos, this);
            }
        }

    }

    public void doParticleExplosion(boolean smallparticles, boolean bigparticles) {
        if (getExplosivePlacedBy() instanceof EntityPlayer)
            worldObj.playSound((EntityPlayer) getExplosivePlacedBy(), new BlockPos(getPosition()), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        if (bigparticles) {
            worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, getPosition().x, getPosition().y, getPosition().z, 0.0D, 0.0D, 0.0D);
        }
        if (!smallparticles) return;

        if (!blocksCalculated) {
            calculateBlockExplosion();
        }

        for (BlockPos pos : getAffectedBlockPositions()) {
            double px = pos.getX() + worldObj.rand.nextFloat();
            double py = pos.getY() + worldObj.rand.nextFloat();
            double pz = pos.getZ() + worldObj.rand.nextFloat();
            double dx = px - getPosition().x;
            double dy = py - getPosition().y;
            double dz = pz - getPosition().z;
            double distance = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= distance;
            dy /= distance;
            dz /= distance;
            double d7 = 0.5D / (distance / explosionSize + 0.1D);
            d7 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
            dx *= d7;
            dy *= d7;
            dz *= d7;
            worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (px + getPosition().x * 1.0D) / 2D, (py + getPosition().y * 1.0D) / 2D, (pz + getPosition().z * 1.0D) / 2D, dx, dy, dz);
            worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, dx, dy, dz);
        }
    }

    protected void calculateBlockExplosion() {
        byte maxsize = 16;
        Set<BlockPos> set = new HashSet<BlockPos>();
        int i;
        int j;
        int k;
        double dx;
        double dy;
        double dz;

        for (i = 0; i < maxsize; i++) {
            for (j = 0; j < maxsize; j++) {
                for (k = 0; k < maxsize; k++) {
                    if (i == 0 || i == maxsize - 1 || j == 0 || j == maxsize - 1 || k == 0 || k == maxsize - 1) {
                        double rx = (i / (maxsize - 1.0F) * 2.0F - 1.0F);
                        double ry = (j / (maxsize - 1.0F) * 2.0F - 1.0F);
                        double rz = (k / (maxsize - 1.0F) * 2.0F - 1.0F);
                        double rd = Math.sqrt(rx * rx + ry * ry + rz * rz);
                        rx /= rd;
                        ry /= rd;
                        rz /= rd;
                        float strength = explosionSize * (0.7F + worldObj.rand.nextFloat() * 0.6F);
                        dx = getPosition().x;
                        dy = getPosition().y;
                        dz = getPosition().z;

                        for (float f = 0.3F; strength > 0.0F; strength -= f * 0.75F) {
                            BlockPos pos = new BlockPos(MathHelper.floor(dx), MathHelper.floor(dy), MathHelper.floor(dz));
                            IBlockState state = worldObj.getBlockState(pos);
                            if (state.getBlock() != null) {
                                strength -= (state.getBlock().getExplosionResistance(worldObj, pos, getExplosivePlacedBy(), this) + 0.3F) * f;
                            }

                            if (strength > 0.0F) {
                                set.add(new BlockPos(pos));
                            }

                            dx += rx * f;
                            dy += ry * f;
                            dz += rz * f;
                        }
                    }
                }
            }
        }

        getAffectedBlockPositions().addAll(set);
        blocksCalculated = true;
    }
}
