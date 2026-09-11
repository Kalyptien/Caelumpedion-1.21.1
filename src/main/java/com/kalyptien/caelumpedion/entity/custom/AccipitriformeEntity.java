package com.kalyptien.caelumpedion.entity.custom;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
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

public class AccipitriformeEntity extends FlyingBirdEntity {

    public AccipitriformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.LONG_FLYER);
        this.setAquaticBirdType(AquaticBirdType.NONE);
        this.setFlyPathType(FlyPathType.NEAR_GROUND);
        this.setStressBirdType(StressBirdType.FIGHTER);

        this.flyRange = 200;
        this.flyHeight = 60;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20d)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FLYING_SPEED, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0d)
                .add(Attributes.ARMOR, 2d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    //Food

    @Override
    public boolean isFood(ItemStack itemStack) {
        return super.isFood(itemStack) || itemStack.is(Tags.Items.FOODS_RAW_MEAT) || itemStack.is(Tags.Items.FOODS_COOKED_MEAT) || itemStack.is(Tags.Items.BONES);
    }

    //Getter / Setter

    public int getIdVariant() {
        return this.entityData.get(VARIANT);
    }

    public AccipitriformeVariant getVariant() {
        return AccipitriformeVariant.byId(this.getIdVariant());
    }

    public void setVariant(AccipitriformeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if (spawnType == MobSpawnType.SPAWN_EGG) {
            AccipitriformeVariant variant = Util.getRandom(AccipitriformeVariant.values(), this.random);
            this.setVariant(variant);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Variant

    public static enum AccipitriformeVariant {
        GypaetusBarbatus(0, "gypaetus_barbatus", true),
        ;

        private static final AccipitriformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(AccipitriformeVariant::getId)).toArray(AccipitriformeVariant[]::new);
        private final int id;
        private final String fileName;
        private final boolean thickNeck;

        AccipitriformeVariant(int id, String fileName, boolean thickNeck) {
            this.id = id;
            this.fileName = fileName;
            this.thickNeck = thickNeck;
        }

        public int getId() {
            return id;
        }

        public String getFileName() {
            return fileName;
        }

        public boolean getThickNeck() {
            return thickNeck;
        }

        public static AccipitriformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int lenght() {
            return BY_ID.length;
        }
    }
}
