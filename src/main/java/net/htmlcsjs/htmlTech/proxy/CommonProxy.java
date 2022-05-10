package net.htmlcsjs.htmlTech.proxy;

import gregtech.api.GregTechAPI;
import gregtech.api.block.VariantItemBlock;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.htmlcsjs.htmlTech.api.HTValues;
import net.htmlcsjs.htmlTech.api.unification.materials.HTMaterials;
import net.htmlcsjs.htmlTech.api.unification.materials.HTOrePrefix;
import net.htmlcsjs.htmlTech.common.laserpipe.BlockLaserPipe;
import net.htmlcsjs.htmlTech.common.laserpipe.ItemBlockLaserPipe;
import net.htmlcsjs.htmlTech.loaders.HTRecipeManiger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.HT_CASING;
import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.LASER_PIPES;

@Mod.EventBusSubscriber(modid = HTValues.MODID)
public class CommonProxy {

    public void preLoad() {
    }

    public void init() {
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        HtmlTech.logger.info("Registering Recipes");
        HTRecipeManiger.init();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        HtmlTech.logger.info("Registering Blocks");
        IForgeRegistry<Block> registry = event.getRegistry();
        for(BlockLaserPipe pipe : LASER_PIPES) registry.register(pipe);
        registry.register(HT_CASING);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        HtmlTech.logger.info("Registering Items");
        IForgeRegistry<Item> registry = event.getRegistry();
        for(BlockLaserPipe pipe : LASER_PIPES) registry.register(createItemBlock(pipe, ItemBlockLaserPipe::new));

        registry.register(createItemBlock(HT_CASING, VariantItemBlock::new));
    }

    @SubscribeEvent
    public static void materialRegister(GregTechAPI.MaterialEvent event) {
        HTMaterials.init();
        HTOrePrefix.init();
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }
}
