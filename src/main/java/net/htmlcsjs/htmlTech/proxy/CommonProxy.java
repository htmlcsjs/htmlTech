package net.htmlcsjs.htmlTech.proxy;

import net.htmlcsjs.htmlTech.htmlTech;
import net.htmlcsjs.htmlTech.loaders.HTRecipes;

public class CommonProxy {

    public void init() {
        htmlTech.logger.info("Registering Recipes");
        HTRecipes.registerHandlers();
    }
}
