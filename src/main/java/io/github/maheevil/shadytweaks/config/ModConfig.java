package io.github.maheevil.shadytweaks.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "shadytweaks")
public class ModConfig implements ConfigData {
    public boolean dontHitVillagers = true;
    public boolean noExplodingBeds = true;
    public boolean dontHitPets = true;
}
