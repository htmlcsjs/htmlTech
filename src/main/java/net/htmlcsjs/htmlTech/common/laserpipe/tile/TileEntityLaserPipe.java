package net.htmlcsjs.htmlTech.common.laserpipe.tile;

import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import net.htmlcsjs.htmlTech.api.capability.HtmlTechCapabilities;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.common.laserpipe.LaserPipeType;
import net.htmlcsjs.htmlTech.common.laserpipe.net.PipeLaserContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityLaserPipe extends TileEntityPipeBase<LaserPipeType, EmptyNodeData> {

    private PipeLaserContainer laserContainer;

    public PipeLaserContainer getLaserContainer() {
        if(laserContainer == null) {
            laserContainer = new PipeLaserContainer(this);
        }
        return laserContainer;
    }

    @Override
    public Class<LaserPipeType> getPipeTypeClass() {
        return LaserPipeType.class;
    }

    @Override
    public boolean supportsTicking() {
        return false;
    }

    @Override
    public <T> T getCapabilityInternal(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == HtmlTechCapabilities.LASER_CONTAINER && isConnectionOpenAny(facing)) {
            return HtmlTechCapabilities.LASER_CONTAINER.cast(getLaserContainer());
        }
        return super.getCapabilityInternal(capability, facing);
    }

    public ILaserContainer findLaserContainer(EnumFacing facing) {
        ILaserContainer laserContainer = null;
        BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain(getPos());
        while (laserContainer == null) {
            pos.move(facing);
            TileEntity tile = world.getTileEntity(pos);
            if(tile == null)
                break;
            if(tile instanceof TileEntityLaserPipe)
                continue;
            laserContainer = tile.getCapability(HtmlTechCapabilities.LASER_CONTAINER, facing.getOpposite());
        }
        return laserContainer;
    }
}
