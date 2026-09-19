package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.entity.ai.BirdGroundPathNavigation;
import com.kalyptien.caelumpedion.entity.ai.goal.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;

public abstract class BirdEntity extends Animal {

    //Variant var

    protected static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(BirdEntity.class, EntityDataSerializers.INT);

    //Anim var

    protected static final EntityDataAccessor<Boolean> ON_ANIMATION =
            SynchedEntityData.defineId(BirdEntity.class, EntityDataSerializers.BOOLEAN);

    private int idleAnimationTimeout = 0;
    private int idleAnimationTimein = 0;

    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    //Anim var : Aquatic Bird

    public final AnimationState eatInWaterAnimationState = new AnimationState();
    public final AnimationState idleInWaterAnimationState = new AnimationState();

    //Enum var

    FlyingBirdEntity.AquaticBirdType aquaticBirdType = AquaticBirdType.NONE;

    public BirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new MoveControl(this);
        this.navigation = new BirdGroundPathNavigation(this, level());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(ON_ANIMATION, false);
    }

    @Override
    protected void registerGoals() {
        // Goal
        this.goalSelector.addGoal(0, new BirdFloatGoal(this));

        this.goalSelector.addGoal(1, new BirdPanicGoal(this, 2.0f, (bird) -> {
            return bird instanceof NeutralMob ? DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES : DamageTypeTags.PANIC_CAUSES;
        }));

        this.goalSelector.addGoal(5, new BirdTemptGoal(this, 1.5f, this::isFood, false));

        this.goalSelector.addGoal(8, new BirdRandomStrollGoal(this, 1.0));

        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, this.getViewRange()));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    //Misc

    public boolean canBeLeashed() {
        return false;
    }

    public boolean canAttack(LivingEntity target) {
        return this.level().getDifficulty() == Difficulty.PEACEFUL ? false : target.canBeSeenAsEnemy();
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return this.getIdAquaticBirdType() == FlyingBirdEntity.AquaticBirdType.FULL.getId() ? fluidState.is(FluidTags.WATER) : false;
    }

    //Tick

    @Override
    public void tick(){
        super.tick();
        this.setupAnimationStates();
    }

    //Animation

    protected void setupAnimationStates() {
        if(this.isInWater() && this.aquaticBirdType.getId() == AquaticBirdType.FULL.id){
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(500) + 500;
                this.idleAnimationTimein = 0;

                double seed = this.random.nextInt(100)/100.0f;

                if(seed > 0.5f){
                    this.idleInWaterAnimationState.start(this.tickCount);
                }
                else{
                    this.eatInWaterAnimationState.start(this.tickCount);
                }

                this.setOnAnimation(true);
            } else {
                --this.idleAnimationTimeout;
                ++this.idleAnimationTimein;
            }
        }
        else if (this.onGround()){
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(500) + 500;
                this.idleAnimationTimein = 0;

                double seed = this.random.nextInt(100)/100.0f;

                if(seed > 0.5f){
                    this.idleAnimationState.start(this.tickCount);
                }
                else{
                    this.eatAnimationState.start(this.tickCount);
                }

                this.setOnAnimation(true);
            } else {
                --this.idleAnimationTimeout;
                ++this.idleAnimationTimein;
            }
        }

        if(this.idleAnimationTimein >= 100){
            this.setOnAnimation(false);
        }
    }

    public void resetAnimations(){
        if(this.idleAnimationState.isStarted()){
            this.idleAnimationState.stop();
        }

        if(this.eatAnimationState.isStarted()){
            this.eatAnimationState.stop();
        }

        if(this.idleInWaterAnimationState.isStarted()){
            this.idleInWaterAnimationState.stop();
        }

        if(this.eatInWaterAnimationState.isStarted()){
            this.eatInWaterAnimationState.stop();
        }

        this.idleAnimationTimein = 0;
        this.setOnAnimation(false);
    }

    //Food/Breed

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Tags.Items.SEEDS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    //Getter / Setter

    public abstract int getIdVariant();

    public FlyingBirdEntity.AquaticBirdType getAquaticBirdType() {
        return this.aquaticBirdType;
    }

    public int getIdAquaticBirdType() {
        return this.aquaticBirdType.getId();
    }

    public void setAquaticBirdType(FlyingBirdEntity.AquaticBirdType aquaticBirdType) {
        this.aquaticBirdType = aquaticBirdType;
    }

    public boolean isOnAnimation() {
        return this.entityData.get(ON_ANIMATION);
    }

    public void setOnAnimation(boolean onAnimation) {
        this.entityData.set(ON_ANIMATION, onAnimation);
    }

    public double getFlySpeed() {
        return this.getAttributeValue(Attributes.FLYING_SPEED);
    }

    public double getGroundSpeed() {
        return this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    public double getFollowRange(){
        return this.getViewRange();
    }

    public int getViewRange() {
        return (int) this.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    //Save Data

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getIdVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
    }

    //Enum

    public static enum AquaticBirdType {
        NONE(0),
        TALL(1),
        FULL(2);

        private static final AquaticBirdType[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(AquaticBirdType::getId)).toArray(AquaticBirdType[]::new);
        private final int id;

        AquaticBirdType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static AquaticBirdType byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }
}
