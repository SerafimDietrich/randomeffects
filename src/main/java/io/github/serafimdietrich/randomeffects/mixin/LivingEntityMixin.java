package io.github.serafimdietrich.randomeffects.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements LivingEntityInvoker {
    @Shadow
    @Final
    private Map<Holder<MobEffect>, MobEffectInstance> activeEffects;

    @Shadow
    public abstract void forceAddEffect(MobEffectInstance mobEffectInstance, @Nullable Entity entity);

    @Shadow
    protected abstract void onEffectAdded(MobEffectInstance mobEffectInstance, @Nullable Entity entity);

    @Shadow
    protected abstract void onEffectUpdated(MobEffectInstance mobEffectInstance, boolean bl, @Nullable Entity entity);

    @Shadow
    protected abstract void onEffectRemoved(MobEffectInstance mobEffectInstance);

    @WrapMethod(method = "triggerOnDeathMobEffects")
    protected void triggerOnDeathMobEffects(Entity.RemovalReason removalReason, Operation<Void> original) {
        original.call(removalReason);
    }

    @WrapMethod(method = "tickEffects")
    protected void tickEffects(Operation<Void> original) {
        original.call();
    }

    @WrapMethod(method = "updateInvisibilityStatus")
    protected void updateInvisibilityStatus(Operation<Void> original) {
        original.call();
    }

    @WrapMethod(method = "getActiveEffects")
    public Collection<MobEffectInstance> getActiveEffects(Operation<Collection<MobEffectInstance>> original) {
        return original.call();
    }

    @WrapMethod(method = "getActiveEffectsMap")
    public Map<Holder<MobEffect>, MobEffectInstance> getActiveEffectsMap(Operation<Map<Holder<MobEffect>, MobEffectInstance>> original) {
        return original.call();
    }

    @WrapMethod(method = "hasEffect")
    public boolean hasEffect(Holder<MobEffect> holder, Operation<Boolean> original) {
        return original.call(holder);
    }

    @WrapMethod(method = "getEffect")
    public MobEffectInstance getEffect(Holder<MobEffect> holder, Operation<MobEffectInstance> original) {
        return original.call(holder);
    }

    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.getGameTime ()J", shift = At.Shift.AFTER))
    public void hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
    }

    @Unique
    public boolean hasEffectNative(Holder<MobEffect> holder) {
        return this.activeEffects.containsKey(holder);
    }
}
