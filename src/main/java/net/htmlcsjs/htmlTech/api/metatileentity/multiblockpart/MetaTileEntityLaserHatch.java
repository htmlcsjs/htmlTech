package net.htmlcsjs.htmlTech.api.metatileentity.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.render.SimpleOverlayRenderer;
import gregtech.api.unification.material.Material;
import gregtech.api.util.PipelineUtil;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityMultiblockPart;
import net.htmlcsjs.htmlTech.api.HTTextures;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.api.capability.LaserContainerHandler;
import net.htmlcsjs.htmlTech.htmlTech;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

import static net.htmlcsjs.htmlTech.api.materials.HTMaterials.LASER;

public class MetaTileEntityLaserHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ILaserContainer> {
    private final ItemStackHandler laserInventory;
    private final ILaserContainer laserEnergyContainer;
    private final boolean isEmitter;
    private ItemStackHandler diodeItem;

    public MetaTileEntityLaserHatch(ResourceLocation metaTileEntityId, int tier, boolean isEmitter) {
        super(metaTileEntityId, tier);
        this.laserInventory = new ItemStackHandler(1);
        this.isEmitter = isEmitter;
        this.diodeItem = new ItemStackHandler(1);
        if (isEmitter) {
            this.laserEnergyContainer = LaserContainerHandler.emitterContainer(this, GTValues.V[14] * 128L, GTValues.V[14], Integer.MAX_VALUE);
            ((LaserContainerHandler) this.laserEnergyContainer).setSideOutputCondition(s -> s == getFrontFacing());
        } else {
            this.laserEnergyContainer = LaserContainerHandler.receiverContainer(this, GTValues.V[14] * 128L, GTValues.V[14], Integer.MAX_VALUE * 2L);
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityLaserHatch(this.metaTileEntityId, this.getTier(), this.isEmitter);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        if (isEmitter) {
            return ModularUI.builder(GuiTextures.BACKGROUND, 178, 130).label(10, 5, this.getMetaFullName())
                    .widget(new SlotWidget(this.laserInventory, 0, 80, 18, true, true).setBackgroundTexture(GuiTextures.SLOT))
                    .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 47).build(this.getHolder(), entityPlayer);
        } else {
            return null;
        }
    }

    @Override
    public MultiblockAbility<ILaserContainer> getAbility() {
        if (isEmitter) {
            return HTMultiblockAbility.OUTPUT_LASER;
        } else {
            return HTMultiblockAbility.INPUT_LASER;
        }
    }

    @Override
    public void registerAbilities(List<ILaserContainer> abilityList) {
        abilityList.add(laserEnergyContainer);
    }

    @Override
    public void update() {
        super.update();
        if ((isEmitter && !laserInventory.equals(diodeItem)) && getOffsetTimer() % 20 == 0) {
            diodeItem = laserInventory;

            Item laserItem = diodeItem.getStackInSlot(0).getItem();
            if (laserItem.getRegistryName().toString().equals("gregtech:meta_laser")) {
                Material material = GregTechAPI.MATERIAL_REGISTRY.getObjectById(laserItem.getDamage(laserInventory.getStackInSlot(0)));
                laserEnergyContainer.setDiodeAmperage(material.getProperty(LASER).amperage);
                laserEnergyContainer.setDiodeVoltage(material.getProperty(LASER).voltage);
            } else {
                laserEnergyContainer.setDiodeVoltage(0);
                laserEnergyContainer.setDiodeAmperage(0);
            }
            htmlTech.logger.debug("New diode: " + String.valueOf(laserEnergyContainer.getDiodeAmperage()) + "A, " + String.valueOf(laserEnergyContainer.getDiodeVoltage()) + "Eu/t");
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        SimpleOverlayRenderer overlay;
        if (this.shouldRenderOverlay()) {
            if (isEmitter) {
                overlay = HTTextures.LASER_OUTPUT;
            } else {
                overlay = HTTextures.LASER_INPUT;
            }
            overlay.renderSided(this.getFrontFacing(), renderState, translation, PipelineUtil.color(pipeline, GTValues.VC[this.getTier()]));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("LaserInventory", laserInventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.laserInventory.deserializeNBT(data.getCompoundTag("LaserInventory"));
    }

}
