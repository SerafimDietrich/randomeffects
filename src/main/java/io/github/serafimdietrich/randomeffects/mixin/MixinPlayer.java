package io.github.serafimdietrich.randomeffects.mixin;

import io.github.serafimdietrich.randomeffects.gamerule.ModGameRules;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinPlayer {

    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource damageSource);

    @Inject(method = "actuallyHurt", at = @At("TAIL"))
    private void actuallyHurt(DamageSource damageSource, float damage, CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (player.level().getGameRules().getBoolean(ModGameRules.RANDOM_EFFECT_ON_DAMAGE) && !damageSource.is(DamageTypes.MAGIC)) {
            player.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.getRandom(player.getRandom()).get(), -1));
        }
    }
}
