package io.github.serafimdietrich.randomeffects.platform.fabric;

        //? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import io.github.serafimdietrich.randomeffects.RandomEffects;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RandomEffects.onInitializeClient();
        FabricClientEventSubscriber.registerEvents();
    }
}
        //?}
