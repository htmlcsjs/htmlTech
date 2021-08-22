package net.htmlcsjs.htmlTech.api.metatileentity.multiblock;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.htmlcsjs.htmlTech.api.metatileentity.multiblockpart.HTMultiblockAbility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityLaserTransformer extends MultiblockWithDisplayBase {
    public static final MultiblockAbility<?>[] ALLOWED_ABILITIES = new MultiblockAbility[]{
            MultiblockAbility.INPUT_ENERGY, HTMultiblockAbility.OUTPUT_LASER
    };

    public MetaTileEntityLaserTransformer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        //TODO: laser walker here pls.
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("xxx", "xXx", "xxx")
                .aisle("xXx", "XCX", "xXx")
                .aisle("xxx", "xSx", "xxx")
                .setAmountLimit('C', 1, 1)
                .setAmountAtLeast('x', 10)
                .where('S', selfPredicate())
                .where('x', statePredicate(getCasingState()))
                .where('X', statePredicate(getCasingState()).or(abilityPartPredicate(ALLOWED_ABILITIES)))
                .where('C', statePredicate(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.DIAMERICIUM_TITANIUM)))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.INERT_PTFE_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityLaserTransformer(this.metaTileEntityId);
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING);
    }
}
