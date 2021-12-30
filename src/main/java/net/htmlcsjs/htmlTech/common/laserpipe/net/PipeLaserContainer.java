package net.htmlcsjs.htmlTech.common.laserpipe.net;

import gregtech.api.util.GTUtility;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.common.laserpipe.tile.TileEntityLaserPipe;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.Objects;

public class PipeLaserContainer implements ILaserContainer {

    private final LaserPipeNet net;
    private final TileEntityLaserPipe cable;
    private final EnumFacing facing;

    public PipeLaserContainer(LaserPipeNet net, TileEntityLaserPipe cable, EnumFacing facing) {
        this.net = Objects.requireNonNull(net);
        this.cable = Objects.requireNonNull(cable);
        this.facing = facing;
    }

    public long getInputPerSec() {
        return net.getEnergyFluxPerSec();
    }

    public long getOutputPerSec() {
        return net.getEnergyFluxPerSec();
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        if (side == null) {
            if (facing == null) return 0;
            side = facing;
        }

        long amperesUsed = 0L;
        List<LaserRoutePath> paths = net.getNetData(cable.getPos());
        for (LaserRoutePath path : paths) {
            if (GTUtility.arePosEqual(cable.getPos(), path.getPipePos()) && side == path.getFaceToHandler()) {
                //Do not insert into source handler
                continue;
            }
            ILaserContainer dest = path.getHandler(cable.getWorld());
            EnumFacing facing = path.getFaceToHandler().getOpposite();
            if (dest == null || !dest.inputsEnergy(facing) || dest.getEnergyCanBeInserted() <= 0) continue;
            long amps = 0;
            if (voltage > 0) {
                amps = dest.acceptEnergyFromNetwork(facing, voltage, amperage - amperesUsed);
            }
            amperesUsed += amps;
            for (TileEntityLaserPipe cable : path.getPath()) {
                cable.incrementAmperage(amps, voltage);
            }

            if (amperage == amperesUsed)
                break;
        }
        net.addEnergyFluxPerSec(amperesUsed * voltage);
        return amperesUsed;
    }

    private void burnCable(World world, BlockPos pos) {
        world.setBlockState(pos, Blocks.FIRE.getDefaultState());
        if (!world.isRemote) {
            ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    5 + world.rand.nextInt(3), 0.0, 0.0, 0.0, 0.1);
        }
    }

    @Override
    public long getInputAmperage() {
        return Short.MAX_VALUE;
    }

    @Override
    public long getInputVoltage() {
        return Integer.MAX_VALUE;
    }

    @Override
    public long getEnergyCapacity() {
        return getInputVoltage() * getInputAmperage();
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        HtmlTech.logger.fatal("Do not use changeEnergy() for cables! Use acceptEnergyFromNetwork()");
        return acceptEnergyFromNetwork(facing == null ? EnumFacing.UP : facing,
                energyToAdd / getInputAmperage(),
                energyToAdd / getInputVoltage()) * getInputVoltage();
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public long getEnergyStored() {
        return 0;
    }

    @Override
    public long getDiodeAmperage() {
        return 0;
    }

    @Override
    public long getDiodeVoltage() {
        return 0;
    }

    @Override
    public void setDiodeAmperage(long amperage) {

    }

    @Override
    public void setDiodeVoltage(long voltage) {

    }

    @Override
    public boolean isOneProbeHidden() {
        return true;
    }
}
