package net.htmlcsjs.htmlTech.api.laserpipe;

import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.simple.BlockSimplePipe;
import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import net.htmlcsjs.htmlTech.api.capability.HtmlTechCapabilities;
import net.htmlcsjs.htmlTech.api.laserpipe.net.WorldLaserNet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockLaserPipe extends BlockSimplePipe<LaserPipeType, EmptyNodeData, WorldLaserNet> {
    @Override
    public Class<LaserPipeType> getPipeTypeClass() {
        return LaserPipeType.class;
    }

    @Override
    public WorldLaserNet getWorldPipeNet(World world) {
        return WorldLaserNet.getWorldPipeNet(world);
    }

    @Override
    public TileEntityPipeBase<LaserPipeType, EmptyNodeData> createNewTileEntity(boolean b) {
        return b ? new TileEntityLaserPipeTickable() : new TileEntityLaserPipe();
    }

    @Override
    public EmptyNodeData createItemProperties(ItemStack itemStack) {
        return EmptyNodeData.INSTANCE;
    }

    @Override
    protected EmptyNodeData createProperties(LaserPipeType laserPipeType) {
        return EmptyNodeData.INSTANCE;
    }

    @Override
    protected EmptyNodeData getFallbackType() {
        return EmptyNodeData.INSTANCE;
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs creativeTabs, @Nonnull NonNullList<ItemStack> items) {
        for(LaserPipeType type : LaserPipeType.values()) {
            items.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public boolean canPipeConnectToBlock(IPipeTile iPipeTile, EnumFacing enumFacing, @Nullable TileEntity tileEntity) {
        return tileEntity != null && tileEntity.hasCapability(HtmlTechCapabilities.LASER_CONTAINER, enumFacing.getOpposite());
    }

    @Override
    public boolean canPipesConnect(IPipeTile iPipeTile, EnumFacing enumFacing, IPipeTile iPipeTile1) {
        return iPipeTile1 instanceof TileEntityLaserPipe;
    }

    @Override
    protected Pair<TextureAtlasSprite, Integer> getParticleTexture(World world, BlockPos blockPos) {
        return LaserPipeRenderer.INSTANCE.getParticleTexture(getPipeTileEntity(world, blockPos));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return LaserPipeRenderer.BLOCK_RENDER_TYPE;
    }
}
