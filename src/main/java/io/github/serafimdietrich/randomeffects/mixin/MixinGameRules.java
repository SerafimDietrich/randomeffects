package io.github.serafimdietrich.randomeffects.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GameRules.class)
public abstract class MixinGameRules {
    @Unique
    private static final GameRules.Key<GameRules.BooleanValue> RULE_RANDOM_EFFECT_ON_DAMAGE = GameRules.register("randomEffectOnDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
    @Unique
    private static final GameRules.Key<GameRules.BooleanValue> RULE_RANDOM_EFFECT_PER_BLOCK = GameRules.register("randomEffectPerBlock", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
    @Unique
    private static final GameRules.Key<GameRules.IntegerValue> RULE_RANDOM_EFFECT_TIMER = GameRules.register("randomEffectTimer", GameRules.Category.PLAYER, GameRules.IntegerValue.create(0, 0, Integer.MAX_VALUE, (server, i) -> {}));
}
