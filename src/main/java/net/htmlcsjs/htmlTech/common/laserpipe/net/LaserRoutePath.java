package net.htmlcsjs.htmlTech.common.laserpipe.net;

import net.htmlcsjs.htmlTech.api.capability.HtmlTechCapabilities;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.common.laserpipe.tile.TileEntityLaserPipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class LaserRoutePath {
    private final BlockPos destPipePos;
    private final EnumFacing destFacing;
    private final int distance;
    private final Set<TileEntityLaserPipe> path;

    public LaserRoutePath(BlockPos destPipePos, EnumFacing destFacing, Set<TileEntityLaserPipe> path, int distance) {
        this.destPipePos = destPipePos;
        this.destFacing = destFacing;
        this.path = path;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public Set<TileEntityLaserPipe> getPath() {
        return path;
    }

    public BlockPos getPipePos() {
        return destPipePos;
    }

    public EnumFacing getFaceToHandler() {
        return destFacing;
    }

    public BlockPos getHandlerPos() {
        return destPipePos.offset(destFacing);
    }

    public ILaserContainer getHandler(World world) {
        TileEntity tile = world.getTileEntity(getHandlerPos());
        if(tile != null) {
            return tile.getCapability(HtmlTechCapabilities.LASER_CONTAINER, destFacing.getOpposite());
        }
        return null;
    }
}
