package net.htmlcsjs.htmlTech.api.metatileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.htmlcsjs.htmlTech.api.HTTextures;
import net.htmlcsjs.htmlTech.api.blocks.BlockHTCasing;
import net.htmlcsjs.htmlTech.api.blocks.HTMetaBlocks;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.api.capability.LaserContainerList;
import net.htmlcsjs.htmlTech.api.metatileentity.multiblockpart.HTMultiblockAbility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetaTileEntityLaserCollector extends MultiblockWithDisplayBase {

    public boolean isActive;
    public static final MultiblockAbility<?>[] ALLOWED_ABILITIES = new MultiblockAbility[]{
            MultiblockAbility.OUTPUT_ENERGY, HTMultiblockAbility.INPUT_LASER,
            MultiblockAbility.MAINTENANCE_HATCH
    };

    protected IEnergyContainer energyContainer;
    protected ILaserContainer laserContainer;

    public MetaTileEntityLaserCollector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        isActive = true;
        resetTileAbilities();
    }

    public IEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    public ILaserContainer getLaserContainer() {
        return laserContainer;
    }

    @Override
    protected void updateFormedValid() {
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "XCX", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .setAmountLimit('C', 1, 1)
                .setAmountAtLeast('x', 8)
                .where('S', selfPredicate())
                .where('x', statePredicate(getCasingState()))
                .where('X', statePredicate(getCasingState()).or(abilityPartPredicate(ALLOWED_ABILITIES)))
                .where('C', statePredicate(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.NAQUADAH)))
                .where(' ', isAirPredicate())
                .build();
    }

    private void initializeAbilities() {
        this.laserContainer = new LaserContainerList(getAbilities(new MultiblockAbility<ILaserContainer>()));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    private void resetTileAbilities() {
        this.laserContainer = new LaserContainerList(Lists.newArrayList());
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return HTTextures.FLOPPA_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityLaserCollector(this.metaTileEntityId);
    }

    @Nullable
    protected IBlockState getCasingState() {
        return HTMetaBlocks.HT_CASING.getState(BlockHTCasing.CasingType.FLOPPA_CASING);
        //return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return HTTextures.LASER_COLLECTOR_OVERLAY;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().render(renderState, translation, pipeline, this.getFrontFacing(), this.isStructureFormed());
    }
}
