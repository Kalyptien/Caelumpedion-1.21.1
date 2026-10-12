package com.kalyptien.caelumpedion.worldgen.densityFunction;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.worldgen.voronoiNoise.VoronoiDF;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CaelumpedionDensityFunctionTypes {

    public static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPE =
            DeferredRegister.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE, CaelumpedionMod.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<VoronoiDF>>
            VORONOI = DENSITY_FUNCTION_TYPE.register("voronoi", VoronoiDF.CODEC::codec);

    public static void register(IEventBus eventBus) {
        DENSITY_FUNCTION_TYPE.register(eventBus);
    }

}
