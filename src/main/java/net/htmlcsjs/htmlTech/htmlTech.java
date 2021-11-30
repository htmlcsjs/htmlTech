package net.htmlcsjs.htmlTech;

import gregtech.api.capability.SimpleCapabilityManager;
import net.htmlcsjs.htmlTech.api.blocks.HTMetaBlocks;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.api.metatileentity.HTMetaTileEntities;
import net.htmlcsjs.htmlTech.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = htmlTech.MODID, name = htmlTech.NAME, version = htmlTech.VERSION,
        dependencies = "required-after:gregtech;")
public class htmlTech {
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
    }
}