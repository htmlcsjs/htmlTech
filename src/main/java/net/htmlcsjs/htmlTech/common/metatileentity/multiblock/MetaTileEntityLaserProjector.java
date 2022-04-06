package net.htmlcsjs.htmlTech.common.metatileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.api.capability.LaserContainerList;
import net.htmlcsjs.htmlTech.api.metatileentity.multiblock.HTMultiblockWithDisplayBase;
import net.htmlcsjs.htmlTech.client.HTTextures;
import net.htmlcsjs.htmlTech.common.blocks.BlockHTCasing;
import net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks;
import net.htmlcsjs.htmlTech.common.metatileentity.multiblock.multiblockpart.HTMultiblockAbility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityLaserProjector extends HTMultiblockWithDisplayBase {

    public boolean isActive;

    protected IEnergyContainer energyContainer;
    protected ILaserContainer laserContainer;

    public MetaTileEntityLaserProjector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        isActive = true;
    }

    public IEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    public ILaserContainer getLaserContainer() {
        return laserContainer;
    }

    public List<ILaserContainer> getLaserAbiltities() {
        return this.getAbilities(HTMultiblockAbility.OUTPUT_LASER);
    }

    @Override
    protected void updateFormedValid() {
        long voltage = getLaserAbiltities().isEmpty() ? 0 : getLaserAbiltities().get(0).getDiodeVoltage();
        long amperage = getLaserAbiltities().isEmpty() ? 0 : getLaserAbiltities().get(0).getDiodeAmperage();
        long energyAdded = new EnergyContainerList(this.getAbilities(MultiblockAbility.INPUT_ENERGY)).removeEnergy(voltage * amperage) * -1L;
        getLaserAbiltities().get(0).addEnergy(energyAdded);
        //HtmlTech.logger.info(energyAdded + " EU was taken from enet and put in lnet");
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "XCX", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(14).or(autoAbilities(true, false, true, false, false, true)))
                .where('C', states(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.NAQUADAH)))
                .build();
    }


    private void initializeAbilities() {
        this.laserContainer = new LaserContainerList(getAbilities(HTMultiblockAbility.OUTPUT_LASER));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    private void resetTileAbilities() {
        this.laserContainer = new LaserContainerList(Lists.newArrayList());
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return HTTextures.NAQ_ALLOY_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLaserProjector(this.metaTileEntityId);
    }

    protected IBlockState getCasingState() {
        return HTMetaBlocks.HT_CASING.getState(BlockHTCasing.CasingType.NAQ_ALLOY_CASING);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return HTTextures.LASER_PROJECTOR_OVERLAY;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isActive(), true);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        long voltage = getLaserAbiltities().isEmpty() ? 0 : getLaserAbiltities().get(0).getDiodeVoltage();
        long amperage = getLaserAbiltities().isEmpty() ? 0 : getLaserAbiltities().get(0).getDiodeAmperage();
        textList.add(new TextComponentString(I18n.format("htmltech.laser.voltage", voltage, GTValues.VNF[(GTUtility.getTierByVoltage(voltage))])));
        textList.add(new TextComponentString(I18n.format("htmltech.laser.amperage", amperage)));
    }
}
