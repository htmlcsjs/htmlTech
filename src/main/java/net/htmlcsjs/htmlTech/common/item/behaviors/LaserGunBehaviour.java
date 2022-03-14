package net.htmlcsjs.htmlTech.common.item.behaviors;

import codechicken.lib.vec.Vector3;
import com.google.common.base.Predicates;
import gregtech.api.GTValues;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.client.particle.GTLaserBeamParticle;
import gregtech.client.particle.GTParticleManager;
import net.htmlcsjs.htmlTech.api.damagesources.HTDamageSources;
import net.htmlcsjs.htmlTech.common.HTConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class LaserGunBehaviour implements IItemBehaviour {

    private GTLaserBeamParticle particle;
    private float alpha; // TODO move to getter after ceu pr #800

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        RayTraceResult rayTracedLooking = rayTrace(player, 100, 1.0F, false, true);

        if (particle != null) {
            GTParticleManager.INSTANCE.clearAllEffects(true);
        }
        alpha = 1.0F;
        particle = new GTLaserBeamParticle(world, Vector3.fromEntity(player).add(0, player.getEyeHeight(), 0), Vector3.fromEntity(player).subtract(new Vector3(rayTracedLooking.hitVec)))
                .setBody(new ResourceLocation(GTValues.MODID, "textures/fx/laser/laser.png")); // create a beam particle and set its texture.
        GTParticleManager.INSTANCE.addEffect(particle); // add it to the particle manager.
        ItemStack itemStack = player.getHeldItem(hand);

        if (rayTracedLooking.typeOfHit == RayTraceResult.Type.ENTITY && rayTracedLooking.entityHit != null) {
            rayTracedLooking.entityHit.attackEntityFrom(HTDamageSources.getLaserDamage(), HTConfig.lasers.laserGunDamage);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    /**
     * raytraces player, taken from https://github.com/Lach01298/QMD with permission
     */
    private RayTraceResult rayTrace(EntityLivingBase entity, double reachDistance, float partialTicks, boolean hitBlocks, boolean hitEntities) {
        Vec3d eyesPos = entity.getPositionEyes(partialTicks);
        Vec3d lookVec = entity.getLook(1.0F);
        Vec3d rayVec = eyesPos.add(lookVec.scale(reachDistance));
        Entity pointedEntity = null;
        Vec3d hitVec = null;
        RayTraceResult entityHit = null;
        RayTraceResult blockHit = null;
        RayTraceResult missHit = new RayTraceResult(RayTraceResult.Type.MISS, eyesPos.subtract(rayVec), EnumFacing.UP, new BlockPos(MathHelper.floor(eyesPos.subtract(rayVec).x), MathHelper.floor(eyesPos.subtract(rayVec).y), MathHelper.floor(eyesPos.subtract(rayVec).z)));

        if (hitEntities) {
            List<Entity> list = entity.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance).grow(1.0D, 1.0D, 1.0D),
                    Predicates.and(EntitySelectors.NOT_SPECTATING, entity12 -> entity12 != null && entity12.canBeCollidedWith()));

            double d2 = reachDistance;

            for (Entity entity1 : list) {
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double) entity1.getCollisionBorderSize());
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(eyesPos, rayVec);

                if (axisalignedbb.contains(eyesPos)) {
                    pointedEntity = entity1;
                    hitVec = raytraceresult == null ? eyesPos : raytraceresult.hitVec;
                } else if (raytraceresult != null) {
                    double d3 = eyesPos.distanceTo(raytraceresult.hitVec);

                    if (d3 < d2 || d2 == 0.0D) {
                        if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract()) {
                            if (d2 == 0.0D) {
                                pointedEntity = entity1;
                                hitVec = raytraceresult.hitVec;
                            }
                        } else {
                            pointedEntity = entity1;
                            hitVec = raytraceresult.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }
            if (pointedEntity != null) {
                entityHit = new RayTraceResult(pointedEntity, hitVec);
            }
        }

        if (hitBlocks) {
            blockHit = entity.world.rayTraceBlocks(eyesPos, rayVec, false, false, true);
        }

        double blockDistance = 0;
        double entityDistance = 0;

        if (entityHit != null) {
            entityDistance = entityHit.hitVec.distanceTo(entity.getPositionVector());
        }

        if (blockHit != null) {
            blockDistance = blockHit.hitVec.distanceTo(entity.getPositionVector());
        }

        if (hitEntities && hitBlocks) {
            if (entityDistance < blockDistance && entityDistance > 0) {
                return entityHit;
            } else {
                return blockHit;
            }
        } else if (hitEntities) {
            if (entityHit == null) {
                return missHit;
            }
            return entityHit;
        } else {
            if (blockHit == null) {
                return missHit;
            }
            return blockHit;
        }
    }

    @Override
    public void onUpdate(ItemStack itemStack, Entity entity) {
        if (alpha > 0) {
            alpha -= 0.05F;
            if (particle != null) {
                particle.setAlpha(alpha);
            }
        }
    }
}
