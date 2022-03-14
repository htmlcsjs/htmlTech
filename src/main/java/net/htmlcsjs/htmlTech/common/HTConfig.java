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
        @Config.Comment({"Minimum tier of laser collectors.", "Default: 5 [IV]"})
        public int minLaserTier = 5;

        @Config.Comment({"Amount of damage to do to an entity when it is hit by the laser gun", "Default: 10 (5 hearts)"})
        public float laserGunDamage = 10.0F;
    }
}
