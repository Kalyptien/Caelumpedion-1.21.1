package com.kalyptien.caelumpedion.util;

import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.PiciformeEntity;
import net.minecraft.world.level.biome.Biome;

import java.util.Arrays;
import java.util.Comparator;

public enum IRLRegion {
    Europe(1, "irlregion.caelumpedion.europe.name"),
    NorthAmerica(2, "irlregion.caelumpedion.northamerica.name"),
    SouthAmerica(3, "irlregion.caelumpedion.southamerica.name"),
    Oceania(4, "irlregion.caelumpedion.oceania.name"),
    NorthAfrica(5, "irlregion.caelumpedion.northafrica.name"),
    SouthAfrica(6, "irlregion.caelumpedion.southafrica.name"),
    Asia(7, "irlregion.caelumpedion.asia.name");

    private final int id;
    private final String name;

    private static final IRLRegion[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(IRLRegion::getId)).toArray(IRLRegion[]::new);

    IRLRegion(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public static IRLRegion byID(int id){
        return BY_ID[id % BY_ID.length];
    }
}
