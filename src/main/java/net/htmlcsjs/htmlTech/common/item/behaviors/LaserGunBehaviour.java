package net.htmlcsjs.htmlTech.common.item.behaviors;

import codechicken.lib.vec.Vector3;
import gregtech.api.GTValues;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.client.particle.GTLaserBeamParticle;
import gregtech.client.particle.GTParticleManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaserGunBehaviour implements IItemBehaviour {

    @Override
    public ActionResult<ItemStack> onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        GTLaserBeamParticle particle = new GTLaserBeamParticle(world, Vector3.fromEntityCenter(player), Vector3.fromBlockPos(player.rayTrace(10, 5).getBlockPos()))
                .setBody(new ResourceLocation(GTValues.MODID,"textures/fx/laser/laser.png")); // create a beam particle and set its texture.
        GTParticleManager.INSTANCE.addEffect(particle); // add it to the particle manager.

        ItemStack itemStack = player.getHeldItem(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }
}
