package com.kalyptien.caelumpedion.worldgen.densityFunction;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

public class CaelumpedionDensityFunctionBuilders {
    public static final ResourceKey<DensityFunction> VORONOI = createKey("caelumpedion/voronoi");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, name));
    }

    public static DensityFunction getFunction(HolderGetter<DensityFunction> function, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(function.getOrThrow(key));
    }
}
