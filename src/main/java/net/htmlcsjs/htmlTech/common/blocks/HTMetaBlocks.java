package net.htmlcsjs.htmlTech.common.blocks;

import net.htmlcsjs.htmlTech.HtmlTech;
import net.htmlcsjs.htmlTech.common.laserpipe.BlockLaserPipe;
import net.htmlcsjs.htmlTech.common.laserpipe.LaserPipeType;
import net.htmlcsjs.htmlTech.common.laserpipe.tile.TileEntityLaserPipe;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HTMetaBlocks {
    public static final BlockLaserPipe[] LASER_PIPES = new BlockLaserPipe[1];
    public static BlockHTCasing HT_CASING;

    public static void init() {
        // MTEn't TileEntities
        for (LaserPipeType type : LaserPipeType.values()) {
            LASER_PIPES[type.ordinal()] = new BlockLaserPipe();
            LASER_PIPES[type.ordinal()].setRegistryName(String.format("laser_pipe_%s", type.name));
        }

        GameRegistry.registerTileEntity(TileEntityLaserPipe.class, new ResourceLocation(HtmlTech.MODID, "laser_pipe"));

        // Basic Blocks
        HT_CASING = new BlockHTCasing();
        HT_CASING.setRegistryName("casing");
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(HT_CASING);
    }

    /*
     * mostly copy and pasted from GregTech
     */
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            //noinspection ConstantConditions
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                    block.getMetaFromState(state),
                    new ModelResourceLocation(block.getRegistryName(),
                            statePropertiesToString(state.getProperties())));
        }
    }

    /*
     * mostly copy and pasted from GregTech
     */
    private static String statePropertiesToString(Map<IProperty<?>, Comparable<?>> properties) {
        StringBuilder stringbuilder = new StringBuilder();

        List<Map.Entry<IProperty<?>, Comparable<?>>> entries = properties.entrySet().stream()
                .sorted(Comparator.comparing(c -> c.getKey().getName()))
                .collect(Collectors.toList());

        for (Map.Entry<IProperty<?>, Comparable<?>> entry : entries) {
            if (stringbuilder.length() != 0) {
                stringbuilder.append(",");
            }

            IProperty<?> property = entry.getKey();
            stringbuilder.append(property.getName());
            stringbuilder.append("=");
            stringbuilder.append(getPropertyName(property, entry.getValue()));
        }

        if (stringbuilder.length() == 0) {
            stringbuilder.append("normal");
        }

        return stringbuilder.toString();
    }

    /*
     * mostly copy and pasted from GregTech
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
        return property.getName((T) value);
    }
}
