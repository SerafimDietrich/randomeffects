package io.github.serafimdietrich.randomeffects.platform.fabric;

        //? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import io.github.serafimdietrich.randomeffects.RandomEffects;
import net.fabricmc.api.ModInitializer;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        RandomEffects.onInitialize();
        FabricEventSubscriber.registerEvents();
    }
}
        //?}
