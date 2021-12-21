package net.htmlcsjs.htmlTech.metatileentity;

import gregtech.api.GTValues;
import gregtech.common.metatileentities.MetaTileEntities;
import net.htmlcsjs.htmlTech.HTValues;
import net.htmlcsjs.htmlTech.htmlTech;
import net.htmlcsjs.htmlTech.metatileentity.multiblock.MetaTileEntityLaserCollector;
import net.htmlcsjs.htmlTech.metatileentity.multiblock.MetaTileEntityLaserProjector;
import net.htmlcsjs.htmlTech.metatileentity.multiblockpart.MetaTileEntityLaserHatch;
import net.minecraft.util.ResourceLocation;

public class HTMetaTileEntities {

    public static MetaTileEntityLaserProjector LASER_PROJECTOR;
    public static MetaTileEntityLaserHatch LASER_OUTPUT_HATCH;
    public static final MetaTileEntityLaserHatch[] LASER_INPUT_HATCHES = new MetaTileEntityLaserHatch[GTValues.V.length];
    public static MetaTileEntityLaserCollector LASER_COLLECTOR;


    /**
     * Inits MTEs for htmlTech
     * avaible ids: 9000-9499
     */
    public static void init() {
        htmlTech.logger.info("registering htmlTech Tile Entities");

        LASER_PROJECTOR = MetaTileEntities.registerMetaTileEntity(9000, new MetaTileEntityLaserProjector(location("laser_projector")));
        LASER_OUTPUT_HATCH = MetaTileEntities.registerMetaTileEntity(9001, new MetaTileEntityLaserHatch(location("laser_output_hatch"), 7, true));
        for (int i = 0; i < 15; i++) {
            LASER_INPUT_HATCHES[i] = new MetaTileEntityLaserHatch(location("laser_input_hatch." + GTValues.VN[i].toLowerCase()), i, false);
            MetaTileEntities.registerMetaTileEntity(9002 + i, LASER_INPUT_HATCHES[i]);
        }
        LASER_COLLECTOR = MetaTileEntities.registerMetaTileEntity(9017, new MetaTileEntityLaserCollector(location("laser_collector")));
    }

    public static ResourceLocation location(String name) {
        return new ResourceLocation(HTValues.MODID, name);
    }
}
