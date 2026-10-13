package com.kalyptien.caelumpedion.entity.custom.bird.piciforme;

import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.Util;
import net.minecraft.core.Holder;
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

    protected void setRandomVariant(){
        RamphastidaeVariant variant = Util.getRandom(RamphastidaeVariant.values(), this.random);

        this.setVariant(variant);
    }

    protected boolean setVariantFromIrlRegionBiome(Holder<Biome> holder, IRLRegion IrlRegionKey, int randomInt){
        RamphastidaeVariant variant = RamphastidaeVariant.byIrlRegionBiome(holder, IrlRegionKey, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    };

    protected boolean setVariantFromBiome(Holder<Biome> holder, int randomInt){
        RamphastidaeVariant variant = RamphastidaeVariant.byBiome(holder, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    };

    protected boolean setVariantFromIrlRegion(IRLRegion IrlRegionKey, int randomInt){
        RamphastidaeVariant variant = RamphastidaeVariant.byRegion(IrlRegionKey, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    }

    protected void setVariantFromId(int id){
        RamphastidaeVariant variant = RamphastidaeVariant.byId(id);

        this.setVariant(variant);
    }

    protected int getVariantLength(){
        return RamphastidaeVariant.lenght();
    };

    // Variant

    public static enum RamphastidaeVariant implements BirdVariant {
        RamphastosToco(0, "ramphastos_toco", 1.0f, false, new FeatherColor[]{FeatherColor.Black,FeatherColor.White}, FeatherColor.White, new IRLRegion[]{IRLRegion.SouthAmerica}, null),
        ;

        private static final RamphastidaeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(RamphastidaeVariant::getId)).toArray(RamphastidaeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final IRLRegion[] IRLRegions;
        @Nullable
        private final TagKey<Biome> biomeTagKey;

        RamphastidaeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, IRLRegion[] IRLRegions, TagKey<Biome> biomeTagKey) {
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

        public static RamphastidaeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static RamphastidaeVariant byIrlRegionBiome(Holder<Biome> holder, IRLRegion region, int id) {
            RamphastidaeVariant[] BY_BIOME = Arrays.stream(values())
                    .filter(variant -> variant.getBiomeTagKey() != null && holder.is(variant.getBiomeTagKey())).toArray(RamphastidaeVariant[]::new);

            RamphastidaeVariant[] BY_BIOMEREGION = Arrays.stream(BY_BIOME)
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(irlRegion -> irlRegion == region)).toArray(RamphastidaeVariant[]::new);

            if(BY_BIOMEREGION.length != 0){
                return BY_BIOMEREGION[id % BY_BIOMEREGION.length];
            }
            else{
                return null;
            }
        }

        public static RamphastidaeVariant byRegion(IRLRegion region, int id) {
            RamphastidaeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(irlRegion -> irlRegion == region)).toArray(RamphastidaeVariant[]::new);

            if(BY_REGION.length != 0){
                return BY_REGION[id % BY_REGION.length];

            }
            else{
                return null;
            }
        }

        public static RamphastidaeVariant byBiome(Holder<Biome> holder, int id) {
            RamphastidaeVariant[] BY_BIOME = Arrays.stream(values())
                    .filter(variant -> variant.getBiomeTagKey() != null && holder.is(variant.getBiomeTagKey())).toArray(RamphastidaeVariant[]::new);

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
