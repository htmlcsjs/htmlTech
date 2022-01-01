package net.htmlcsjs.htmlTech.common;

import net.htmlcsjs.htmlTech.api.HTValues;
import net.minecraftforge.common.config.Config;

@Config(modid = HTValues.MODID)
public class HTConfig {
    @Config.Comment("Config for Laser Equipment")
    @Config.Name("Laser Options")
    @Config.RequiresMcRestart
    public static LaserOptions lasers = new LaserOptions();

    public static class LaserOptions {
        @Config.Comment({"Minimum tier of laser collectors.", "Default: 6 [IV]"})
        public int minLaserTier = 6;
    }
}
