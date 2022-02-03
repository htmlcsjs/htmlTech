package net.htmlcsjs.htmlTech;

import gregtech.api.GTValues;
import gregtech.api.capability.SimpleCapabilityManager;
import net.htmlcsjs.htmlTech.api.HTValues;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks;
import net.htmlcsjs.htmlTech.common.command.HtmlTechCommand;
import net.htmlcsjs.htmlTech.common.metatileentity.HTMetaTileEntities;
import net.htmlcsjs.htmlTech.integration.theoneprobe.HTTOPCompatibility;
import net.htmlcsjs.htmlTech.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = HtmlTech.MODID, name = HtmlTech.NAME, version = HtmlTech.VERSION,
        dependencies = "required-after:gregtech;")
public class HtmlTech {
    public static final String MODID = HTValues.MODID;
    public static final String NAME = HTValues.MOD_NAME;
    public static final String VERSION = HTValues.VERSION;

    public static Logger logger;

    @SidedProxy(modId = HTValues.MODID, serverSide = "net.htmlcsjs.htmlTech.proxy.CommonProxy", clientSide = "net.htmlcsjs.htmlTech.proxy.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        SimpleCapabilityManager.registerCapabilityWithNoDefault(ILaserContainer.class);
        HTMetaBlocks.init();
        HTMetaTileEntities.init();
        proxy.preLoad();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();

        if (GTValues.isModLoaded(HTValues.MODID_TOP)) {
            logger.info("TheOneProbe found. Enabling integration...");
            HTTOPCompatibility.registerCompatibility();
        }
    }

    @EventHandler
    public void onServerLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new HtmlTechCommand());
    }
}