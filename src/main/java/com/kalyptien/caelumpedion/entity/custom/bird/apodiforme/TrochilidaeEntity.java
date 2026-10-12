package com.kalyptien.caelumpedion.entity.custom.bird.apodiforme;

import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.Util;
import net.minecraft.tags.ItemTags;
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

    // SPAWN

    protected void setRandomVariant(){
        TrochilidaeVariant variant = Util.getRandom(TrochilidaeVariant.values(), this.random);
        this.setVariant(variant);
    }

    protected void setVariantFromIrlRegion(IRLRegion IrlRegionKey){
        TrochilidaeVariant variant = TrochilidaeVariant.byRegion(IrlRegionKey, random.nextInt(TrochilidaeVariant.values().length));
        this.setVariant(variant);
    }

    protected void setVariantFromId(int id){
        TrochilidaeVariant variant = TrochilidaeVariant.byId(id);
        this.setVariant(variant);
    }

    // Variant

    public static enum TrochilidaeVariant implements BirdVariant {
        ColibriDelphinae(0, "colibri_delphinae", 1.0f, true, new FeatherColor[]{FeatherColor.Brown}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.SouthAmerica}),
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

        TrochilidaeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, IRLRegion[] IRLRegions) {
            this.id = id;
            this.fileName = fileName;
            this.size = size;
            this.isIridescent = isIridescent;
            this.featherColor = featherColor;
            this.childFeatherColor = childFeatherColor;
            this.IRLRegions = IRLRegions;
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

        public static TrochilidaeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static TrochilidaeVariant byRegion(IRLRegion region, int id) {
            TrochilidaeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(biomeRegion -> biomeRegion == region)).toArray(TrochilidaeVariant[]::new);

            return BY_REGION[id % BY_REGION.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
