package io.github.serafimdietrich.randomeffects.world;

import io.github.serafimdietrich.randomeffects.mixin.GameRulesInvoker;
import io.github.serafimdietrich.randomeffects.mixin.IntegerValueInvoker;
import io.github.serafimdietrich.randomeffects.mixin.BooleanValueInvoker;
import net.minecraft.world.level.GameRules;

public final class ModGameRules {
    public static GameRules.Key<GameRules.BooleanValue> randomEffectOnDamage;
    public static GameRules.Key<GameRules.BooleanValue> randomEffectPerBlock;
    public static GameRules.Key<GameRules.IntegerValue> randomEffectTimer;

    public static void register() {
        randomEffectOnDamage = GameRulesInvoker.register("randomEffectOnDamage", GameRules.Category.PLAYER, BooleanValueInvoker.create(false));
        randomEffectPerBlock = GameRulesInvoker.register("randomEffectPerBlock", GameRules.Category.PLAYER, BooleanValueInvoker.create(false));
        randomEffectTimer = GameRulesInvoker.register("randomEffectTimer", GameRules.Category.PLAYER, IntegerValueInvoker.create(0, 0, Integer.MAX_VALUE, (server, value) -> {
        }));
    }
}
