package ckathode.weaponmod;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.item.ExtendedReachHelper;
import ckathode.weaponmod.item.IExtendedReachItem;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.network.MsgCannonFire;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WMClientEventHandler {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!e.player.worldObj.isRemote) {
            return;
        }
        if (e.phase == TickEvent.Phase.END) {
            if (e.player != null && e.player.swingProgressInt == 1) // Just swung
            {
                ItemStack itemstack = e.player.getCurrentEquippedItem();
                IExtendedReachItem ieri;
                if (itemstack != null) {
                    if (itemstack.getItem() instanceof IExtendedReachItem) {
                        ieri = (IExtendedReachItem) itemstack.getItem();
                    } else if (itemstack.getItem() instanceof IItemWeapon && ((IItemWeapon) itemstack.getItem()).getMeleeComponent() instanceof IExtendedReachItem) {
                        ieri = (IExtendedReachItem) ((IItemWeapon) itemstack.getItem()).getMeleeComponent();
                    } else {
                        ieri = null;
                    }

                    if (ieri != null) {
                        float reach = ieri.getExtendedReach(e.player.worldObj, e.player, itemstack);
                        MovingObjectPosition mov = ExtendedReachHelper.getMouseOver(0, reach);

                        if (mov != null && mov.entityHit != null && mov.entityHit != e.player && mov.entityHit.hurtResistantTime == 0) {
                            FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, mov.entityHit);
                        }
                    }
                }
            }
        } else if (e.phase == TickEvent.Phase.START && e.player instanceof EntityPlayerSP) {
            EntityPlayerSP entity = (EntityPlayerSP) e.player;
            if (entity.movementInput.jump && entity.getRidingEntity() instanceof EntityCannon && ((EntityCannon) entity.getRidingEntity()).isLoaded()) {
                MsgCannonFire msg = new MsgCannonFire((EntityCannon) entity.getRidingEntity());
                BalkonsWeaponMod.instance.messagePipeline.sendToServer(msg);
            }
        }
    }

    @SubscribeEvent
    public void onFOVUpdateEvent(FOVUpdateEvent e) {
        //TODO: The variables I'm using might be wrong but it's a start.
        if (e.getEntity().isSwingInProgress && e.getEntity().getActiveItemStack().getItem() instanceof IItemWeapon) {
            RangedComponent rc = ((IItemWeapon) e.getEntity().getActiveItemStack().getItem()).getRangedComponent();
            if (rc != null && RangedComponent.isReadyToFire(e.getEntity().getActiveItemStack())) {
                e.setNewfov(e.getFov() * rc.getFOVMultiplier(e.getEntity().swingProgressInt));
            }
        }
    }
}
