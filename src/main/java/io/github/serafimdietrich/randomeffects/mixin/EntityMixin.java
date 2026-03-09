package io.github.serafimdietrich.randomeffects.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract BlockState getBlockStateOn();

    @Shadow
    public abstract void setInvisible(boolean bl);

    @Shadow
    public abstract MinecraftServer getServer();

    @Shadow
    public abstract Level level();

    @Shadow
    public abstract RandomSource getRandom();
}
