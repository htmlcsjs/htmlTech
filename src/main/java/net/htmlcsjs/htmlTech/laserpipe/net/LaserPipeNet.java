package net.htmlcsjs.htmlTech.laserpipe.net;

import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.block.simple.EmptyNodeData;
import net.minecraft.nbt.NBTTagCompound;

public class LaserPipeNet extends PipeNet<EmptyNodeData> {

    public LaserPipeNet(WorldPipeNet<EmptyNodeData, ? extends PipeNet> world) {
        super(world);
    }

    @Override
    protected void writeNodeData(EmptyNodeData emptyNodeData, NBTTagCompound nbtTagCompound) {

    }

    @Override
    protected EmptyNodeData readNodeData(NBTTagCompound nbtTagCompound) {
        return null;
    }
}
