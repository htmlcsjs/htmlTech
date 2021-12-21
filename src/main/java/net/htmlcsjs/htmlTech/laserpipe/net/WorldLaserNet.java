package net.htmlcsjs.htmlTech.laserpipe.net;

import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.block.simple.EmptyNodeData;
import net.minecraft.world.World;

public class WorldLaserNet extends WorldPipeNet<EmptyNodeData, LaserPipeNet> {

    private static final String DATA_ID_BASE = "htmltech.laser_pipe_net";

    public static WorldLaserNet getWorldPipeNet(World world) {
        String DATA_ID = getDataID(DATA_ID_BASE, world);
        WorldLaserNet netWorldData = (WorldLaserNet) world.loadData(WorldLaserNet.class, DATA_ID);
        if (netWorldData == null) {
            netWorldData = new WorldLaserNet(DATA_ID);
            world.setData(DATA_ID, netWorldData);
        }

        netWorldData.setWorldAndInit(world);
        return netWorldData;
    }

    public WorldLaserNet(String name) {
        super(name);
    }

    @Override
    protected LaserPipeNet createNetInstance() {
        return new LaserPipeNet(this);
    }
}
