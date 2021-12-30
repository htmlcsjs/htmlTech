package net.htmlcsjs.htmlTech.common.laserpipe;

import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.TransformUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.GTValues;
import gregtech.api.cover.ICoverable;
import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ModCompatibility;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.htmlcsjs.htmlTech.common.laserpipe.tile.TileEntityLaserPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class LaserPipeRenderer implements ICCBlockRenderer, IItemRenderer {

    public static final ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(new ResourceLocation(GTValues.MODID, "laser_pipe_normal"), "normal");
    public static final LaserPipeRenderer INSTANCE = new LaserPipeRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;
    private static final ThreadLocal<BlockRenderer.BlockFace> blockFaces = ThreadLocal.withInitial(BlockRenderer.BlockFace::new);
    private final Map<LaserPipeType, PipeTextureInfo> pipeTextures = new HashMap();

    public static void preInit() {
        HtmlTech.logger.info("initializing laser pipe renderer");
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("ht_laser_pipe");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        TextureUtils.addIconRegister(INSTANCE::registerIcons);
    }

    public void registerIcons(TextureMap map) {
        for (LaserPipeType laserPipeType : LaserPipeType.values()) {
            ResourceLocation inLocation = new ResourceLocation(HtmlTech.MODID, String.format("blocks/pipe/laser_%s", laserPipeType.name));
            ResourceLocation sideLocation = new ResourceLocation(HtmlTech.MODID, String.format("blocks/pipe/laser_%s", laserPipeType.name));

            TextureAtlasSprite inTexture = map.registerSprite(inLocation);
            TextureAtlasSprite sideTexture = map.registerSprite(sideLocation);
            this.pipeTextures.put(laserPipeType, new PipeTextureInfo(inTexture, sideTexture));
        }
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        HtmlTech.logger.info("registering laser pipe model");
        event.getModelRegistry().putObject(MODEL_LOCATION, this);
    }

    @Override
    public void renderItem(ItemStack rawItemStack, ItemCameraTransforms.TransformType transformType) {
        ItemStack stack = ModCompatibility.getRealItemStack(rawItemStack);
        if (stack.getItem() instanceof ItemBlockLaserPipe) {
            CCRenderState renderState = CCRenderState.instance();
            GlStateManager.enableBlend();
            renderState.reset();
            renderState.startDrawing(7, DefaultVertexFormats.ITEM);
            BlockLaserPipe blockFluidPipe = (BlockLaserPipe) ((ItemBlockLaserPipe) stack.getItem()).getBlock();
            LaserPipeType pipeType = blockFluidPipe.getItemPipeType(stack);
            if (pipeType != null) {
                int connections = 1 << EnumFacing.SOUTH.getIndex() | 1 << EnumFacing.NORTH.getIndex() | 1 << 6 + EnumFacing.SOUTH.getIndex() | 1 << 6 + EnumFacing.NORTH.getIndex();
                connections |= 4096;
                this.renderPipeBlock(pipeType, 0xFFFFFF, renderState, new IVertexOperation[0], connections);
            }

            renderState.draw();
            GlStateManager.disableBlend();
        }
    }

    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.setBrightness(world, pos);
        IVertexOperation[] pipeline = {new Translation(pos)};

        BlockLaserPipe blockFluidPipe = ((BlockLaserPipe) state.getBlock());
        TileEntityLaserPipe tileEntityPipe = (TileEntityLaserPipe) blockFluidPipe.getPipeTileEntity(world, pos);

        if (tileEntityPipe == null) {
            HtmlTech.logger.info("Tile is null");
            return false;
        }

        LaserPipeType fluidPipeType = tileEntityPipe.getPipeType();
        int paintingColor = tileEntityPipe.getPaintingColor();
        int connectedSidesMap = blockFluidPipe.getVisualConnections(tileEntityPipe);

        if (fluidPipeType != null) {
            HtmlTech.logger.debug("Rendering laser pipe block");
            BlockRenderLayer renderLayer = MinecraftForgeClient.getRenderLayer();

            if (renderLayer == BlockRenderLayer.CUTOUT)
                renderPipeBlock(fluidPipeType, paintingColor, renderState, pipeline, connectedSidesMap);

            ICoverable coverable = tileEntityPipe.getCoverableImplementation();
            coverable.renderCovers(renderState, new Matrix4().translate(pos.getX(), pos.getY(), pos.getZ()), renderLayer);
        }
        return true;
    }

    public void renderPipeBlock(LaserPipeType pipeType, int insulationColor, CCRenderState state, IVertexOperation[] pipeline, int connectMask) {
        int pipeColor = GTUtility.convertRGBtoOpaqueRGBA_CL(insulationColor);
        float thickness = pipeType.getThickness();
        ColourMultiplier multiplier = new ColourMultiplier(pipeColor);
        PipeTextureInfo textureInfo = this.pipeTextures.get(pipeType);
        IVertexOperation[] pipeConnectSide = ArrayUtils.addAll(pipeline, new IconTransformation(textureInfo.inTexture), multiplier);
        IVertexOperation[] pipeSide = ArrayUtils.addAll(pipeline, new IconTransformation(textureInfo.sideTexture), multiplier);

        Cuboid6 cuboid6 = BlockLaserPipe.getSideBox(null, thickness);
        if (connectMask == 0) {
            for (EnumFacing renderedSide : EnumFacing.VALUES) {
                renderPipeSide(state, pipeConnectSide, renderedSide, cuboid6);
            }
        } else {
            for (EnumFacing renderedSide : EnumFacing.VALUES) {
                if ((connectMask & 1 << renderedSide.getIndex()) == 0) {
                    int oppositeIndex = renderedSide.getOpposite().getIndex();
                    if ((connectMask & 1 << oppositeIndex) > 0 && (connectMask & 63 & ~(1 << oppositeIndex)) == 0) {
                        renderPipeSide(state, pipeConnectSide, renderedSide, cuboid6);
                    } else {
                        renderPipeSide(state, pipeSide, renderedSide, cuboid6);
                    }
                } else {
                    renderPipeCube(connectMask, state, pipeSide, pipeConnectSide, renderedSide, thickness);
                }
            }
        }
    }

    private static void renderPipeCube(int connections, CCRenderState renderState, IVertexOperation[] pipeline, IVertexOperation[] pipeConnectSide, EnumFacing side, float thickness) {
        Cuboid6 cuboid6 = BlockLaserPipe.getSideBox(side, thickness);
        for (EnumFacing renderedSide : EnumFacing.VALUES) {
            if (renderedSide.getAxis() != side.getAxis()) {
                renderPipeSide(renderState, pipeline, renderedSide, cuboid6);
            }
        }
        if ((connections & 1 << 12) > 0) {
            renderPipeSide(renderState, pipeConnectSide, side, cuboid6);
        } else if ((connections & 1 << (6 + side.getIndex())) > 0) {
            // if neighbour pipe is smaller, render closed texture
            renderPipeSide(renderState, pipeline, side, cuboid6);
        }
    }

    private static void renderPipeSide(CCRenderState renderState, IVertexOperation[] pipeline, EnumFacing side, Cuboid6 cuboid6) {
        BlockRenderer.BlockFace blockFace = blockFaces.get();
        blockFace.loadCuboidFace(cuboid6, side.getIndex());
        renderState.setPipeline(blockFace, 0, blockFace.verts.length, pipeline);
        renderState.render();
    }

    public void renderBrightness(IBlockState state, float brightness) {
    }

    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.setPipeline((new Vector3(new Vec3d(pos))).translation(), new IconTransformation(sprite));
        BlockLaserPipe blockFluidPipe = (BlockLaserPipe) state.getBlock();
        IPipeTile<LaserPipeType, EmptyNodeData> tileEntityPipe = blockFluidPipe.getPipeTileEntity(world, pos);
        if (tileEntityPipe != null) {
            LaserPipeType fluidPipeType = tileEntityPipe.getPipeType();
            if (fluidPipeType != null) {
                float thickness = fluidPipeType.getThickness();
                int connectedSidesMask = blockFluidPipe.getVisualConnections(tileEntityPipe);
                Cuboid6 baseBox = BlockLaserPipe.getSideBox((EnumFacing) null, thickness);
                BlockRenderer.renderCuboid(renderState, baseBox, 0);
                EnumFacing[] var13 = EnumFacing.VALUES;
                int var14 = var13.length;

                for (int var15 = 0; var15 < var14; ++var15) {
                    EnumFacing renderSide = var13[var15];
                    if ((connectedSidesMask & 1 << renderSide.getIndex()) > 0) {
                        Cuboid6 sideBox = BlockLaserPipe.getSideBox(renderSide, thickness);
                        BlockRenderer.renderCuboid(renderState, sideBox, 0);
                    }
                }

            }
        }
    }

    public void registerTextures(TextureMap map) {
    }

    public IModelState getTransforms() {
        return TransformUtils.DEFAULT_BLOCK;
    }

    public TextureAtlasSprite getParticleTexture() {
        return TextureUtils.getMissingSprite();
    }

    public boolean isBuiltInRenderer() {
        return true;
    }

    public boolean isAmbientOcclusion() {
        return true;
    }

    public boolean isGui3d() {
        return true;
    }

    public Pair<TextureAtlasSprite, Integer> getParticleTexture(IPipeTile<LaserPipeType, EmptyNodeData> tileEntity) {
        if (tileEntity == null) {
            return Pair.of(TextureUtils.getMissingSprite(), 16777215);
        } else {
            LaserPipeType fluidPipeType = tileEntity.getPipeType();
            if (fluidPipeType != null) {
                TextureAtlasSprite atlasSprite = this.pipeTextures.get(fluidPipeType).sideTexture;
                return Pair.of(atlasSprite, 16777215);
            } else {
                return Pair.of(TextureUtils.getMissingSprite(), 16777215);
            }
        }
    }

    private static class PipeTextureInfo {
        public final TextureAtlasSprite inTexture;
        public final TextureAtlasSprite sideTexture;

        public PipeTextureInfo(TextureAtlasSprite inTexture, TextureAtlasSprite sideTexture) {
            this.inTexture = inTexture;
            this.sideTexture = sideTexture;
        }
    }
}
