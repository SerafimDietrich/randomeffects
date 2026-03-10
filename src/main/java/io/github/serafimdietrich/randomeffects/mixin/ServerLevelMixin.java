package io.github.serafimdietrich.randomeffects.mixin;

import io.github.serafimdietrich.randomeffects.util.EffectUtils;
import io.github.serafimdietrich.randomeffects.world.ModGameRules;
import io.github.serafimdietrich.randomeffects.world.ModObjectiveCriteria;
import net.minecraft.core.Holder;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends LevelMixin {
    @Unique
    private int randomEffectTimer = 0;

    @Shadow
    @Final
    List<ServerPlayer> players;

    @Shadow
    public abstract ServerScoreboard getScoreboard();

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        int randomEffectTimerThreshold = this.getGameRules().getInt(ModGameRules.randomEffectTimer);

        if (randomEffectTimerThreshold != 0) {
            if (this.randomEffectTimer % randomEffectTimerThreshold == 0) {
                this.randomEffectTimer = 0;

                if (this.getGameRules().getBoolean(ModGameRules.randomEffectTimerSameEffect)) {
                    Holder<MobEffect> mobEffectHolder = EffectUtils.getRandom(this.random);
                    this.players.forEach(player -> player.forceAddEffect(new MobEffectInstance(mobEffectHolder, randomEffectTimerThreshold + 1), null));
                } else {
                    this.players.forEach(player -> player.forceAddEffect(
                            new MobEffectInstance(EffectUtils.getRandom(this.random), randomEffectTimerThreshold + 1),
                            null
                    ));
                }
            }

            this.randomEffectTimer++;
            this.players.forEach(player -> this.getScoreboard()
                    .forAllObjectives(ModObjectiveCriteria.randomEffectTimer, player, scoreAccess -> scoreAccess.set(this.randomEffectTimer)));
        }
    }
}
