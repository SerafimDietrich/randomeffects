package io.github.serafimdietrich.randomeffects.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public final class EffectUtils {
    public static Holder<MobEffect> getRandom(RandomSource random) {
        return BuiltInRegistries.MOB_EFFECT.getRandom(random).map(reference -> (Holder<MobEffect>) reference).orElse(MobEffects.GLOWING);
    }

    public static boolean holderIsMobEffectInstance(Holder<MobEffect> holder, MobEffectInstance mobEffectInstance) {
        return holder.is(mobEffectInstance.getEffect().unwrapKey().orElse(null));
    }
}
