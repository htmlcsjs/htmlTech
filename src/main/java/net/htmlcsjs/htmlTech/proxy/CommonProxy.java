package net.htmlcsjs.htmlTech.proxy;

import net.htmlcsjs.htmlTech.HTValues;
import net.htmlcsjs.htmlTech.htmlTech;
import net.htmlcsjs.htmlTech.loaders.HTRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HTValues.MODID)
public class CommonProxy {

    public void init() {

    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        htmlTech.logger.info("Registering Recipes");
        HTRecipes.init();
    }
}
