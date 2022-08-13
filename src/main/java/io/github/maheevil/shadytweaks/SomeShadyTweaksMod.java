package io.github.maheevil.shadytweaks;

import io.github.maheevil.shadytweaks.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class SomeShadyTweaksMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("shadytweaks");
	//public static ModConfig config;
	public static HashMap<String, Boolean> configMap = new HashMap<>();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.BLOCK.forEach(
				block -> configMap.put(block.getName().getString(),false)
		);
		//config = AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new).get();
		LOGGER.info("Hello Modding world!");
	}
}
