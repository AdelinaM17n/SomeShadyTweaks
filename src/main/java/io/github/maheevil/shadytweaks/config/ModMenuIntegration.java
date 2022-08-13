package io.github.maheevil.shadytweaks.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.maheevil.shadytweaks.SomeShadyTweaksMod;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    public Screen getConfigScreen(Screen parent){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Title"));

        //builder.setGlobalized(true);

        builder.setSavingRunnable(() -> {

        });

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        Registry.BLOCK.stream().forEach(
                block -> {
                    String blockName = block.getName().getString();
                    general.addEntry(entryBuilder.startBooleanToggle(Component.literal(blockName), SomeShadyTweaksMod.configMap.get(blockName))
                            .setDefaultValue(false) // Recommended: Used when user click "Reset"
                            //.setTooltip(Component.literal("This option is awesome!")) // Optional: Shown when the user hover over this option
                            .setSaveConsumer(newValue -> SomeShadyTweaksMod.configMap.put(blockName,newValue)) // Recommended: Called when user save the config
                            .build()); // Builds the option entry for cloth config
                }
        );
        return builder.build();
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::getConfigScreen;
    }
}
