package net.htmlcsjs.htmlTech.intergration.jei.multiblock;

import com.google.common.collect.Lists;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import net.htmlcsjs.htmlTech.api.metatileentity.HTMetaTileEntities;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class LaserTransformerInfo extends MultiblockInfoPage {
    @Override
    public MultiblockControllerBase getController() {
        return HTMetaTileEntities.LASER_TRANSFORMER;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        MultiblockShapeInfo shapeInfo = MultiblockShapeInfo.builder()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "ECL", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', HTMetaTileEntities.LASER_TRANSFORMER, EnumFacing.WEST)
                .where('X', MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING))
                .where('C', MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.DIAMERICIUM_TITANIUM))
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[7], EnumFacing.WEST)
                .where('L', HTMetaTileEntities.LASER_HATCH, EnumFacing.WEST)
                .build();

        return Lists.newArrayList(shapeInfo);
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("htmltech.multiblock.laser_transformer.description")};
    }
}
