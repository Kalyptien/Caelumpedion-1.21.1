package com.kalyptien.caelumpedion.entity.custom.bird.passeriforme;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.kalyptien.caelumpedion.util.BiomeRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PasseriformeEntity extends SocialFlyingBirdEntity {

    public PasseriformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.SHORT_FlYER);
        this.setAquaticBirdType(AquaticBirdType.NONE);
        this.setBOIDBirdType(BOIDType.FOLLOW);
        this.setFlyPathType(FlyPathType.CHAOS);

        this.flyRange = 100;
        this.flyHeight = 30;

        this.maxSchoolSize = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6d)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FLYING_SPEED, 4.0D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    //Getter / Setter

    public int getIdVariant() {
        return this.entityData.get(VARIANT);
    }

    public PasseriformeVariant getVariant() {
        return PasseriformeVariant.byId(this.getIdVariant());
    }

    public void setVariant(PasseriformeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if(spawnType == MobSpawnType.SPAWN_EGG){
            PasseriformeVariant variant = Util.getRandom(PasseriformeVariant.values(), this.random);
            this.setVariant(variant);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Variant

    public enum PasseriformeVariant implements BirdVariant {
        CardinalisCardinalis(0, "cardinalis_cardinalis", 1.0f, false, new FeatherColor[]{FeatherColor.Red}, FeatherColor.Brown, new BiomeRegion[]{BiomeRegion.NorthAmerica}),
        CyanistesCaeruleus(1, "cyanistes_caeruleus", 1.0f, false, new FeatherColor[]{FeatherColor.LightBlue, FeatherColor.Yellow}, FeatherColor.Yellow, new BiomeRegion[]{BiomeRegion.Europe}),
        CyanocittaCristata(2, "cyanocitta_cristata", 1.0f, false, new FeatherColor[]{FeatherColor.Cyan, FeatherColor.White}, FeatherColor.Blue, new BiomeRegion[]{BiomeRegion.NorthAmerica}),
        ErithacusRubecula(3, "erithacus_rubecula", 1.0f, false, new FeatherColor[]{FeatherColor.Orange, FeatherColor.Gray, FeatherColor.Brown}, FeatherColor.Brown, new BiomeRegion[]{BiomeRegion.Europe}),
        LophophanesCristatus(4, "lophophanes_cristatus", 1.0f, false, new FeatherColor[]{FeatherColor.White, FeatherColor.Brown}, FeatherColor.Brown, new BiomeRegion[]{BiomeRegion.Europe}),
        PasserDomesticus(5, "passer_domesticus", 1.0f, false, new FeatherColor[]{FeatherColor.Gray, FeatherColor.Brown}, FeatherColor.Gray, new BiomeRegion[]{BiomeRegion.Europe, BiomeRegion.NorthAmerica, BiomeRegion.SouthAmerica, BiomeRegion.SouthAfrica, BiomeRegion.Oceania, BiomeRegion.Asia}),
        PeriparusAter(6, "periparus_ater", 1.0f, false, new FeatherColor[]{FeatherColor.Black, FeatherColor.White}, FeatherColor.Black, new BiomeRegion[]{BiomeRegion.Europe, BiomeRegion.Asia}),
        PhoenicurusOchruros(7, "phoenicurus_ochruros", 1.0f, false, new FeatherColor[]{FeatherColor.Black}, FeatherColor.Gray, new BiomeRegion[]{BiomeRegion.Europe, BiomeRegion.NorthAfrica, BiomeRegion.Asia}),
        ;

        private static final PasseriformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(PasseriformeVariant::getId)).toArray(PasseriformeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final BiomeRegion[] biomeRegions;

        PasseriformeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, BiomeRegion[] biomeRegions) {
            this.id = id;
            this.fileName = fileName;
            this.size = size;
            this.isIridescent = isIridescent;
            this.featherColor = featherColor;
            this.childFeatherColor = childFeatherColor;
            this.biomeRegions = biomeRegions;
        }

        public int getId() {
            return id;
        }

        public String getFileName(){
            return fileName;
        }

        @Override
        public float getSize() {
            return size;
        }

        @Override
        public boolean isIridescent() {
            return isIridescent;
        }

        @Override
        public FeatherColor[] getFeatherColors() {
            return featherColor;
        }

        public FeatherColor getFeatherColor(int id){
            return this.featherColor[id % this.featherColor.length];
        }

        @Override
        public FeatherColor getChildFeatherColor() {
            return childFeatherColor;
        }

        @Override
        public BiomeRegion[] getBiomeRegion() {
            return biomeRegions;
        }

        public static PasseriformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static PasseriformeVariant byRegion(BiomeRegion region, int id) {
            PasseriformeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(biomeRegion -> biomeRegion == region)).toArray(PasseriformeVariant[]::new);

            return BY_REGION[id % BY_REGION.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
