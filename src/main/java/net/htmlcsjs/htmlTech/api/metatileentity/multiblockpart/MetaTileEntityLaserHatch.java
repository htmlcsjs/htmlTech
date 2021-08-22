package net.htmlcsjs.htmlTech.api.metatileentity.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.render.SimpleOverlayRenderer;
import gregtech.api.render.Textures;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityMultiblockPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class MetaTileEntityLaserHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IEnergyContainer> {
    private final ItemStackHandler laserInventory;

    public MetaTileEntityLaserHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        laserInventory = new ItemStackHandler(1);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityLaserHatch(this.metaTileEntityId, this.getTier());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 178, 130).label(10, 5, this.getMetaFullName())
                .widget(new SlotWidget(this.laserInventory, 0, 80, 18, true, true).setBackgroundTexture(GuiTextures.SLOT))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 47).build(this.getHolder(), entityPlayer);
    }

    @Override
    public MultiblockAbility<IEnergyContainer> getAbility() {
        return HTMultiblockAbility.OUTPUT_LASER;
    }

    @Override
    public void registerAbilities(List<IEnergyContainer> list) {

    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            SimpleOverlayRenderer renderer = Textures.PIPE_OUT_OVERLAY;
            renderer.renderSided(this.getFrontFacing(), renderState, translation, pipeline);
            SimpleOverlayRenderer overlay = Textures.ITEM_HATCH_OUTPUT_OVERLAY;
            overlay.renderSided(this.getFrontFacing(), renderState, translation, pipeline);
        }
    }
}
