package io.github.serafimdietrich.randomeffects.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;

@Mixin(GameRules.IntegerValue.class)
public interface IntegerValueInvoker {
    @Invoker("create")
    static GameRules.Type<GameRules.IntegerValue> create(int i, int j, int k, BiConsumer<MinecraftServer, GameRules.IntegerValue> biConsumer) {
        throw new AssertionError();
    }
}
