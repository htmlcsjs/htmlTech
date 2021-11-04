package net.htmlcsjs.htmlTech.intergration.jei.multiblock;

import com.google.common.collect.Lists;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import net.htmlcsjs.htmlTech.api.blocks.BlockHTCasing;
import net.htmlcsjs.htmlTech.api.blocks.HTMetaBlocks;
import net.htmlcsjs.htmlTech.api.metatileentity.HTMetaTileEntities;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class LaserCollectorInfo extends MultiblockInfoPage {
    @Override
    public MultiblockControllerBase getController() {
        return HTMetaTileEntities.LASER_COLLECTOR;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        MultiblockShapeInfo shapeInfo = MultiblockShapeInfo.builder()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "LCE", "XXX")
                .aisle("XMX", "XSX", "XXX")
                .where('S', HTMetaTileEntities.LASER_COLLECTOR, EnumFacing.WEST)
                .where('X', HTMetaBlocks.HT_CASING.getState(BlockHTCasing.CasingType.FLOPPA_CASING))
                .where('C', MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.NAQUADAH))
                .where('E', MetaTileEntities.ENERGY_OUTPUT_HATCH[7], EnumFacing.SOUTH)
                .where('M', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.WEST)
                .where('L', HTMetaTileEntities.LASER_INPUT_HATCHES[7], EnumFacing.NORTH)
                .build();

        return Lists.newArrayList(shapeInfo);
    }

    @Override
    public String[] getDescription() {
        return new String[0];
    }
}
