package com.kalyptien.caelumpedion.entity.custom.bird.piciforme;

import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public class RamphastidaeEntity extends  SocialFlyingBirdEntity{
    
    public RamphastidaeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.LONG_FLYER);
        this.setAquaticBirdType(AquaticBirdType.NONE);
        this.setBOIDBirdType(BOIDType.FOLLOW);
        this.setFlyPathType(FlyPathType.NORMAL);

        this.flyRange = 150;
        this.flyHeight = 50;

        this.maxSchoolSize = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12d)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FLYING_SPEED, 3.0D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    //Food

    @Override
    public boolean isFood(ItemStack itemStack) {
        return super.isFood(itemStack) || itemStack.is(Tags.Items.FOODS_FRUIT) ;
    }

    //Getter / Setter

    public int getIdVariant() {
        return this.entityData.get(VARIANT);
    }

    public RamphastidaeEntity.RamphastidaeVariant getVariant() {
        return RamphastidaeEntity.RamphastidaeVariant.byId(this.getIdVariant());
    }

    public void setVariant(RamphastidaeEntity.RamphastidaeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if(spawnType == MobSpawnType.SPAWN_EGG){
            RamphastidaeEntity.RamphastidaeVariant variant = Util.getRandom(RamphastidaeEntity.RamphastidaeVariant.values(), this.random);
            this.setVariant(variant);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Variant

    public static enum RamphastidaeVariant implements BirdVariant {
        RamphastosToco(0, "ramphastos_toco", 1.0f, false, new FeatherColor[]{FeatherColor.Black,FeatherColor.White}, FeatherColor.White, new BiomeRegion[]{BiomeRegion.SouthAmerica}),
        ;

        private static final RamphastidaeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(RamphastidaeVariant::getId)).toArray(RamphastidaeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final BiomeRegion[] biomeRegions;

        RamphastidaeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, BiomeRegion[] biomeRegions) {
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

        public static RamphastidaeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static RamphastidaeVariant byRegion(BiomeRegion region, int id) {
            RamphastidaeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(biomeRegion -> biomeRegion == region)).toArray(RamphastidaeVariant[]::new);

            return BY_REGION[id % BY_REGION.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
