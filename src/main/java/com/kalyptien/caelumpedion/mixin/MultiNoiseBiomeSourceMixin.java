package com.kalyptien.caelumpedion.mixin;

import com.kalyptien.caelumpedion.util.LevelUtils;
import com.kalyptien.caelumpedion.worldgen.CaelumpedionDensityFunctions;
import com.kalyptien.caelumpedion.worldgen.voronoiNoise.VoronoiDF;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin {

    @Inject(method="addDebugInfo", at =@At("TAIL"), remap = false)
    public void addDebugInfo(List<String> debugLines, BlockPos pos, Climate.Sampler sampler, CallbackInfo ci)
    {
        DensityFunction voronoi = LevelUtils.getVoronoiDensityFunction();

        double voronoiValue = voronoi.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));

        debugLines.add("Voronoi Value: " + voronoiValue);
    }
}
