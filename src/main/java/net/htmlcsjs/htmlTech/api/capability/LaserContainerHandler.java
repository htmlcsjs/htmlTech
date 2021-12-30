package net.htmlcsjs.htmlTech.api.capability;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.function.Predicate;

public class LaserContainerHandler extends MTETrait implements ILaserContainer {
    private long diodeVoltage;
    private long diodeAmperage;

    private final long maxCapacity;
    private long laserEnergyStored;

    private final long maxInputVoltage;
    private final long maxInputAmperage;

    private final long maxOutputVoltage;
    private final long maxOutputAmperage;

    private Predicate<EnumFacing> sideInputCondition;
    private Predicate<EnumFacing> sideOutputCondition;

    private long amps = 0;


    public LaserContainerHandler(MetaTileEntity tileEntity, long maxCapacity, long maxInputVoltage, long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage) {
        super(tileEntity);
        this.maxCapacity = maxCapacity;
        this.maxInputVoltage = maxInputVoltage;
        this.maxInputAmperage = maxInputAmperage;
        this.maxOutputVoltage = maxOutputVoltage;
        this.maxOutputAmperage = maxOutputAmperage;
    }

    public void setSideInputCondition(Predicate<EnumFacing> sideInputCondition) {
        this.sideInputCondition = sideInputCondition;
    }

    public void setSideOutputCondition(Predicate<EnumFacing> sideOutputCondition) {
        this.sideOutputCondition = sideOutputCondition;
    }

    public static LaserContainerHandler emitterContainer(MetaTileEntity tileEntity, long maxCapacity, long maxOutputVoltage, long maxOutputAmperage) {
        return new LaserContainerHandler(tileEntity, maxCapacity, 0L, 0L, maxOutputVoltage, maxOutputAmperage);
    }

    public static LaserContainerHandler receiverContainer(MetaTileEntity tileEntity, long maxCapacity, long maxInputVoltage, long maxInputAmperage) {
        return new LaserContainerHandler(tileEntity, maxCapacity, maxInputVoltage, maxInputAmperage, 0L, 0L);
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability.equals(HtmlTechCapabilities.LASER_CONTAINER)) {
            return HtmlTechCapabilities.LASER_CONTAINER.cast(this);
        } else {
            return null;
        }
    }

    @Override
    public long getDiodeAmperage() {
        return diodeAmperage;
    }

    @Override
    public long getDiodeVoltage() {
        return diodeVoltage;
    }

    @Override
    public void setDiodeAmperage(long amperage) {
        diodeAmperage = amperage;
    }

    @Override
    public void setDiodeVoltage(long voltage) {
        diodeVoltage = voltage;
    }

    public String getName() {
        return "LaserContainer";
    }

    @Override
    public int getNetworkID() {
        return TraitNetworkIds.TRAIT_ID_ENERGY_CONTAINER;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setLong("LaserEnergyStored", laserEnergyStored);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        this.laserEnergyStored = compound.getLong("LaserEnergyStored");
        notifyEnergyListener(true);
    }

    @Override
    public long getEnergyStored() {
        return this.laserEnergyStored;
    }

    public void setEnergyStored(long laserEnergyStored) {
        this.laserEnergyStored = laserEnergyStored;
        if (!metaTileEntity.getWorld().isRemote) {
            metaTileEntity.markDirty();
            notifyEnergyListener(false);
        }
    }

    protected void notifyEnergyListener(boolean isInitialChange) {
        if (metaTileEntity instanceof ILaserEnergyChangeListener) {
            ((ILaserEnergyChangeListener) metaTileEntity).onLaserEnergyChanged(this, isInitialChange);
        }
    }

    public boolean dischargeOrRechargeEnergyContainers(IItemHandlerModifiable itemHandler, int slotIndex) {
        ItemStack stackInSlot = itemHandler.getStackInSlot(slotIndex);
        if (stackInSlot.isEmpty()) {
            return false;
        }
        IElectricItem electricItem = stackInSlot.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (electricItem == null || !electricItem.canProvideChargeExternally()) {
            return false;
        }
        int machineTier = GTUtility.getTierByVoltage(Math.max(getInputVoltage(), getOutputVoltage()));

        if (getEnergyCanBeInserted() > 0) {
            double chargePercent = getEnergyStored() / (getEnergyCapacity() * 1.0);
            if (chargePercent <= 0.5) {
                long dischargedBy = electricItem.discharge(getEnergyCanBeInserted(), machineTier, false, true, false);
                addEnergy(dischargedBy);
                return dischargedBy > 0L;

            } else if (chargePercent >= 0.9) {
                long chargedBy = electricItem.charge(getEnergyStored(), machineTier, false, false);
                removeEnergy(chargedBy);
                return chargedBy > 0L;
            }
        }
        return false;
    }

    @Override
    public void update() {
        amps = 0;
        if (getMetaTileEntity().getWorld().isRemote)
            return;
        if (getEnergyStored() >= getDiodeVoltage() && getDiodeVoltage() > 0 && getDiodeAmperage() > 0) {
            long outputVoltage = getDiodeVoltage();
            long outputAmperes = Math.min(getEnergyStored() / outputVoltage, getDiodeAmperage());
            if (outputAmperes == 0) return;
            long amperesUsed = 0;
            for (EnumFacing side : EnumFacing.VALUES) {
                if (!outputsEnergy(side)) continue;
                TileEntity tileEntity = metaTileEntity.getWorld().getTileEntity(metaTileEntity.getPos().offset(side));
                EnumFacing oppositeSide = side.getOpposite();
                if (tileEntity != null && tileEntity.hasCapability(HtmlTechCapabilities.LASER_CONTAINER, oppositeSide)) {
                    ILaserContainer laserContainer = tileEntity.getCapability(HtmlTechCapabilities.LASER_CONTAINER, oppositeSide);
                    if (laserContainer == null || !laserContainer.inputsEnergy(oppositeSide)) continue;
                    amperesUsed += laserContainer.acceptEnergyFromNetwork(oppositeSide, outputVoltage, outputAmperes - amperesUsed);
                    if (amperesUsed == outputAmperes) break;
                }
            }
            if (amperesUsed > 0) {
                setEnergyStored(getEnergyStored() - amperesUsed * outputVoltage);
            }
        }
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        if(amps >= getInputAmperage()) return 0;
        long canAccept = getEnergyCapacity() - getEnergyStored();
        if (voltage > 0L && (side == null || inputsEnergy(side))) {
            if (voltage > getInputVoltage()) {
                metaTileEntity.doExplosion(GTUtility.getExplosionPower(voltage));
                return Math.min(amperage, getInputAmperage() - amps);
            }
            if (canAccept >= voltage) {
                long amperesAccepted = Math.min(canAccept / voltage, Math.min(amperage, getInputAmperage() - amps));
                if (amperesAccepted > 0) {
                    setEnergyStored(getEnergyStored() + voltage * amperesAccepted);
                    amps += amperesAccepted;
                    return amperesAccepted;
                }
            }
        }
        return 0;
    }

    @Override
    public long getEnergyCapacity() {
        return this.maxCapacity;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return !outputsEnergy(side) && getInputVoltage() > 0 && (sideInputCondition == null || sideInputCondition.test(side));
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return getOutputVoltage() > 0 && (sideOutputCondition == null || sideOutputCondition.test(side));
    }

    @Override
    public long changeEnergy(long laserToAdd) {
        long oldEnergyStored = getEnergyStored();
        long newEnergyStored = (maxCapacity - oldEnergyStored < laserToAdd) ? maxCapacity : (oldEnergyStored + laserToAdd);
        if (newEnergyStored < 0)
            newEnergyStored = 0;
        setEnergyStored(newEnergyStored);
        return newEnergyStored - oldEnergyStored;
    }

    @Override
    public long getOutputVoltage() {
        return this.maxOutputVoltage;
    }

    @Override
    public long getOutputAmperage() {
        return this.maxOutputAmperage;
    }

    @Override
    public long getInputAmperage() {
        return this.maxInputAmperage;
    }

    @Override
    public long getInputVoltage() {
        return this.maxInputVoltage;
    }

    public interface ILaserEnergyChangeListener {
        void onLaserEnergyChanged(ILaserContainer container, boolean isInitialChange);
    }
}
