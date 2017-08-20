package com.elytradev.weaponmod.network;

import com.elytradev.concrete.network.Message;
import com.elytradev.concrete.network.NetworkContext;
import com.elytradev.concrete.network.annotation.field.MarshalledAs;
import com.elytradev.concrete.network.annotation.type.ReceivedOn;
import com.elytradev.weaponmod.entity.EntityCannon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

@ReceivedOn(Side.SERVER)
public class MessageFireCannon extends Message {

    @MarshalledAs("int")
    public int entityID;

    public MessageFireCannon(int entityID) {
        super(WeaponNetworking.NETWORK);
        this.entityID = entityID;
    }

    public MessageFireCannon(NetworkContext ctx) {
        super(ctx);
    }

    @Override
    protected void handle(EntityPlayer player) {
        Entity entity = player.world.getEntityByID(entityID);
        if (entity instanceof EntityCannon) {
            ((EntityCannon) entity).fireCannon();
        }
    }
}
