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
import javax.annotation.Nullable;
import java.util.List;

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

    public List<ILaserContainer> getLaserAbiltities() {
        return this.getAbilities(HTMultiblockAbility.INPUT_LASER);
    }


    @Override
    protected void updateFormedValid() {
        long voltage = getLaserAbiltities().isEmpty() ? 0 : new LaserContainerList(getLaserAbiltities()).getInputVoltage();
        long amperage = getLaserAbiltities().isEmpty() ? 0 : new LaserContainerList(getLaserAbiltities()).getInputAmperage();
        long energyAdded = new LaserContainerList(getLaserAbiltities()).removeEnergy(voltage * amperage) * -1L;
        new EnergyContainerList(this.getAbilities(MultiblockAbility.OUTPUT_ENERGY)).addEnergy(energyAdded);
        //HtmlTech.logger.info(energyAdded + " EU was taken from lnet and put in enet");
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
        return HTMetaBlocks.HT_CASING.getState(BlockHTCasing.CasingType.PBI_CASING);
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

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        long voltage = getLaserAbiltities().isEmpty() ? 0 : new LaserContainerList(getLaserAbiltities()).getInputVoltage();
        long amperage = getLaserAbiltities().isEmpty() ? 0 : new LaserContainerList(getLaserAbiltities()).getInputAmperage();
        textList.add(new TextComponentString(I18n.format("htmltech.laser.voltage", voltage, GTValues.VNF[(GTUtility.getTierByVoltage(voltage))])));
        textList.add(new TextComponentString(I18n.format("htmltech.laser.amperage", amperage)));
    }
}
