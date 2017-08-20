package com.elytradev.weaponmod.network;

import com.elytradev.concrete.network.Message;
import com.elytradev.concrete.network.NetworkContext;
import com.elytradev.concrete.network.annotation.field.MarshalledAs;
import com.elytradev.concrete.network.annotation.type.ReceivedOn;
import com.elytradev.weaponmod.AdvancedExplosion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

@ReceivedOn(Side.CLIENT)
public class MessageExplosion extends Message {

    public List<BlockPos> affectedBlocks;
    public boolean smallParticles, bigParticles;

    @MarshalledAs("double")
    private double x, y, z;
    @MarshalledAs("float")
    private float size;

    public MessageExplosion(List<BlockPos> affectedBlocks, boolean smallParticles, boolean bigParticles, double x, double y, double z, float size) {
        super(WeaponNetworking.NETWORK);
        this.affectedBlocks = affectedBlocks;
        this.smallParticles = smallParticles;
        this.bigParticles = bigParticles;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
    }

    public MessageExplosion(NetworkContext ctx) {
        super(ctx);
    }

    @Override
    protected void handle(EntityPlayer player) {
        World world = FMLClientHandler.instance().getWorldClient();
        AdvancedExplosion expl = new AdvancedExplosion(world, null, x, y, z, size, affectedBlocks);
        expl.doParticleExplosion(smallParticles, bigParticles);
    }
}
