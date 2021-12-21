package net.htmlcsjs.htmlTech.metatileentity.multiblock;

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
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.htmlcsjs.htmlTech.HTTextures;
import net.htmlcsjs.htmlTech.blocks.BlockHTCasing;
import net.htmlcsjs.htmlTech.blocks.HTMetaBlocks;
import net.htmlcsjs.htmlTech.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.capability.LaserContainerList;
import net.htmlcsjs.htmlTech.metatileentity.HTMultiblockWithDisplayBase;
import net.htmlcsjs.htmlTech.metatileentity.multiblockpart.HTMultiblockAbility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetaTileEntityLaserCollector extends HTMultiblockWithDisplayBase {

    public boolean isActive;

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
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(20).or(autoAbilities(true, false, false, true, true, false)))
                .where('C', states(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.NAQUADAH)))
                .build();
    }

    private void initializeAbilities() {
        this.laserContainer = new LaserContainerList(getAbilities(HTMultiblockAbility.INPUT_LASER));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
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
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return HTTextures.LASER_COLLECTOR_OVERLAY;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isActive(), true);
    }
}
