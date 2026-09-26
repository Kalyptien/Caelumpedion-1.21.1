package com.kalyptien.caelumpedion.entity.custom.bird.piciforme;

import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
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

public class PiciformeEntity extends FlyingBirdEntity {

    public PiciformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.SHORT_FlYER);
        this.setAquaticBirdType(BirdEntity.AquaticBirdType.NONE);
        this.setFlyPathType(FlyingBirdEntity.FlyPathType.NORMAL);

        this.flyRange = 100;
        this.flyHeight = 30;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12d)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FLYING_SPEED, 3.0D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    //Getter / Setter

    public int getIdVariant() {
        return this.entityData.get(VARIANT);
    }

    public PiciformeVariant getVariant() {
        return PiciformeVariant.byId(this.getIdVariant());
    }

    public void setVariant(PiciformeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if(spawnType == MobSpawnType.SPAWN_EGG){
            PiciformeVariant variant = Util.getRandom(PiciformeVariant.values(), this.random);
            this.setVariant(variant);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Variant

    public static enum PiciformeVariant implements BirdVariant {
        PicusViridis(0, "picus_viridis", 1.0f, false, new FeatherColor[]{FeatherColor.Green, FeatherColor.White}, FeatherColor.Green, new BiomeRegion[]{BiomeRegion.Europe}),
        ;

        private static final PiciformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(PiciformeVariant::getId)).toArray(PiciformeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final BiomeRegion[] biomeRegions;

        PiciformeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, BiomeRegion[] biomeRegions) {
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

        public static PiciformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static PiciformeVariant byRegion(BiomeRegion region, int id) {
            PiciformeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(biomeRegion -> biomeRegion == region)).toArray(PiciformeVariant[]::new);

            return BY_REGION[id % BY_REGION.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
