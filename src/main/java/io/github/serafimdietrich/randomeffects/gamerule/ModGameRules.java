package io.github.serafimdietrich.randomeffects.gamerule;

import net.minecraft.world.level.GameRules;

public final class ModGameRules {
    public static GameRules.Key<GameRules.BooleanValue> RANDOM_EFFECT_ON_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> RANDOM_EFFECT_PER_BLOCK;
    public static GameRules.Key<GameRules.IntegerValue> RANDOM_EFFECT_TIMER;

    public static void register() {
        RANDOM_EFFECT_ON_DAMAGE = GameRules.register("randomEffectOnDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
        RANDOM_EFFECT_PER_BLOCK = GameRules.register("randomEffectPerBlock", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
        RANDOM_EFFECT_TIMER = GameRules.register("randomEffectTimer", GameRules.Category.PLAYER, GameRules.IntegerValue.create(0, 0, Integer.MAX_VALUE, (server, value) -> {}));
    }
}
