package net.htmlcsjs.htmlTech.integration.theoneprobe.provider;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.htmlcsjs.htmlTech.api.HTValues;
import net.htmlcsjs.htmlTech.api.capability.HtmlTechCapabilities;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

public class LaserContainerInfoProvider extends CapabilityInfoProvider<ILaserContainer> {

    @Override
    @Nonnull
    protected Capability<ILaserContainer> getCapability() {
        return HtmlTechCapabilities.LASER_CONTAINER;
    }
    @Override
    public String getID() {
        return String.format("%s:laser_container_provider", HTValues.MODID);
    }

    @Override
    protected boolean allowDisplaying(ILaserContainer capability) {
        return !capability.isOneProbeHidden();
    }

    @Override
    protected void addProbeInfo(ILaserContainer laserContainer, IProbeInfo probeInfo, EntityPlayer player, TileEntity tileEntity, IProbeHitData probeHitData) {
        long energyStored = laserContainer.getEnergyStored();
        long maxStorage = laserContainer.getEnergyCapacity();
        if (maxStorage == 0) return; //do not add empty max storage progress bar
        IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
        String additionalSpacing = tileEntity.hasCapability(GregtechTileCapabilities.CAPABILITY_WORKABLE, probeHitData.getSideHit()) ? "   " : "";
        horizontalPane.text(TextStyleClass.INFO + "{*gregtech.top.energy_stored*} " + additionalSpacing);
        horizontalPane.progress(energyStored, maxStorage, probeInfo.defaultProgressStyle()
                .suffix("/" + maxStorage + " EU")
                .borderColor(0x00000000)
                .backgroundColor(0x00000000)
                .filledColor(0xFFFFE000)
                .alternateFilledColor(0xFFEED000));
    }
}
