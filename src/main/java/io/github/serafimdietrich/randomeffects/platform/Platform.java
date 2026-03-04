package io.github.serafimdietrich.randomeffects.platform;

public interface Platform {
    boolean isModLoaded(String modId);

    ModLoader loader();

    String mcVersion();

    boolean isDevelopmentEnvironment();

    default boolean isDebug() {
        return this.isDevelopmentEnvironment();
    }

    enum ModLoader {
        FABRIC,
        NEOFORGE,
        FORGE,
        QUILT
    }
}
