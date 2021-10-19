package net.htmlcsjs.htmlTech.proxy;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import net.htmlcsjs.htmlTech.HTValues;
import net.htmlcsjs.htmlTech.api.blocks.MetaBlocks;
import net.htmlcsjs.htmlTech.api.item.HTMetaItems;
import net.htmlcsjs.htmlTech.api.laserpipe.BlockLaserPipe;
import net.htmlcsjs.htmlTech.api.laserpipe.ItemBlockLaserPipe;
import net.htmlcsjs.htmlTech.api.materials.HTMaterials;
import net.htmlcsjs.htmlTech.api.materials.HTOrePrefix;
import net.htmlcsjs.htmlTech.htmlTech;
import net.htmlcsjs.htmlTech.api.materials.HTRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

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
        IForgeRegistry<Block> registry = event.getRegistry();
        for(BlockLaserPipe pipe : MetaBlocks.LASER_PIPES) registry.register(pipe);
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        for(BlockLaserPipe pipe : MetaBlocks.LASER_PIPES) registry.register(createItemBlock(pipe, ItemBlockLaserPipe::new));
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
