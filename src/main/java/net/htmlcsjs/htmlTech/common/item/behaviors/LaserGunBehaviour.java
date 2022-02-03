package net.htmlcsjs.htmlTech.common.item.behaviors;

import codechicken.lib.vec.Vector3;
import gregtech.api.GTValues;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.client.particle.GTLaserBeamParticle;
import gregtech.client.particle.GTParticleManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class LaserGunBehaviour implements IItemBehaviour {
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        GTLaserBeamParticle particle = new GTLaserBeamParticle(world, Vector3.fromEntity(player), new Vector3(player.getLookVec()).add(0,-0.5,0).multiply(0,-1,0).multiply(100.1))
                .setBody(new ResourceLocation(GTValues.MODID,"textures/fx/laser/laser.png")); // create a beam particle and set its texture.
        GTParticleManager.INSTANCE.addEffect(particle); // add it to the particle manager.
        ItemStack itemStack = player.getHeldItem(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }
}
