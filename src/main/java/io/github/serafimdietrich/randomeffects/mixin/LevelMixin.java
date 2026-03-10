package io.github.serafimdietrich.randomeffects.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Shadow
    @Final
    public RandomSource random;

    @Shadow
    public abstract GameRules getGameRules();
}
