package io.github.serafimdietrich.randomeffects;

import io.github.serafimdietrich.randomeffects.platform.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//? fabric {
import io.github.serafimdietrich.randomeffects.platform.fabric.FabricPlatform;
//?} neoforge {
/*import io.github.serafimdietrich.randomeffects.platform.neoforge.NeoforgePlatform;
 *///?}

@SuppressWarnings("LoggingSimilarMessage")
public class RandomEffects {
    public static final String MOD_ID = /*$ mod_id*/ "randomeffects";
    public static final String MOD_VERSION = /*$ mod_version*/ "0.1.0";
    public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Random Effects";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Platform PLATFORM = createPlatformInstance();

    public static void onInitialize() {
        LOGGER.info("Initializing {} on {}", MOD_ID, xplat().loader());
        LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
    }

    public static void onInitializeClient() {
        LOGGER.info("Initializing {} Client on {}", MOD_ID, xplat().loader());
        LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
    }

    static Platform xplat() {
        return PLATFORM;
    }

    private static Platform createPlatformInstance() {
        //? fabric {
        return new FabricPlatform();
        //?} neoforge {
        /*return new NeoforgePlatform();
         *///?}
    }
}
