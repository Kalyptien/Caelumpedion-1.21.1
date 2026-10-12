package com.kalyptien.caelumpedion.worldgen.densityFunction;

import com.kalyptien.caelumpedion.worldgen.voronoiNoise.VoronoiDF;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.DensityFunction;

public class CaelumpedionDensityFunctions extends CaelumpedionDensityFunctionBuilders{
    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        context.register(VORONOI, new VoronoiDF(4206967L, true, 350.0, 0.5, 5, 1, 1));
    }
}
