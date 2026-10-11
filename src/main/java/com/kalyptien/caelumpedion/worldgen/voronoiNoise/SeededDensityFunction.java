package com.kalyptien.caelumpedion.worldgen.voronoiNoise;

import net.minecraft.world.level.levelgen.DensityFunction;

public interface SeededDensityFunction extends DensityFunction {

    boolean initialized();

    DensityFunction initialize(long levelSeed);

}
