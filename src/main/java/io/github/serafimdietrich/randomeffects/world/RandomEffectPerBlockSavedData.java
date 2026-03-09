package io.github.serafimdietrich.randomeffects.world;

import io.github.serafimdietrich.randomeffects.util.EffectUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RandomEffectPerBlockSavedData extends SavedData {
    private final Map<ResourceLocation, Holder<MobEffect>> blockEffectMap = new HashMap<>();

    public static RandomEffectPerBlockSavedData getSavedData(@NotNull MinecraftServer server) {
        RandomEffectPerBlockSavedData savedData = server.overworld().getDataStorage().get(factory(), "blockEffectsMap");

        if (savedData == null) {
            server.overworld().getDataStorage().set("blockEffectsMap", savedData = new RandomEffectPerBlockSavedData());
        }

        return savedData;
    }

    public static SavedData.Factory<RandomEffectPerBlockSavedData> factory() {
        return new SavedData.Factory<>(RandomEffectPerBlockSavedData::new, RandomEffectPerBlockSavedData::load, null);
    }

    private RandomEffectPerBlockSavedData(Map<ResourceLocation, Holder<MobEffect>> blockEffectMap) {
        super();
        this.blockEffectMap.putAll(blockEffectMap);
    }

    private RandomEffectPerBlockSavedData() {
        super();
    }

    public static RandomEffectPerBlockSavedData load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        RandomEffectPerBlockSavedData savedData = new RandomEffectPerBlockSavedData();
        ListTag listTag = compoundTag.getList("block_effects", 10);

        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag entryTag = listTag.getCompound(i);
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse(entryTag.getString("effect")));

            if (effect != null) {
                savedData.blockEffectMap.put(ResourceLocation.parse(entryTag.getString("block")), BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect));
            }
        }

        return savedData;
    }

    @NotNull
    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ListTag listTag = new ListTag();

        for (Map.Entry<ResourceLocation, Holder<MobEffect>> entry : this.blockEffectMap.entrySet()) {
            ResourceLocation effect = BuiltInRegistries.MOB_EFFECT.getKey(entry.getValue().value());

            if (effect != null) {
                CompoundTag entryTag = new CompoundTag();
                entryTag.putString("block", entry.getKey().toString());
                entryTag.putString("effect", effect.toString());
                listTag.add(entryTag);
            }
        }

        compoundTag.put("block_effects", listTag);
        return compoundTag;
    }

    public Holder<MobEffect> getOrSetRandom(ResourceLocation block, RandomSource random) {
        Holder<MobEffect> mobEffectInstance = this.blockEffectMap.get(block);

        if (mobEffectInstance == null) {
            mobEffectInstance = EffectUtils.getRandom(random);
            this.blockEffectMap.put(block, mobEffectInstance);
            this.setDirty();
        }

        return mobEffectInstance;
    }
}
