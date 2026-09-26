package com.kalyptien.caelumpedion.util;

public enum BiomeRegion {
    Europe(1),
    NorthAmerica(2),
    SouthAmerica(3),
    Oceania(4),
    NorthAfrica(6),
    SouthAfrica(6),
    Asia(7);

    private final int id;

    BiomeRegion(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
