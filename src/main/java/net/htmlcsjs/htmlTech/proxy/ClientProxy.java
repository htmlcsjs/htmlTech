package net.htmlcsjs.htmlTech.proxy;

import net.htmlcsjs.htmlTech.api.laserpipe.BlockLaserPipe;
import net.htmlcsjs.htmlTech.api.laserpipe.LaserPipeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static net.htmlcsjs.htmlTech.api.blocks.MetaBlocks.LASER_PIPES;

public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
        LaserPipeRenderer.preInit();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (BlockLaserPipe pipe : LASER_PIPES) {
            ModelLoader.setCustomStateMapper(pipe, new DefaultStateMapper() {
                @Nonnull
                @Override
                protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                    return LaserPipeRenderer.MODEL_LOCATION;
                }
            });
        }
        for (BlockLaserPipe pipe : LASER_PIPES) {
            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(pipe), stack -> LaserPipeRenderer.MODEL_LOCATION);
        }
    }
}
