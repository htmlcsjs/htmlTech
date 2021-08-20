package net.htmlcsjs.htmlTech.api.item.behaviors;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.PhantomSlotWidget;
import gregtech.api.gui.widgets.SimpleTextWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.MaterialRegistry;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.items.ItemStackHandler;

import java.awt.*;

import static net.htmlcsjs.htmlTech.api.materials.HTMaterials.LASER;

public class LaserInspectorToolBehaviour implements IItemBehaviour, ItemUIFactory {

    private final ItemStackHandler laserInventory;

    public LaserInspectorToolBehaviour() {
        this.laserInventory = new ItemStackHandler(1);
    }

    public static LaserInspectorToolBehaviour getInstanceFor(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof MetaItem)) {
            return null;
        } else {
            MetaItem<?> metaItem = (MetaItem<?>) itemStack.getItem();
            MetaItem<?>.MetaValueItem valueItem = metaItem.getItem(itemStack);
            if (valueItem == null) {
                return null;
            } else {
                ItemUIFactory itemUIFactory = valueItem.getUIManager();
                return !(itemUIFactory instanceof LaserInspectorToolBehaviour) ? null : (LaserInspectorToolBehaviour) itemUIFactory;
            }
        }
    }

    private String getLaserName() {
        String itemName;
        ItemStack laserItem = laserInventory.getStackInSlot(0);
        if (laserItem.getDisplayName().equals("Air")) {
            itemName = "";
        } else {
            itemName = TextFormatting.UNDERLINE + laserItem.getDisplayName();
        }
        return itemName;
    }

    private String getLaserVoltage() {
        Item laserItem = laserInventory.getStackInSlot(0).getItem();
        if (laserItem.getRegistryName().toString().equals("minecraft:air")){
            return "";
        } else if (!laserItem.getRegistryName().toString().equals("gregtech:meta_laser")) {
            return I18n.format("metaitem.tool.laser.inspector.gui.not_laser");
        } else {
            Material material = MaterialRegistry.MATERIAL_REGISTRY.getObjectById(laserItem.getDamage(laserInventory.getStackInSlot(0)));
            int voltage = material.getProperty(LASER).voltage;
            return I18n.format("htmltech.laser.voltage", voltage, GTValues.VN[(GTUtility.getTierByVoltage(voltage))]);
        }
    }

    private String getLaserAmperage() {
        Item laserItem = laserInventory.getStackInSlot(0).getItem();
         if (!laserItem.getRegistryName().toString().equals("gregtech:meta_laser")) {
            return "";
        } else {
            Material material = MaterialRegistry.MATERIAL_REGISTRY.getObjectById(laserItem.getDamage(laserInventory.getStackInSlot(0)));
            int amperage = material.getProperty(LASER).amperage;
            return I18n.format("htmltech.laser.amperage", amperage);
        }
    }

    private String getLaserEfficiency() {
        Item laserItem = laserInventory.getStackInSlot(0).getItem();
        if (!laserItem.getRegistryName().toString().equals("gregtech:meta_laser")) {
            return "";
        } else {
            Material material = MaterialRegistry.MATERIAL_REGISTRY.getObjectById(laserItem.getDamage(laserInventory.getStackInSlot(0)));
            double efficiency = material.getProperty(LASER).efficiency;
            return I18n.format("htmltech.laser.efficiency", efficiency);
        }
    }

    @Override
    public ModularUI createUI(PlayerInventoryHolder playerInventoryHolder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BOXED_BACKGROUND, 412, 222)
                .label(17, 17, "metaitem.tool.laser.inspector.gui.title", Color.green.getRGB())
                .widget(new PhantomSlotWidget(laserInventory, 0, 17, 27)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.LENS_OVERLAY))
                .widget(new SimpleTextWidget(412/2, 47, "", Color.green.getRGB(), this::getLaserName))
                .widget(new SimpleTextWidget(412/2, 59, "", Color.green.getRGB(), this::getLaserVoltage))
                .widget(new SimpleTextWidget(412/2, 69, "", Color.green.getRGB(), this::getLaserAmperage))
                .widget(new SimpleTextWidget(412/2, 79, "", Color.green.getRGB(), this::getLaserEfficiency))
                .build(playerInventoryHolder, entityPlayer);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        LaserInspectorToolBehaviour behaviour = getInstanceFor(itemStack);
        if (!world.isRemote && behaviour != null) {
            PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
            holder.openUI();
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }
}
