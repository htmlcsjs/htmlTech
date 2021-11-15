package net.htmlcsjs.htmlTech.api.metatileentity.multiblock;

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
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.htmlcsjs.htmlTech.api.HTTextures;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.api.capability.LaserContainerList;
import net.htmlcsjs.htmlTech.api.metatileentity.multiblockpart.HTMultiblockAbility;
import net.htmlcsjs.htmlTech.htmlTech;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityLaserProjector extends MultiblockWithDisplayBase {

    public boolean isActive;
    public static final MultiblockAbility<?>[] ALLOWED_ABILITIES = new MultiblockAbility[]{
            MultiblockAbility.INPUT_ENERGY, HTMultiblockAbility.OUTPUT_LASER,
            MultiblockAbility.MAINTENANCE_HATCH
    };

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

    @Override
    protected void updateFormedValid() {
        htmlTech.logger.info(getAbilities(HTMultiblockAbility.INPUT_LASER).size());
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("xXx", "XXX", "xXx")
                .aisle("XXX", "XCX", "XXX")
                .aisle("xXx", "XSX", "xXx")
                .setAmountLimit('C', 1, 1)
                .setAmountAtLeast('x', 10)
                .where('S', selfPredicate())
                .where('x', statePredicate(getCasingState()))
                .where('X', statePredicate(getCasingState()).or(abilityPartPredicate(ALLOWED_ABILITIES)))
                .where('C', statePredicate(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.NAQUADAH)))
                .build();
    }


    private void initializeAbilities() {
        this.laserContainer = new LaserContainerList(getAbilities(HTMultiblockAbility.INPUT_LASER));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    private void resetTileAbilities() {
        this.laserContainer = new LaserContainerList(Lists.newArrayList());
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.INERT_PTFE_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityLaserProjector(this.metaTileEntityId);
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return HTTextures.LASER_PROJECTOR_OVERLAY;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().render(renderState, translation, pipeline, this.getFrontFacing(), this.isStructureFormed());
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        long voltage = laserContainer.getInputVoltage();//laserContainer.getDiodeVoltage();
        textList.add(new TextComponentString(I18n.format("htmltech.laser.voltage", voltage, GTValues.VN[(GTUtility.getTierByVoltage(voltage))])));
        textList.add(new TextComponentString(I18n.format("htmltech.laser.amperage", laserContainer.getDiodeAmperage())));
    }
}
