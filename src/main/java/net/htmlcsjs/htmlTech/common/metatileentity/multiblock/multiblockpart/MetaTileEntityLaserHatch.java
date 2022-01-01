package net.htmlcsjs.htmlTech.common.metatileentity.multiblock.multiblockpart;

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
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.client.utils.PipelineUtil;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.api.capability.LaserContainerHandler;
import net.htmlcsjs.htmlTech.client.HTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

import static net.htmlcsjs.htmlTech.api.unification.materials.HTMaterials.LASER;

public class MetaTileEntityLaserHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ILaserContainer> {
    private final ItemStackHandler laserInventory;
    private final ILaserContainer laserEnergyContainer;
    private final boolean isEmitter;

    public MetaTileEntityLaserHatch(ResourceLocation metaTileEntityId, int tier, boolean isEmitter) {
        super(metaTileEntityId, tier);
        this.laserInventory = new ItemStackHandler(1);
        this.isEmitter = isEmitter;
        if (isEmitter) {
            this.laserEnergyContainer = LaserContainerHandler.emitterContainer(this, GTValues.V[14] * Short.MAX_VALUE, GTValues.V[14], Short.MAX_VALUE);
            ((LaserContainerHandler) this.laserEnergyContainer).setSideOutputCondition(s -> s == getFrontFacing());
        } else {
            this.laserEnergyContainer = LaserContainerHandler.receiverContainer(this, GTValues.V[tier] * Short.MAX_VALUE, GTValues.V[tier], Short.MAX_VALUE);
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
        if (isEmitter && getOffsetTimer() % 20 == 0) {
            Item laserItem = laserInventory.getStackInSlot(0).getItem();
            if (laserItem.getRegistryName().toString().equals("gregtech:meta_laser")) {
                Material material = GregTechAPI.MATERIAL_REGISTRY.getObjectById(laserItem.getDamage(laserInventory.getStackInSlot(0)));
                laserEnergyContainer.setDiodeAmperage(material.getProperty(LASER).amperage);
                laserEnergyContainer.setDiodeVoltage(material.getProperty(LASER).voltage);
            } else {
                laserEnergyContainer.setDiodeVoltage(0);
                laserEnergyContainer.setDiodeAmperage(0);
            }
            HtmlTech.logger.debug("New diode: " + laserEnergyContainer.getDiodeAmperage() + "A, " + laserEnergyContainer.getDiodeVoltage() + "Eu/t");
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
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        String tierName = GTValues.VNF[getTier()];

        if (isEmitter) {
            // TODO: tooltip description
        } else {
            tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", laserEnergyContainer.getInputVoltage(), tierName));
            tooltip.add(I18n.format("gregtech.universal.tooltip.amperage_in_till", laserEnergyContainer.getInputAmperage()));
        }
        tooltip.add(I18n.format("gregtech.universal.enabled"));
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

