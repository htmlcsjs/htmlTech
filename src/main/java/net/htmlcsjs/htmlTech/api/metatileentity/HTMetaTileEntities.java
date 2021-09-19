package net.htmlcsjs.htmlTech.api.metatileentity;

import gregtech.api.GregTechAPI;
import gregtech.common.metatileentities.MetaTileEntities;
import net.htmlcsjs.htmlTech.HTValues;
import net.htmlcsjs.htmlTech.api.metatileentity.multiblock.MetaTileEntityLaserProjector;
import net.htmlcsjs.htmlTech.api.metatileentity.multiblockpart.MetaTileEntityLaserHatch;
import net.htmlcsjs.htmlTech.htmlTech;
import net.minecraft.util.ResourceLocation;

public class HTMetaTileEntities {

    public static MetaTileEntityLaserHatch LASER_HATCH;
    public static MetaTileEntityLaserProjector LASER_PROJECTOR;

    /**
     * Inits MTE's for htmlTech
     * avaible ids: 9000-9499
     */
    public static void init() {
        htmlTech.logger.info("registering htmlTech Tile Entities");

        LASER_HATCH = MetaTileEntities.registerMetaTileEntity(9000, new MetaTileEntityLaserHatch(location("laser_hatch.zpm"), 7));
        LASER_PROJECTOR = MetaTileEntities.registerMetaTileEntity(9001, new MetaTileEntityLaserProjector(location("laser_projector")));
    }

    public static ResourceLocation location(String name) {
        return new ResourceLocation(HTValues.MODID, name);
    }
}
