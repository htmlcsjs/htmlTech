package net.htmlcsjs.htmlTech.common.metatileentity;

import gregtech.api.GTValues;
import gregtech.common.metatileentities.MetaTileEntities;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.htmlcsjs.htmlTech.api.HTValues;
import net.htmlcsjs.htmlTech.common.HTConfig;
import net.htmlcsjs.htmlTech.common.metatileentity.multiblock.MetaTileEntityLaserCollector;
import net.htmlcsjs.htmlTech.common.metatileentity.multiblock.MetaTileEntityLaserProjector;
import net.htmlcsjs.htmlTech.common.metatileentity.multiblock.multiblockpart.MetaTileEntityLaserHatch;
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
        HtmlTech.logger.info("registering htmlTech Tile Entities");

        LASER_PROJECTOR = MetaTileEntities.registerMetaTileEntity(9000, new MetaTileEntityLaserProjector(location("laser_projector")));
        LASER_OUTPUT_HATCH = MetaTileEntities.registerMetaTileEntity(9001, new MetaTileEntityLaserHatch(location("laser_output_hatch"), 7, true));

        int endPos = GTValues.HT ? LASER_INPUT_HATCHES.length - 1 : Math.min(LASER_INPUT_HATCHES.length - 1, GTValues.UHV + 1);
        for (int i = HTConfig.lasers.minLaserTier; i < endPos; i++) {
            LASER_INPUT_HATCHES[i] = new MetaTileEntityLaserHatch(location("laser_input_hatch." + GTValues.VN[i].toLowerCase()), i, false);
            MetaTileEntities.registerMetaTileEntity(9002 + i, LASER_INPUT_HATCHES[i]);
        }
        LASER_COLLECTOR = MetaTileEntities.registerMetaTileEntity(9017, new MetaTileEntityLaserCollector(location("laser_collector")));
    }

    public static ResourceLocation location(String name) {
        return new ResourceLocation(HTValues.MODID, name);
    }
}
