package com.kalyptien.caelumpedion.entity.custom.bird.apodiforme;

import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.RamphastidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public class TrochilidaeEntity extends FlyingBirdEntity {

    public TrochilidaeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.SHORT_FlYER);
        this.setAquaticBirdType(AquaticBirdType.NONE);
        this.setFlyPathType(FlyPathType.CHAOS);

        this.flyRange = 50;
        this.flyHeight = 30;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6d)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.FLYING_SPEED, 5.0D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemTags.BEE_FOOD);
    }

    //Getter / Setter

    public int getIdVariant() {
        return this.entityData.get(VARIANT);
    }

    public TrochilidaeVariant getVariant() {
        return TrochilidaeVariant.byId(this.getIdVariant());
    }

    public void setVariant(TrochilidaeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    protected void setRandomVariant(){
        TrochilidaeVariant variant = Util.getRandom(TrochilidaeVariant.values(), this.random);

        this.setVariant(variant);
    }

    protected boolean setVariantFromIrlRegionBiome(Holder<Biome> holder, IRLRegion IrlRegionKey, int randomInt){
        TrochilidaeVariant variant = TrochilidaeVariant.byIrlRegionBiome(holder, IrlRegionKey, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    };

    protected boolean setVariantFromBiome(Holder<Biome> holder, int randomInt){
        TrochilidaeVariant variant = TrochilidaeVariant.byBiome(holder, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    };

    protected boolean setVariantFromIrlRegion(IRLRegion IrlRegionKey, int randomInt){
        TrochilidaeVariant variant = TrochilidaeVariant.byRegion(IrlRegionKey, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    }

    protected void setVariantFromId(int id){
        TrochilidaeVariant variant = TrochilidaeVariant.byId(id);

        this.setVariant(variant);
    }

    protected int getVariantLength(){
        return TrochilidaeVariant.lenght();
    };

    // Variant

    public static enum TrochilidaeVariant implements BirdVariant {
        ColibriDelphinae(0, "colibri_delphinae", 1.0f, true, new FeatherColor[]{FeatherColor.Brown}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.SouthAmerica}, BiomeTags.IS_JUNGLE),
        ;

        private static final TrochilidaeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(TrochilidaeVariant::getId)).toArray(TrochilidaeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final IRLRegion[] IRLRegions;
        @Nullable
        private final TagKey<Biome> biomeTagKey;

        TrochilidaeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, IRLRegion[] IRLRegions, TagKey<Biome> biomeTagKey) {
            this.id = id;
            this.fileName = fileName;
            this.size = size;
            this.isIridescent = isIridescent;
            this.featherColor = featherColor;
            this.childFeatherColor = childFeatherColor;
            this.IRLRegions = IRLRegions;
            this.biomeTagKey = biomeTagKey;
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
        public IRLRegion[] getBiomeRegion() {
            return IRLRegions;
        }

        @Override
        public TagKey<Biome> getBiomeTagKey() {
            return biomeTagKey;
        }

        public static TrochilidaeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static TrochilidaeVariant byIrlRegionBiome(Holder<Biome> holder, IRLRegion region, int id) {
            TrochilidaeVariant[] BY_BIOME = Arrays.stream(values())
                    .filter(variant -> variant.getBiomeTagKey() != null && holder.is(variant.getBiomeTagKey())).toArray(TrochilidaeVariant[]::new);

            TrochilidaeVariant[] BY_BIOMEREGION = Arrays.stream(BY_BIOME)
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(irlRegion -> irlRegion == region)).toArray(TrochilidaeVariant[]::new);

            if(BY_BIOMEREGION.length != 0){
                return BY_BIOMEREGION[id % BY_BIOMEREGION.length];
            }
            else{
                return null;
            }
        }

        public static TrochilidaeVariant byRegion(IRLRegion region, int id) {
            TrochilidaeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(irlRegion -> irlRegion == region)).toArray(TrochilidaeVariant[]::new);

            if(BY_REGION.length != 0){
                return BY_REGION[id % BY_REGION.length];

            }
            else{
                return null;
            }
        }

        public static TrochilidaeVariant byBiome(Holder<Biome> holder, int id) {
            TrochilidaeVariant[] BY_BIOME = Arrays.stream(values())
                    .filter(variant -> variant.getBiomeTagKey() != null && holder.is(variant.getBiomeTagKey())).toArray(TrochilidaeVariant[]::new);

            if(BY_BIOME.length != 0){
                return BY_BIOME[id % BY_BIOME.length];
            }
            else{
                return null;
            }
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
