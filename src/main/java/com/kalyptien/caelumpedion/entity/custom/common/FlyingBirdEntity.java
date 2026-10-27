package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.datagen.CaelumpedionBiomeTagProvider;
import com.kalyptien.caelumpedion.entity.ai.BirdFlyPathNavigation;
import com.kalyptien.caelumpedion.entity.ai.BirdGroundPathNavigation;
import com.kalyptien.caelumpedion.entity.ai.FlyMoveController;
import com.kalyptien.caelumpedion.entity.ai.GroundMoveController;
import com.kalyptien.caelumpedion.entity.ai.goal.*;
import com.kalyptien.caelumpedion.util.CaelumpedionTags;
import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.VoronoiLevel;
import com.sun.jna.platform.win32.WinNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


import java.util.*;

public abstract class FlyingBirdEntity extends BirdEntity {

    //Anim var

    //Enum var

    FlyingBirdType flyingBirdType = FlyingBirdType.WALKER;

    //Flying Var

    protected static final EntityDataAccessor<Boolean> ON_MIGRATION =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> FLYING =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> NEED_TO_FLY_AWAY =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.BOOLEAN);

    protected int flyHeight = 50;
    protected int flyRange = 100;

    protected boolean isLandNavigator;

    private float flightPitch = 0;
    private float prevFlightPitch = 0;
    private float flightRoll = 0;
    private float prevFlightRoll = 0;

    private float flyProgress;
    private float prevFlyProgress;

    private boolean needToUpdateGoal = false;

    private BirdRandomFlyingGoal randomFlyingGoal;

    public FlyingBirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ON_MIGRATION, false);
        builder.define(FLYING, false);
        builder.define(NEED_TO_FLY_AWAY, false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        // Goal
        this.randomFlyingGoal = new BirdRandomFlyingGoal(this, 1.0);
        this.goalSelector.addGoal(8, randomFlyingGoal);
    }

    protected void updateGoals(){
        this.goalSelector.removeGoal(this.randomFlyingGoal);
        if(this.isNeedToFlyAway()){
            this.goalSelector.addGoal(0, this.randomFlyingGoal);
        }
        else{
            this.goalSelector.addGoal(8, this.randomFlyingGoal);
        }
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new GroundMoveController(this);
            this.navigation = new BirdGroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyMoveController(this);
            this.navigation = new FlyingPathNavigation(this, level());
            this.isLandNavigator = false;
        }
    }

    //Misc

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        if(this.isFlying()){
            double d0 = this.getBoundingBox().getSize();
            if (Double.isNaN(d0)) {
                d0 = 1.0;
            }

            d0 *= 128.0f * getViewScale();
            return distance < d0 * d0;
        }
        else{
            return super.shouldRenderAtSqrDistance(distance);
        }
    }

    //AiStep

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && !this.isFlying() && vec3.y < 0.0 && this.isTooHight(1)) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
        }
    }


    //Tick

    @Override
    public void tick(){
        super.tick();

        prevFlyProgress = flyProgress;
        prevFlightPitch = flightPitch;
        prevFlightRoll = flightRoll;

        if (isFlying() && flyProgress < 5F) {
            flyProgress++;
        }
        if (!isFlying() && flyProgress > 0F) {
            flyProgress--;
        }

        if(this.tickCount > 3200){
            this.setOnMigration(true);
            this.setNeedToFlyAway(true);
        }

        if (!level().isClientSide) {
            if(needToUpdateGoal){
                this.updateGoals();
            }

            if (this.isFlying()) {
                this.setNoGravity(true);
                if (this.isLandNavigator) {
                    switchNavigator(false);
                }
            } else {
                this.setNoGravity(false);
                if (!this.isLandNavigator) {
                    switchNavigator(true);
                }
            }
        }

        if(this.isFlying()){
            tickRotation((float) this.getDeltaMovement().y * 2 * -(float) (180F / (float) Math.PI));
        }
    }

    private void tickRotation(float yMov) {
        flightPitch = yMov;
        float threshold = 1F;
        boolean flag = false;
        if (isFlying() && this.yRotO - this.getYRot() > threshold) {
            flightRoll += 10;
            flag = true;
        }
        if (isFlying() && this.yRotO - this.getYRot() < -threshold) {
            flightRoll -= 10;
            flag = true;
        }
        if (!flag) {
            if (flightRoll > 0) {
                flightRoll = Math.max(flightRoll - 5, 0);
            }
            if (flightRoll < 0) {
                flightRoll = Math.min(flightRoll + 5, 0);
            }
        }
        flightRoll = Mth.clamp(flightRoll, -60, 60);
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    //Anim

    @Override
    protected void setupAnimationStates() {
        super.setupAnimationStates();

        this.fallAnimationState.animateWhen(!this.onGround() && !this.isOnAnimation() && !this.isFlying() && this.isTooHight(1),this.tickCount);
    }

    public boolean isTooHight(int blockUnderToScan){
        boolean result = true;

        BlockPos.MutableBlockPos ground = new BlockPos.MutableBlockPos();
        ground.set(this.getX(), this.getY(), this.getZ());

        for (int i = 0; i < blockUnderToScan; i++) {
            ground.move(0, -1, 0);

            if(!this.level().getBlockState(ground).isAir()){
                result = false;
            }
        }

        return result;
    }

    //Getter / Setter

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    public boolean isOnMigration() {
        return this.entityData.get(ON_MIGRATION);
    }

    public void setOnMigration(boolean onMigration) {
        this.entityData.set(ON_MIGRATION, onMigration);
    }

    public FlyingBirdType getFlyingBirdType() {
        return this.flyingBirdType;
    }

    public int getIdFlyingBirdType() {
        return this.flyingBirdType.getId();
    }

    public void setFlyingBirdType(FlyingBirdType flyingBirdType) {
        this.flyingBirdType = flyingBirdType;
    }

    public boolean isNeedToFlyAway() {
        return this.entityData.get(NEED_TO_FLY_AWAY);
    }

    public void setNeedToFlyAway(boolean needToFlyAway) {
        if(this.isNeedToFlyAway() != needToFlyAway){
            this.needToUpdateGoal = true;
        }

        this.entityData.set(NEED_TO_FLY_AWAY, needToFlyAway);
    }

    public int getFlyHeight() {
        return flyHeight;
    }

    public int getFlyRange() {
        return flyRange;
    }

    public float getFlightPitch(float partialTick) {
        return (prevFlightPitch + (flightPitch - prevFlightPitch) * partialTick);
    }

    public float getFlightRoll(float partialTick) {
        return (prevFlightRoll + (flightRoll - prevFlightRoll) * partialTick);
    }

    public float getFlyProgress(float partialTick) {
        return (prevFlyProgress + (flyProgress - prevFlyProgress) * partialTick) * 0.2F;
    }

    //Spawn

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData) {
        if(!(spawnGroupData instanceof BirdGroupData)){
            spawnGroupData = new BirdGroupData();
        }

        //Migration
        if(!(spawnType == MobSpawnType.SPAWN_EGG || spawnType == MobSpawnType.SPAWNER || spawnType == MobSpawnType.TRIAL_SPAWNER)){
            if(spawnGroupData instanceof BirdGroupData birdGroupData){
                if(!birdGroupData.onMigrationSet){
                    Holder<Biome> holder = level.getBiome(this.blockPosition());

                    //If not in cave
                    if (!holder.is(CaelumpedionTags.Biomes.IS_CAVE)) {
                        //Small chance to happen
                        if(this.random.nextFloat() <= 0.9f){
                            birdGroupData.setOnMigration(true);
                        }
                        else{
                            birdGroupData.setOnMigration(false);
                        }
                    }
                    else{
                        birdGroupData.setOnMigration(false);
                    }
                }

                this.setOnMigration(birdGroupData.onMigration);

                if(birdGroupData.onMigration){
                    this.setNeedToFlyAway(true);
                }
            }
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    //Save Data

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("OnMigration", this.isOnMigration());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(ON_MIGRATION, compound.getBoolean("OnMigration"));
    }

    //Enum

    public static enum FlyingBirdType {
        WALKER(0),
        SHORT_FlYER(1),
        LONG_FLYER(2);

        private static final FlyingBirdType[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(FlyingBirdType::getId)).toArray(FlyingBirdType[]::new);
        private final int id;

        FlyingBirdType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static FlyingBirdType byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }
}
