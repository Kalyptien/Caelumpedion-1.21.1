package com.kalyptien.caelumpedion.worldgen.voronoiNoise;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import javax.annotation.Nullable;

public class VoronoiDF implements SeededDensityFunction {

    private static final MapCodec<VoronoiDF> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
                    Codec.LONG.optionalFieldOf("salt", 0L).forGetter((input) -> input.salt),
                    Codec.BOOL.optionalFieldOf("flat", true).forGetter((input) -> input.flat),
                    Codec.DOUBLE.fieldOf("scale").forGetter((input) -> input.scale),
                    Codec.doubleRange(0.0,0.5).optionalFieldOf("jitter", 0.4).forGetter((input) -> input.jitter),
                    Codec.intRange(0,5).optionalFieldOf("metric", 1).forGetter((input) -> input.metric),
                    Codec.intRange(0,6).optionalFieldOf("mode", 0).forGetter((input) -> input.mode),
                    Codec.intRange(1,9).optionalFieldOf("ordinal", 1).forGetter((input) -> input.ordinal)
            ).apply(instance, VoronoiDF::new)
    );

    public static final KeyDispatchDataCodec<VoronoiDF> CODEC = KeyDispatchDataCodec.of(MAP_CODEC);

    @Nullable
    public VoronoiNoise noise = null;
    private final long salt;
    private final boolean flat;
    private final double scale;
    private final double jitter;
    private final int metric;
    private final int mode;
    private final int ordinal;

    public VoronoiDF(long salt, boolean flat, double scale, double jitter, int metric, int mode, int ordinal) {
        this.salt = salt;
        this.flat = flat;
        this.scale = scale;
        this.jitter = jitter;
        this.metric = metric;
        this.mode = mode; //0 distance; 1 value; 2 relative velocity; 3 passive margin; 4 direction of center; 5 direction of boundary; 6 relative distance
        this.ordinal = ordinal;
    }

    @Override
    public double compute(FunctionContext pos) {
        if (this.noise == null) {
            throw new NullPointerException("VoronoiDF not initialized");
        } else return this.mode == 0 || this.mode == 6 ? this.noise.getDistance(pos, this.flat, this.scale, this.jitter, this.metric, this.ordinal, this.mode == 6) : this.noise.getVoronoi(pos, this.flat, this.scale, this.jitter, this.metric, this.mode, this.ordinal);
    }

    @Override
    public void fillArray(double[] doubles, ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(doubles, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return visitor.apply(this);
    }

    @Override
    public double minValue() {
        return Double.MIN_VALUE;
    }

    @Override
    public double maxValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    @Override
    public VoronoiDF initialize(long levelSeed) {
        this.noise = VoronoiNoise.create(levelSeed + this.salt);
        return this;
    }

    public boolean initialized() {
        return this.noise != null;
    }

    //Visitor

    public record SeededVisitor(long levelSeed) implements DensityFunction.Visitor {
        @Override
        public DensityFunction apply(DensityFunction function) {
            return function instanceof SeededDensityFunction seededDF ?
                    seededDF.initialized() ?
                            seededDF
                            : seededDF.initialize(levelSeed)
                    : function;
        }

        public long getSeed() {
            return levelSeed;
        }
    }
}
