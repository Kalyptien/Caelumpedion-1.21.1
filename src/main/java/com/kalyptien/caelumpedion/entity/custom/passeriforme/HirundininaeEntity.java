package com.kalyptien.caelumpedion.entity.custom.passeriforme;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
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

public class HirundininaeEntity extends SocialFlyingBirdEntity {

    public HirundininaeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.LONG_FLYER);
        this.setAquaticBirdType(AquaticBirdType.NONE);
        this.setStressBirdType(StressBirdType.RUNNER);
        this.setBOIDBirdType(BOIDType.SWARM);
        this.setFlyPathType(FlyPathType.CHAOS);

        this.flyRange = 150;
        this.flyHeight = 40;

        this.maxSchoolSize = 50;
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

    public HirundininaeVariant getVariant() {
        return HirundininaeVariant.byId(this.getIdVariant());
    }

    public void setVariant(HirundininaeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if(spawnType == MobSpawnType.SPAWN_EGG){
            HirundininaeVariant variant = Util.getRandom(HirundininaeVariant.values(), this.random);
            this.setVariant(variant);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Variant

    public static enum HirundininaeVariant {
        DelichonUrbicum(0, "delichon_urbicum"),
        ;

        private static final HirundininaeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(HirundininaeVariant::getId)).toArray(HirundininaeVariant[]::new);
        private final int id;
        private final String fileName;

        HirundininaeVariant(int id, String fileName) {
            this.id = id;
            this.fileName = fileName;
        }

        public int getId() {
            return id;
        }

        public String getFileName(){
            return fileName;
        }

        public static HirundininaeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
