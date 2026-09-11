package com.kalyptien.caelumpedion.entity.custom;

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

public class GruiformeEntity extends SocialFlyingBirdEntity {

    public GruiformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.WALKER);
        this.setAquaticBirdType(AquaticBirdType.TALL);
        this.setStressBirdType(StressBirdType.FIGHTER);
        this.setBOIDBirdType(BOIDType.FORMATION);
        this.setFlyPathType(FlyPathType.NEAR_GROUND);

        this.flyRange = 200;
        this.flyHeight = 60;

        this.maxSchoolSize = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 18d)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FLYING_SPEED, 3.0D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    //Getter / Setter

    public int getIdVariant() {
        return this.entityData.get(VARIANT);
    }

    public GruiformeVariant getVariant() {
        return GruiformeVariant.byId(this.getIdVariant());
    }

    public void setVariant(GruiformeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if(spawnType == MobSpawnType.SPAWN_EGG){
            GruiformeVariant variant = Util.getRandom(GruiformeVariant.values(), this.random);
            this.setVariant(variant);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Variant

    public static enum GruiformeVariant {
        GrusJaponensis(0, "grus_japonensis"),
        ;

        private static final GruiformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(GruiformeVariant::getId)).toArray(GruiformeVariant[]::new);
        private final int id;
        private final String fileName;

        GruiformeVariant(int id, String fileName) {
            this.id = id;
            this.fileName = fileName;
        }

        public int getId() {
            return id;
        }

        public String getFileName(){
            return fileName;
        }

        public static GruiformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
