package com.kalyptien.caelumpedion.util;

import com.kalyptien.caelumpedion.worldgen.CaelumpedionDensityFunctions;
import com.kalyptien.caelumpedion.worldgen.voronoiNoise.VoronoiDF;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.DensityFunction;

public class LevelUtils {

    private static DensityFunction voronoiDensityFunction;

    public static void initializeOnServerStart(MinecraftServer server)
    {
        RegistryAccess registryAccess = server.registryAccess();
        HolderGetter<DensityFunction> densityFunctions = registryAccess.lookupOrThrow(Registries.DENSITY_FUNCTION);

        long seed = server.getWorldData().worldGenOptions().seed();

        voronoiDensityFunction = CaelumpedionDensityFunctions.getFunction(densityFunctions, CaelumpedionDensityFunctions.VORONOI);

        DensityFunction.Visitor visitor = new VoronoiDF.SeededVisitor(seed);

        voronoiDensityFunction.mapAll(visitor);

    }

    public static DensityFunction getVoronoiDensityFunction() {
        return voronoiDensityFunction;
    }

    public static void setVoronoiDensityFunction(DensityFunction voronoiDensityFunction) {
        LevelUtils.voronoiDensityFunction = voronoiDensityFunction;
    }
}
