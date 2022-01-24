package net.htmlcsjs.htmlTech.proxy;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.htmlcsjs.htmlTech.client.HTTextures;
import net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks;
import net.htmlcsjs.htmlTech.common.laserpipe.BlockLaserPipe;
import net.htmlcsjs.htmlTech.common.laserpipe.LaserPipeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.LASER_PIPES;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
        HTTextures.preInit();
        LaserPipeRenderer.INSTANCE.preInit();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (BlockLaserPipe pipe : LASER_PIPES) {
            ModelLoader.setCustomStateMapper(pipe, new DefaultStateMapper() {
                @Nonnull
                @Override
                protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                    return LaserPipeRenderer.INSTANCE.getModelLocation();
                }
            });
            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(pipe), stack -> LaserPipeRenderer.INSTANCE.getModelLocation());
        }

        HTMetaBlocks.registerItemModels();
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
        AbstractClientPlayer clientPlayer = (AbstractClientPlayer) event.getEntityPlayer();
        UUID capedUUID = UUID.fromString("5d7073e3-882f-4c4a-94b3-0e5ba1c11e02");
        if (capedUUID.equals(clientPlayer.getUniqueID()) && clientPlayer.hasPlayerInfo() && clientPlayer.getLocationCape() == null) {
            NetworkPlayerInfo playerInfo = ObfuscationReflectionHelper.getPrivateValue(AbstractClientPlayer.class, clientPlayer, 0);
            Map<MinecraftProfileTexture.Type, ResourceLocation> playerTextures = ObfuscationReflectionHelper.getPrivateValue(NetworkPlayerInfo.class, playerInfo, 1);
            playerTextures.put(MinecraftProfileTexture.Type.CAPE, HTTextures.HTMLTECH_CAPE);
        }
    }
}
