package com.kalyptien.caelumpedion.entity.custom.bird.passeriforme;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

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

    protected void setRandomVariant(){
        PasseriformeVariant variant = Util.getRandom(PasseriformeVariant.values(), this.random);
        this.setVariant(variant);
    }

    protected void setVariantFromIrlRegion(IRLRegion IrlRegionKey){
        PasseriformeVariant variant = PasseriformeVariant.byRegion(IrlRegionKey, random.nextInt(PasseriformeVariant.values().length));
        this.setVariant(variant);
    }

    protected void setVariantFromId(int id){
        PasseriformeVariant variant = PasseriformeVariant.byId(id);
        this.setVariant(variant);
    }

    // Variant

    public enum PasseriformeVariant implements BirdVariant {
        CardinalisCardinalis(0, "cardinalis_cardinalis", 1.0f, false, new FeatherColor[]{FeatherColor.Red}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.NorthAmerica}),
        CyanistesCaeruleus(1, "cyanistes_caeruleus", 1.0f, false, new FeatherColor[]{FeatherColor.LightBlue, FeatherColor.Yellow}, FeatherColor.Yellow, new IRLRegion[]{IRLRegion.Europe}),
        CyanocittaCristata(2, "cyanocitta_cristata", 1.0f, false, new FeatherColor[]{FeatherColor.Cyan, FeatherColor.White}, FeatherColor.Blue, new IRLRegion[]{IRLRegion.NorthAmerica}),
        ErithacusRubecula(3, "erithacus_rubecula", 1.0f, false, new FeatherColor[]{FeatherColor.Orange, FeatherColor.Gray, FeatherColor.Brown}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.Europe}),
        LophophanesCristatus(4, "lophophanes_cristatus", 1.0f, false, new FeatherColor[]{FeatherColor.White, FeatherColor.Brown}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.Europe}),
        PasserDomesticus(5, "passer_domesticus", 1.0f, false, new FeatherColor[]{FeatherColor.Gray, FeatherColor.Brown}, FeatherColor.Gray, new IRLRegion[]{IRLRegion.Europe, IRLRegion.NorthAmerica, IRLRegion.SouthAmerica, IRLRegion.SouthAfrica, IRLRegion.Oceania, IRLRegion.Asia}),
        PeriparusAter(6, "periparus_ater", 1.0f, false, new FeatherColor[]{FeatherColor.Black, FeatherColor.White}, FeatherColor.Black, new IRLRegion[]{IRLRegion.Europe, IRLRegion.Asia}),
        PhoenicurusOchruros(7, "phoenicurus_ochruros", 1.0f, false, new FeatherColor[]{FeatherColor.Black}, FeatherColor.Gray, new IRLRegion[]{IRLRegion.Europe, IRLRegion.NorthAfrica, IRLRegion.Asia}),
        ;

        private static final PasseriformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(PasseriformeVariant::getId)).toArray(PasseriformeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final IRLRegion[] IRLRegions;

        PasseriformeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, IRLRegion[] IRLRegions) {
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

        public static PasseriformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static PasseriformeVariant byRegion(IRLRegion region, int id) {
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
