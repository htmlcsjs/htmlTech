package net.htmlcsjs.htmlTech.api.blocks;

import gregtech.api.GTValues;
import net.htmlcsjs.htmlTech.api.laserpipe.BlockLaserPipe;
import net.htmlcsjs.htmlTech.api.laserpipe.LaserPipeType;
import net.htmlcsjs.htmlTech.api.laserpipe.TileEntityLaserPipe;
import net.htmlcsjs.htmlTech.htmlTech;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MetaBlocks {
    public static final BlockLaserPipe[] LASER_PIPES = new BlockLaserPipe[1];

    public static void init() {
        for (LaserPipeType type : LaserPipeType.values()) {
            LASER_PIPES[type.ordinal()] = new BlockLaserPipe();
            LASER_PIPES[type.ordinal()].setRegistryName(String.format("laser_pipe_%s", type.name));
        }

        GameRegistry.registerTileEntity(TileEntityLaserPipe.class, new ResourceLocation(htmlTech.MODID, "laser_pipe"));
    }
}
