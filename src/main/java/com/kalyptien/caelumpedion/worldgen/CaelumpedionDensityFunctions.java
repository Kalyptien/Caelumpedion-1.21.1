package com.kalyptien.caelumpedion.worldgen;

import com.kalyptien.caelumpedion.worldgen.voronoiNoise.VoronoiDF;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.DensityFunction;

public class CaelumpedionDensityFunctions extends CaelumpedionDensityFunctionBuilders{
    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        context.register(VORONOI, new VoronoiDF(0, true, 100, 0.4, 1, 1, 1));
    }
}
