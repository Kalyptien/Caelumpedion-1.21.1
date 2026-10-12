package com.kalyptien.caelumpedion.entity.custom.bird.columbiforme;

import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.kalyptien.caelumpedion.util.IRLRegion;
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

public class ColumbiformeEntity extends SocialFlyingBirdEntity {

    public ColumbiformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.SHORT_FlYER);
        this.setAquaticBirdType(AquaticBirdType.NONE);
        this.setBOIDBirdType(BOIDType.FOLLOW);
        this.setFlyPathType(FlyPathType.NORMAL);

        this.flyRange = 100;
        this.flyHeight = 30;

        this.maxSchoolSize = 25;
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

    public ColumbiformeVariant getVariant() {
        return ColumbiformeVariant.byId(this.getIdVariant());
    }

    public void setVariant(ColumbiformeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    protected void setRandomVariant(){
        ColumbiformeVariant variant = Util.getRandom(ColumbiformeVariant.values(), this.random);
        this.setVariant(variant);
    }

    protected void setVariantFromIrlRegion(IRLRegion IrlRegionKey){
        ColumbiformeVariant variant = ColumbiformeVariant.byRegion(IrlRegionKey, random.nextInt(ColumbiformeVariant.values().length));
        this.setVariant(variant);
    }

    protected void setVariantFromId(int id){
        ColumbiformeVariant variant = ColumbiformeVariant.byId(id);
        this.setVariant(variant);
    }

    // Variant

    public static enum ColumbiformeVariant implements BirdVariant {
        ColumbaLivia(0, "columba_livia", 1.0f, true, new FeatherColor[]{FeatherColor.Gray, FeatherColor.LightGray}, FeatherColor.LightGray, new IRLRegion[]{IRLRegion.NorthAmerica, IRLRegion.Europe, IRLRegion.Asia, IRLRegion.NorthAfrica, IRLRegion.Oceania}),
        ;

        private static final ColumbiformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(ColumbiformeVariant::getId)).toArray(ColumbiformeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final IRLRegion[] IRLRegions;

        ColumbiformeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, IRLRegion[] IRLRegions) {
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

        public static ColumbiformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static ColumbiformeVariant byRegion(IRLRegion region, int id) {
            ColumbiformeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(biomeRegion -> biomeRegion == region)).toArray(ColumbiformeVariant[]::new);

            return BY_REGION[id % BY_REGION.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
