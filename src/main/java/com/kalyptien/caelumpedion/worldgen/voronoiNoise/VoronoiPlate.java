package com.kalyptien.caelumpedion.worldgen.voronoiNoise;

import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.phys.Vec3;

import static com.kalyptien.caelumpedion.worldgen.voronoiNoise.VoronoiNoise.RandVecs;

public class VoronoiPlate {

    private final int hash;
    private final Vec3i index;
    private final Vec3 center;
    private final double value;

    public VoronoiPlate(long seed, Vec3i index, double jitter) {

        this.index = index;

        int xPrime = index.getX() * 501125321;
        int yPrime = index.getY() * 1136930381;
        int zPrime = index.getZ() * 1720413743;

        this.hash = (int) ((seed ^ xPrime ^ zPrime ^ yPrime) * 0x27d4eb2d);

        this.value = this.hash * (1 / 2147493648.0f);

        int id = hash & 0x1FE;

        this.center = new Vec3(
                index.getX() + RandVecs[id] * jitter,
                index.getY() + RandVecs[id | 1] * jitter,
                index.getZ() + RandVecs[id | 2] * jitter
        );
    }

    public double getValue() {return this.value;}

    public Vec3i getIndex() {return this.index;}

    public Vec3 getCenter() {return this.center;}

    public DensityFunction.FunctionContext getCenterPos(double scale) {
        return new DensityFunction.FunctionContext() {

            final int x = (int) (center.x() * scale);
            final int y = (int) (center.y() * scale);
            final int z = (int) (center.z() * scale);

            @Override
            public int blockX() {return this.x;}
            @Override
            public int blockY() {return this.y;}
            @Override
            public int blockZ() {return this.z;}

        };

    }

    public DensityFunction.FunctionContext getCenterPos(double scale, boolean flat) {
        if (flat) return new DensityFunction.FunctionContext() {

            final int x = (int) (center.x() * scale);
            final int y = 0;
            final int z = (int) (center.z() * scale);

            @Override
            public int blockX() {return this.x;}
            @Override
            public int blockY() {return this.y;}
            @Override
            public int blockZ() {return this.z;}

        };
        else return getCenterPos(scale);

    }

    public Vec3i velocity() {return new Vec3i(hash % 3 - 2, 0, ((hash % 9) - (hash % 3)) / 3 - 2);}

    public int relativeVelocity(VoronoiPlate otherPlate) {
        Vec3i v0 = this.velocity();
        Vec3i v1 = otherPlate.velocity();
        return ((this.index.getX() - otherPlate.getIndex().getX()) * (v0.getX() - v1.getX())) - ((this.index.getZ() - otherPlate.getIndex().getZ()) * (v0.getZ() - v1.getZ()));
    }

    public double getDist(Vec3 point, boolean flat, int metric) {
        double dx = Math.abs(this.center.x - point.x);
        double dy = Math.abs(flat ? 0 : this.center.y - point.y);
        double dz = Math.abs(this.center.z - point.z);

        return switch (metric) {
            case 0 -> dx + dy + dz;
            default -> Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
            case 2 -> Math.max(dx, Math.max(dy, dz));
            case 3 -> ((dx + dy + dz) + (Math.sqrt((dx * dx) + (dy * dy) + (dz * dz)))) * 0.5;
            case 4 -> ((dx + dy + dz) + (Math.max(dx, Math.max(dy, dz)))) * 0.5;
            case 5 -> ((Math.sqrt((dx * dx) + (dy * dy) + (dz * dz))) + (Math.max(dx, Math.max(dy, dz)))) * 0.5;
        };

    }




}
