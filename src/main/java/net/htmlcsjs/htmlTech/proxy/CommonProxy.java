package net.htmlcsjs.htmlTech.proxy;

import gregtech.api.GregTechAPI;
import gregtech.common.blocks.VariantItemBlock;
import net.htmlcsjs.htmlTech.HTValues;
import net.htmlcsjs.htmlTech.api.item.HTMetaItems;
import net.htmlcsjs.htmlTech.api.laserpipe.BlockLaserPipe;
import net.htmlcsjs.htmlTech.api.laserpipe.ItemBlockLaserPipe;
import net.htmlcsjs.htmlTech.api.materials.HTMaterials;
import net.htmlcsjs.htmlTech.api.materials.HTOrePrefix;
import net.htmlcsjs.htmlTech.api.materials.HTRecipes;
import net.htmlcsjs.htmlTech.htmlTech;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

import static net.htmlcsjs.htmlTech.api.blocks.HTMetaBlocks.HT_CASING;
import static net.htmlcsjs.htmlTech.api.blocks.HTMetaBlocks.LASER_PIPES;

@Mod.EventBusSubscriber(modid = HTValues.MODID)
public class CommonProxy {

    public void preLoad() {
        HTMetaItems.init();
    }

    public void init() {

    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        htmlTech.logger.info("Registering Recipes");
        HTRecipes.init();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        htmlTech.logger.info("Registering Blocks");
        IForgeRegistry<Block> registry = event.getRegistry();
        for(BlockLaserPipe pipe : LASER_PIPES) registry.register(pipe);
        registry.register(HT_CASING);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        htmlTech.logger.info("Registering Items");
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
