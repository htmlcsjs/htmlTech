package net.htmlcsjs.htmlTech;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = htmlTech.MODID, name = htmlTech.NAME, version = htmlTech.VERSION,
        dependencies = "required-after:gregtech@[1.0,);")
public class htmlTech {
    public static final String MODID = "htmltech";
    public static final String NAME = "htmlcsjs's Tech Mod";
    public static final String VERSION = "@VERSION@";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

}