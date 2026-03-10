package io.github.serafimdietrich.randomeffects.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.serafimdietrich.randomeffects.util.EffectUtils;
import io.github.serafimdietrich.randomeffects.world.ModGameRules;
import io.github.serafimdietrich.randomeffects.world.RandomEffectPerBlockSavedData;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntityMixin {
    @Nullable
    @Unique
    private MobEffectInstance mobEffectInstance;

    @Inject(method = "actuallyHurt", at = @At("TAIL"))
    private void actuallyHurt(DamageSource damageSource, float damage, CallbackInfo ci) {
        if (this.level().getGameRules().getBoolean(ModGameRules.randomEffectOnDamage) && !damageSource.is(DamageTypes.MAGIC)) {
            this.forceAddEffect(
                    new MobEffectInstance(
                            EffectUtils.getRandom(this.getRandom()),
                            this.level().getGameRules().getInt(ModGameRules.randomEffectOnDamageDuration)
                    ), null
            );
        }
    }

    @Override
    protected void triggerOnDeathMobEffects(Entity.RemovalReason removalReason, Operation<Void> original) {
        if (this.isEffectActive()) {
            this.mobEffectInstance.onMobRemoved((Player) (Object) this, removalReason);
        }

        super.triggerOnDeathMobEffects(removalReason, original);
    }

    @Override
    protected void tickEffects(Operation<Void> original) {
        BlockState state = this.getBlockStateOn();
        MinecraftServer server = this.getServer();

        if (!state.isAir() && this.level().getGameRules().getBoolean(ModGameRules.randomEffectPerBlock) && server != null) {
            Holder<MobEffect> holder = RandomEffectPerBlockSavedData.getSavedData(server)
                    .getOrSetRandom(BuiltInRegistries.BLOCK.getKey(state.getBlock()), this.getRandom());

            if (this.mobEffectInstance == null || !EffectUtils.holderIsMobEffectInstance(holder, this.mobEffectInstance)) {
                this.removeEffect();
                this.addEffect(holder);
            }

            if (this.mobEffectInstance != null && !this.hasEffectNative(holder)) {
                this.mobEffectInstance.tick((Player) (Object) this, () -> this.onEffectUpdated(this.mobEffectInstance, true, null));
            }
        } else {
            this.removeEffect();
        }

        super.tickEffects(original);
    }

    @Override
    protected void updateInvisibilityStatus(Operation<Void> original) {
        if (this.isEffectActive() && EffectUtils.holderIsMobEffectInstance(MobEffects.INVISIBILITY, this.mobEffectInstance)) {
            this.setInvisible(true);
            this.invokeUpdateSynchronizedMobEffectParticles();
            return;
        }

        super.updateInvisibilityStatus(original);
    }

    @Override
    public Collection<MobEffectInstance> getActiveEffects(Operation<Collection<MobEffectInstance>> original) {
        if (!this.isEffectActive()) {
            return super.getActiveEffects(original);
        }

        Collection<MobEffectInstance> activeEffects = new ArrayList<>(super.getActiveEffects(original));
        activeEffects.add(this.mobEffectInstance);
        return activeEffects;
    }

    @Override
    public Map<Holder<MobEffect>, MobEffectInstance> getActiveEffectsMap(Operation<Map<Holder<MobEffect>, MobEffectInstance>> original) {
        if (!this.isEffectActive()) {
            return super.getActiveEffectsMap(original);
        }

        Map<Holder<MobEffect>, MobEffectInstance> activeEffects = new HashMap<>(super.getActiveEffectsMap(original));
        activeEffects.put(this.mobEffectInstance.getEffect(), this.mobEffectInstance);
        return activeEffects;
    }

    @Override
    public boolean hasEffect(Holder<MobEffect> holder, Operation<Boolean> original) {
        return (this.isEffectActive() && EffectUtils.holderIsMobEffectInstance(holder, this.mobEffectInstance)) || super.hasEffect(holder, original);
    }

    @Override
    public MobEffectInstance getEffect(Holder<MobEffect> holder, Operation<MobEffectInstance> original) {
        if (this.isEffectActive() && EffectUtils.holderIsMobEffectInstance(holder, this.mobEffectInstance)) {
            return this.mobEffectInstance;
        }

        return super.getEffect(holder, original);
    }

    @Override
    public void hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (this.isEffectActive()) {
            this.mobEffectInstance.onMobHurt((Player) (Object) this, damageSource, f);
        }

        super.hurt(damageSource, f, cir);
    }

    @Unique
    private void removeEffect() {
        if (this.mobEffectInstance != null) {
            if (!this.hasEffectNative(this.mobEffectInstance.getEffect())) {
                this.onEffectRemoved(this.mobEffectInstance);
            }

            this.mobEffectInstance = null;
        }
    }

    @Unique
    private void addEffect(@NotNull Holder<MobEffect> holder) {
        if (!this.hasEffectNative(holder)) {
            this.mobEffectInstance = new MobEffectInstance(holder, -1);
            this.onEffectAdded(this.mobEffectInstance, null);
            this.mobEffectInstance.onEffectAdded((Player) (Object) this);
            this.mobEffectInstance.onEffectStarted((Player) (Object) this);
        }
    }

    @Unique
    public boolean isEffectActive() {
        return this.mobEffectInstance != null && this.level().getGameRules().getBoolean(ModGameRules.randomEffectPerBlock) &&
                !this.hasEffectNative(this.mobEffectInstance.getEffect());
    }
}
