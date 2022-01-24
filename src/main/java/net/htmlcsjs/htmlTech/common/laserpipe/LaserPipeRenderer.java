package net.htmlcsjs.htmlTech.common.laserpipe;

import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.GTValues;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.pipe.PipeRenderer;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class LaserPipeRenderer extends PipeRenderer {

    public static final LaserPipeRenderer INSTANCE = new LaserPipeRenderer();
    private final Map<LaserPipeType, TextureAtlasSprite> pipeTextures = new HashMap();

    public LaserPipeRenderer() {
        super("ht_laser_pipe", new ResourceLocation(HtmlTech.MODID, "laser_pipe_normal"));
    }

    public void registerIcons(TextureMap map) {
        for (LaserPipeType laserPipeType : LaserPipeType.values()) {
            ResourceLocation inLocation = new ResourceLocation(HtmlTech.MODID, String.format("blocks/pipe/laser_%s", laserPipeType.name));
            this.pipeTextures.put(laserPipeType, map.registerSprite(inLocation));
        }
    }

    @Override
    public void buildRenderer(PipeRenderContext renderContext, BlockPipe<?, ?, ?> blockPipe, @Nullable IPipeTile<?, ?> iPipeTile, IPipeType<?> pipeType, @Nullable Material material) {
        if (!(pipeType instanceof LaserPipeType)) {
            return;
        }
        TextureAtlasSprite texture = this.pipeTextures.get(pipeType);
        renderContext.addOpenFaceRender(new IconTransformation(texture))
                .addSideRender(new IconTransformation(texture));
    }

    @Override
    public TextureAtlasSprite getParticleTexture(IPipeType<?> pipeType, @Nullable Material material) {
        if (!(pipeType instanceof LaserPipeType))
            return TextureUtils.getMissingSprite();
        return pipeTextures.get(pipeType);
    }
}
