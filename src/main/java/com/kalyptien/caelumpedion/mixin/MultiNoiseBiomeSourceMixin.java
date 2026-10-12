package com.kalyptien.caelumpedion.mixin;

import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MultiNoiseBiomeSource.class)
public abstract class MultiNoiseBiomeSourceMixin {

    @Shadow public abstract Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler);

    @Inject(method="addDebugInfo", at =@At("TAIL"), remap = false)
    public void addDebugInfo(List<String> debugLines, BlockPos pos, Climate.Sampler sampler, CallbackInfo ci)
    {
        int i = QuartPos.fromBlock(pos.getX());
        int j = QuartPos.fromBlock(pos.getY());
        int k = QuartPos.fromBlock(pos.getZ());

        DensityFunction voronoi = LevelUtils.getVoronoiDensityFunction();

        double voronoiValue = voronoi.compute(new DensityFunction.SinglePointContext(i,j,k));

        String IrlRegionKey = IRLRegion.byID((int)voronoiValue).getName();

        debugLines.add("IRL Region: " + Component.translatable(IrlRegionKey).getString() + " (" + voronoiValue + ")");
    }
}
