package com.kalyptien.caelumpedion.worldgen;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.CaelumpedionEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class CaelumpedionBiomeModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_PASSERIFORME = registerKey("spawn_passeriforme");
    public static final ResourceKey<BiomeModifier> SPAWN_HIRUNDINIAE = registerKey("spawn_hirundinia");
    public static final ResourceKey<BiomeModifier> SPAWN_CORVIDAE = registerKey("spawn_corvidae");

    public static final ResourceKey<BiomeModifier> SPAWN_ANSERIFORME = registerKey("spawn_anseriforme");

    public static final ResourceKey<BiomeModifier> SPAWN_ACCIPITRIFORME = registerKey("spawn_accipitriforme");

    public static final ResourceKey<BiomeModifier> SPAWN_PICIFORME = registerKey("spawn_piciforme");
    public static final ResourceKey<BiomeModifier> SPAWN_RAMPHASTIDAE = registerKey("spawn_ramphastidae");

    public static final ResourceKey<BiomeModifier> SPAWN_GRUIFORME = registerKey("spawn_gruiforme");

    public static final ResourceKey<BiomeModifier> SPAWN_TROCHILIDAE = registerKey("spawn_trochilidae");

    public static final ResourceKey<BiomeModifier> SPAWN_COLUMBIFORME = registerKey("spawn_columbiforme");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        HolderSet<Biome> allBiomes = HolderSet.direct(
                biomes.getOrThrow(Biomes.FOREST),
                biomes.getOrThrow(Biomes.FLOWER_FOREST),
                biomes.getOrThrow(Biomes.DARK_FOREST),
                biomes.getOrThrow(Biomes.BIRCH_FOREST),
                biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST),
                biomes.getOrThrow(Biomes.TAIGA),
                biomes.getOrThrow(Biomes.DESERT),
                biomes.getOrThrow(Biomes.MANGROVE_SWAMP),
                biomes.getOrThrow(Biomes.SWAMP),
                biomes.getOrThrow(Biomes.WINDSWEPT_SAVANNA),
                biomes.getOrThrow(Biomes.SAVANNA_PLATEAU),
                biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                biomes.getOrThrow(Biomes.SPARSE_JUNGLE),
                biomes.getOrThrow(Biomes.BAMBOO_JUNGLE),
                biomes.getOrThrow(Biomes.SAVANNA),
                biomes.getOrThrow(Biomes.SNOWY_BEACH),
                biomes.getOrThrow(Biomes.SNOWY_PLAINS),
                biomes.getOrThrow(Biomes.SNOWY_SLOPES),
                biomes.getOrThrow(Biomes.SNOWY_TAIGA),
                biomes.getOrThrow(Biomes.JUNGLE),
                biomes.getOrThrow(Biomes.BEACH),
                biomes.getOrThrow(Biomes.BADLANDS),
                biomes.getOrThrow(Biomes.ERODED_BADLANDS),
                biomes.getOrThrow(Biomes.WOODED_BADLANDS),
                biomes.getOrThrow(Biomes.ICE_SPIKES),
                biomes.getOrThrow(Biomes.JAGGED_PEAKS),
                biomes.getOrThrow(Biomes.RIVER),
                biomes.getOrThrow(Biomes.STONY_PEAKS),
                biomes.getOrThrow(Biomes.STONY_SHORE),
                biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS),
                biomes.getOrThrow(Biomes.FROZEN_RIVER),
                biomes.getOrThrow(Biomes.CHERRY_GROVE));

        context.register(SPAWN_PASSERIFORME, new BiomeModifiers.AddSpawnsBiomeModifier(
                allBiomes, List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.PASSERIFORME.get(), 10, 2, 6))));
        context.register(SPAWN_HIRUNDINIAE, new BiomeModifiers.AddSpawnsBiomeModifier(
                allBiomes, List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.HIRUNDININAE.get(), 5, 2, 6))));
        context.register(SPAWN_CORVIDAE, new BiomeModifiers.AddSpawnsBiomeModifier(
                allBiomes, List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.CORVIDAE.get(), 5, 2, 6))));

        context.register(SPAWN_ANSERIFORME, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.RIVER),
                        biomes.getOrThrow(Biomes.FROZEN_RIVER),
                        biomes.getOrThrow(Biomes.SWAMP),
                        biomes.getOrThrow(Biomes.MANGROVE_SWAMP),
                        biomes.getOrThrow(Biomes.PLAINS),
                        biomes.getOrThrow(Biomes.SNOWY_PLAINS)
                ), List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.ANSERIFORME.get(), 5, 2, 6))));

        context.register(SPAWN_ACCIPITRIFORME, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                        biomes.getOrThrow(Biomes.WINDSWEPT_HILLS),
                        biomes.getOrThrow(Biomes.WINDSWEPT_SAVANNA),
                        biomes.getOrThrow(Biomes.WINDSWEPT_FOREST),
                        biomes.getOrThrow(Biomes.DESERT),
                        biomes.getOrThrow(Biomes.BADLANDS),
                        biomes.getOrThrow(Biomes.WOODED_BADLANDS),
                        biomes.getOrThrow(Biomes.ERODED_BADLANDS),
                        biomes.getOrThrow(Biomes.SAVANNA_PLATEAU)
                ), List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.ACCIPITRIFORME.get(), 5, 1, 2))));

        context.register(SPAWN_PICIFORME, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.FOREST),
                        biomes.getOrThrow(Biomes.FLOWER_FOREST),
                        biomes.getOrThrow(Biomes.DARK_FOREST),
                        biomes.getOrThrow(Biomes.BIRCH_FOREST),
                        biomes.getOrThrow(Biomes.WINDSWEPT_FOREST),
                        biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST),
                        biomes.getOrThrow(Biomes.TAIGA),
                        biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                        biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA),
                        biomes.getOrThrow(Biomes.SNOWY_TAIGA),
                        biomes.getOrThrow(Biomes.DRIPSTONE_CAVES)
                ), List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.PICIFORME.get(), 5, 1, 2))));

        context.register(SPAWN_RAMPHASTIDAE, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.JUNGLE),
                        biomes.getOrThrow(Biomes.BAMBOO_JUNGLE),
                        biomes.getOrThrow(Biomes.SPARSE_JUNGLE)
                ), List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.RAMPHASTIDAE.get(), 5, 1, 3))));

        context.register(SPAWN_GRUIFORME, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.RIVER),
                        biomes.getOrThrow(Biomes.FROZEN_RIVER),
                        biomes.getOrThrow(Biomes.SWAMP),
                        biomes.getOrThrow(Biomes.MANGROVE_SWAMP),
                        biomes.getOrThrow(Biomes.PLAINS),
                        biomes.getOrThrow(Biomes.SNOWY_PLAINS)
                ), List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.GRUIFORME.get(), 5, 1, 4))));

        context.register(SPAWN_TROCHILIDAE, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.FLOWER_FOREST),
                        biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS),
                        biomes.getOrThrow(Biomes.LUSH_CAVES)
                ), List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.TROCHILIDAE.get(), 5, 1, 4))));

        context.register(SPAWN_COLUMBIFORME, new BiomeModifiers.AddSpawnsBiomeModifier(
                allBiomes, List.of(new MobSpawnSettings.SpawnerData(CaelumpedionEntities.COLUMBIFORME.get(), 5, 1, 4))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, name));
    }
}
