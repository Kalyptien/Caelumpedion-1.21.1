package com.kalyptien.caelumpedion.util;

import com.kalyptien.caelumpedion.worldgen.densityFunction.CaelumpedionDensityFunctions;
import com.kalyptien.caelumpedion.worldgen.voronoiNoise.VoronoiDF;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;

public class VoronoiLevel {

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
        VoronoiLevel.voronoiDensityFunction = voronoiDensityFunction;
    }

    public static double getVoronoiValue(BlockPos pos){
        int i = QuartPos.fromBlock(pos.getX());
        int j = QuartPos.fromBlock(pos.getY());
        int k = QuartPos.fromBlock(pos.getZ());

        DensityFunction voronoi = VoronoiLevel.getVoronoiDensityFunction();

        return voronoi.compute(new DensityFunction.SinglePointContext(i,j,k));
    }

    public static IRLRegion getVoronoiIrlRegion(Holder<Biome> holder, BlockPos pos){

        IRLRegion[] specificRange = null;

        if(holder.is(BiomeTags.IS_JUNGLE)){
            specificRange = new IRLRegion[]{IRLRegion.Asia, IRLRegion.SouthAmerica, IRLRegion.SouthAfrica};
        }
        else if(holder.is(BiomeTags.IS_BADLANDS)){
            specificRange = new IRLRegion[]{IRLRegion.NorthAmerica, IRLRegion.Oceania};
        }
        else if(holder.is(CaelumpedionTags.Biomes.IS_HOT)){
            specificRange = new IRLRegion[]{IRLRegion.NorthAfrica, IRLRegion.SouthAfrica, IRLRegion.Oceania, IRLRegion.Asia};
        }
        else if(holder.is(CaelumpedionTags.Biomes.IS_COLD)){
            specificRange = new IRLRegion[]{IRLRegion.NorthAfrica, IRLRegion.Europe, IRLRegion.Asia};
        }
        else if(holder.is(CaelumpedionTags.Biomes.IS_ASIAN)){
            specificRange = new IRLRegion[]{IRLRegion.Asia};
        }
        else if(holder.is(BiomeTags.IS_SAVANNA)){
            specificRange = new IRLRegion[]{IRLRegion.NorthAfrica, IRLRegion.SouthAfrica, IRLRegion.Oceania};
        }

        if(specificRange == null){
            return IRLRegion.byID((int)getVoronoiValue(pos));
        }
        else{
            return IRLRegion.bySpecificRangeID(specificRange, (int)getVoronoiValue(pos));
        }
    }
}
