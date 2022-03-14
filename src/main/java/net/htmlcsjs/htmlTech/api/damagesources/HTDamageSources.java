package net.htmlcsjs.htmlTech.api.damagesources;

import net.minecraft.util.DamageSource;

public class HTDamageSources {
    private static final DamageSource LASER = new DamageSource("laser").setFireDamage();

    public static DamageSource getLaserDamage() {
        return LASER;
    }
}
