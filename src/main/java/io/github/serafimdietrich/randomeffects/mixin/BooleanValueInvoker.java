package io.github.serafimdietrich.randomeffects.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.BooleanValue.class)
public interface BooleanValueInvoker {
    @Invoker("create")
    static GameRules.Type<GameRules.BooleanValue> create(boolean bl) {
        throw new AssertionError();
    }
}
