package com.kalyptien.caelumpedion.entity.custom.bird.accipitriforme;

import com.kalyptien.caelumpedion.entity.ai.CircleAroundFlyingMob;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.util.BiomeRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

public class AccipitridaeEntity extends FlyingBirdEntity implements NeutralMob, CircleAroundFlyingMob {

    //Anger Var

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME =
            SynchedEntityData.defineId(AccipitridaeEntity.class, EntityDataSerializers.INT);;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);;
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    //Circle Around Var

    Vec3 moveTargetPoint = Vec3.ZERO;
    BlockPos anchorPoint = BlockPos.ZERO;
    AttackPhase attackPhase = AttackPhase.CIRCLE;
    boolean isCyclingAround = false;

    public AccipitridaeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.LONG_FLYER);
        this.setAquaticBirdType(AquaticBirdType.NONE);
        this.setFlyPathType(FlyPathType.NEAR_GROUND);

        this.flyRange = 200;
        this.flyHeight = 60;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20d)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FLYING_SPEED, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0d)
                .add(Attributes.ARMOR, 2d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //Goal

        this.goalSelector.addGoal(9, new AttackStrategyGoal(this));
        this.goalSelector.addGoal(9, new CircleAroundFlyingMob.CircleAroundFlyingGoal(this));

        // Target

        this.targetSelector.addGoal(4, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(4, new ScavangerNearestAttackableTargetGoal(this, Player.class, 10, true, false,
                (target) -> {
                    return this.isAngryAt((LivingEntity) target) || ((LivingEntity) target).getHealth() <= 5.0f;
                }));
        this.targetSelector.addGoal(4, new ScavangerNearestAttackableTargetGoal(this, Animal.class, 200, true, false,
                (target) -> {
                    return ((LivingEntity) target).isBaby() || ((LivingEntity) target).getHealth() <= 5.0f;
                }));

        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
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

    public AccipitridaeVariant getVariant() {
        return AccipitridaeVariant.byId(this.getIdVariant());
    }

    public void setVariant(AccipitridaeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    @Override
    public Vec3 getMoveTargetPoint() {
        return moveTargetPoint;
    }

    @Override
    public void setMoveTargetPoint(Vec3 moveTargetPoint) {
        this.moveTargetPoint = moveTargetPoint;
    }

    @Override
    public BlockPos getAnchorPoint() {
        return anchorPoint;
    }

    @Override
    public void setAnchorPoint(BlockPos anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    @Override
    public AttackPhase getAttackPhase() {
        return attackPhase;
    }

    @Override
    public void setAttackPhase(AttackPhase attackPhase) {
        this.attackPhase = attackPhase;
    }

    public boolean isCyclingAround(){
        return this.isCyclingAround;
    }

    public void setCyclingAround(boolean cyclingAround){
        this.isCyclingAround = cyclingAround;
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if (spawnType == MobSpawnType.SPAWN_EGG) {
            AccipitridaeVariant variant = Util.getRandom(AccipitridaeVariant.values(), this.random);
            this.setVariant(variant);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    //ANGER

    public int getRemainingPersistentAngerTime() {
        return (Integer)this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(this.PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    public int getAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    //Data

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addPersistentAngerSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readPersistentAngerSaveData(this.level(), compound);
    }

    // Variant

    public static enum AccipitridaeVariant implements BirdVariant {
        GypaetusBarbatus(0, "gypaetus_barbatus", 1.0f, false, new FeatherColor[]{FeatherColor.Orange}, FeatherColor.Brown, new BiomeRegion[]{BiomeRegion.Europe, BiomeRegion.Asia, BiomeRegion.NorthAfrica, BiomeRegion.SouthAfrica}),
        ;

        private static final AccipitridaeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(AccipitridaeVariant::getId)).toArray(AccipitridaeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final BiomeRegion[] biomeRegions;

        AccipitridaeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, BiomeRegion[] biomeRegions) {
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

        public static AccipitridaeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static AccipitridaeVariant byRegion(BiomeRegion region, int id) {
            AccipitridaeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(biomeRegion -> biomeRegion == region)).toArray(AccipitridaeVariant[]::new);

            return BY_REGION[id % BY_REGION.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
