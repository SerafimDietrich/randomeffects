package io.github.serafimdietrich.randomeffects.world;

import io.github.serafimdietrich.randomeffects.mixin.ObjectiveCriteriaInvoker;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ModObjectiveCriteria {
    public static ObjectiveCriteria randomEffectTimer;

    public static void register() {
        randomEffectTimer = ObjectiveCriteriaInvoker.registerCustom("randomEffectTimer", true, ObjectiveCriteria.RenderType.INTEGER);
    }
}
