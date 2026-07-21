package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.entity.ai.FlightPathNavigatorNoSpin;
import com.kalyptien.caelumpedion.entity.ai.FlyingMoveController;
import com.kalyptien.caelumpedion.entity.ai.WalkingMoveController;
import com.kalyptien.caelumpedion.entity.ai.goal.BirdFlyGoal;
import com.kalyptien.caelumpedion.entity.ai.goal.PrepareFlyGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class FlyingBirdEntity extends Animal {
    protected static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> FLYING =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState planeAnimationState = new AnimationState();
    public final AnimationState landAnimationState = new AnimationState();

    public final AnimationState walkAnimationState = new AnimationState();

    protected boolean isFlyingAnim = false;
    protected boolean isPlaningAnim = false;
    protected boolean isLandingAnim = false;

    protected boolean isIdlingAnim = false;

    protected boolean IsWalkingAnim = false;

    protected List<Vec3> nextNavigationArray = new ArrayList<Vec3>();

    protected float flySpeed = 4f;

    protected int longFlyHeight = 30;
    protected int longFlyRange = 100;

    protected int shortFlyHeight = 10;
    protected int shortFlyRange = 10;

    protected boolean isLandNavigator;

    protected float flightPitch = 0;
    protected float flightRoll = 0;

    protected int timeFlying = 0;
    protected int groundedFor = 0;

    public FlyingBirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(FLYING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BirdFlyGoal(this));

        this.goalSelector.addGoal(1, new FloatGoal(this));

        this.goalSelector.addGoal(2, new PrepareFlyGoal(this, true));

        //3

        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(5, new PrepareFlyGoal(this, false));

    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new WalkingMoveController(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this);
            this.navigation = new FlightPathNavigatorNoSpin(this, level(), 1.0F);
            this.isLandNavigator = false;
        }
    }

    //Tick

    @Override
    public void tick(){
        super.tick();

        if (!level().isClientSide) {
            if (this.isFlying()) {
                timeFlying++;
                this.setNoGravity(true);
                if (this.isLandNavigator) {
                    switchNavigator(false);
                }
                if (groundedFor > 0) {
                    this.setFlying(false);
                }
            } else {
                timeFlying = 0;
                this.setNoGravity(false);
                if (!this.isLandNavigator) {
                    switchNavigator(true);
                }
            }
        }
        if (groundedFor > 0) {
            groundedFor--;
        }

        tickRotation((float) this.getDeltaMovement().y * 2 * -(float) (180F / (float) Math.PI));
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

    //Animation

    protected void setupAnimationStates() {

        if(!this.isFlying()){
            if(this.isWalkingAnim()){
                this.walkAnimationState.start(this.tickCount);
            }
            else{
                if(this.walkAnimationState.isStarted()) {
                    this.walkAnimationState.stop();
                }
            }
        }

        if(this.isFlying()){

            if(this.isFlyingAnim){
                this.flyAnimationState.start(this.tickCount);
            }
            else{
                if(this.flyAnimationState.isStarted()) {
                    this.flyAnimationState.stop();
                }
            }

            if(this.isPlaningAnim){
                this.planeAnimationState.start(this.tickCount);
            }
            else{
                if(this.planeAnimationState.isStarted()) {
                    this.planeAnimationState.stop();
                }
            }

            if(this.isLandingAnim){
                this.landAnimationState.start(this.tickCount);
            }
            else{
                if(this.landAnimationState.isStarted()) {
                    this.landAnimationState.stop();
                }
            }
        }
        else{
            if(this.flyAnimationState.isStarted()) {
                this.flyAnimationState.stop();
            }

            if(this.planeAnimationState.isStarted()) {
                this.planeAnimationState.stop();
            }

            if(this.landAnimationState.isStarted()) {
                this.landAnimationState.stop();
            }
        }
    }

    //Food/Breed

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.WHEAT_SEEDS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    //Getter / Setter

    public abstract int getDataVariant();

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public List<Vec3> getNextNavigationArray() {
        return nextNavigationArray;
    }

    public void addNextNavigationArray(Vec3 vector){
        this.nextNavigationArray.add(vector);
    }

    public void removeNextNavigationArray(){
        if(!this.nextNavigationArray.isEmpty())
            this.nextNavigationArray.removeFirst();
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public int getLongFlyHeight() {
        return longFlyHeight;
    }

    public int getLongFlyRange() {
        return longFlyRange;
    }

    public int getShortFlyHeight() {
        return shortFlyHeight;
    }

    public int getShortFlyRange() {
        return shortFlyRange;
    }

    public int getTimeFlying() {
        return timeFlying;
    }

    public int getGroundedFor() {
        return groundedFor;
    }

    public boolean isFlyingAnim() {
        return isFlyingAnim;
    }

    public void setFlyingAnim(boolean flyingAnim) {
        isFlyingAnim = flyingAnim;
    }

    public boolean isPlaningAnim() {
        return isPlaningAnim;
    }

    public void setPlaningAnim(boolean planingAnim) {
        isPlaningAnim = planingAnim;
    }

    public boolean isLandingAnim() {
        return isLandingAnim;
    }

    public void setLandingAnim(boolean landingAnim) {
        isLandingAnim = landingAnim;
    }

    public boolean isIdlingAnim() {
        return isIdlingAnim;
    }

    public void setIdlingAnim(boolean idlingAnim) {
        isIdlingAnim = idlingAnim;
    }

    public boolean isWalkingAnim() {
        return IsWalkingAnim;
    }

    public void setWalkingAnim(boolean walkingAnim) {
        IsWalkingAnim = walkingAnim;
    }

    //SaveData

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getDataVariant());
        compound.putBoolean("Flying", this.isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
        this.entityData.set(FLYING, compound.getBoolean("Flying"));
    }
}
