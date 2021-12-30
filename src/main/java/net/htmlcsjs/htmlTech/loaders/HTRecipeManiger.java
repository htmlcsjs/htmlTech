package net.htmlcsjs.htmlTech.loaders;

import net.htmlcsjs.htmlTech.loaders.recipe.LaserEquipmentLoader;
import net.htmlcsjs.htmlTech.loaders.recipe.MaterialLoader;

public class HTRecipeManiger {

    public static void init() {
        LaserEquipmentLoader.init();
        MaterialLoader.init();
    }
}
