package net.htmlcsjs.htmlTech.loaders;

import net.htmlcsjs.htmlTech.loaders.recipe.LaserEquipmentLoader;
import net.htmlcsjs.htmlTech.loaders.recipe.MaterialLoader;
import net.htmlcsjs.htmlTech.loaders.recipe.handlers.HTRecipeHandlers;

public class HTRecipeManiger {

    public static void init() {
        LaserEquipmentLoader.init();
        MaterialLoader.init();

        HTRecipeHandlers.register();
    }
}
