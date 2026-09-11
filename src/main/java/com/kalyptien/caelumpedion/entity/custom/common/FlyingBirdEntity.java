package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.entity.ai.FlightPathNavigator;
import com.kalyptien.caelumpedion.entity.ai.FlyingMoveController;
import com.kalyptien.caelumpedion.entity.ai.goal.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class FlyingBirdEntity extends Animal {

    //Variant var

    protected static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.INT);

    //Anim var

    protected boolean isIdlingAnim = false;
    protected boolean isEatingAnim = false;

    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    protected int idleAnimationTimeout = 0;
    protected int idleAnimationTimein = 0;

    protected int eatAnimationTimein = 0;

    //Anim var : Aquatic Bird

    //Enum var

    AquaticBirdType aquaticBirdType = AquaticBirdType.NONE;
    FlyingBirdType flyingBirdType = FlyingBirdType.WALKER;
    StressBirdType stressBirdType = StressBirdType.RUNNER;
    FlyPathType flyPathType = FlyPathType.NORMAL;

    //Flying Var

    protected static final EntityDataAccessor<Boolean> FLYING =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ON_MIGRATION =
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

    protected boolean needToFlyAway = false;

    public FlyingBirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(FLYING, false);
        builder.define(ON_MIGRATION, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BirdFloatGoal(this));

        this.goalSelector.addGoal(1, new BirdPanicGoal(this, 2.0, (bird) -> {
            return ((FlyingBirdEntity) bird).getIdStressBirdType() == StressBirdType.RUNNER.id ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES;
        }));

        this.goalSelector.addGoal(3, new BirdTemptGoal(this, 2.0f, this::isFood, false));

        this.goalSelector.addGoal(6, new BirdAvoidEntityGoal(this, Player.class, this.getViewRange(), 2.0f, 4.0f, (entity) -> {
            return !((Player)entity).isCrouching();
        }));

        this.goalSelector.addGoal(8, new BirdRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new BirdRandomFlyingGoal(this, 1.0));

        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, this.getViewRange()));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this);
            this.navigation = new FlightPathNavigator(this, level(), 1.0F);
            this.isLandNavigator = false;
        }
    }

    //Misc

    public boolean canBeLeashed() {
        return false;
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return distanceToClosestPlayer >= 100 && this.tickCount > 2400;
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

        if (!level().isClientSide) {
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

        this.setupAnimationStates();
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

    //Animation

    protected void setupAnimationStates() {
        //If Not flying
        if(!this.isFlying()){
            if(this.idleAnimationTimeout <= 0 && !this.isEatingAnim && !this.isIdlingAnim) {
                if(this.onGround() && this.isLandNavigator){
                    this.resetAnimations();

                    if(Math.random() >= 0.5){
                        this.idleAnimationState.start(this.tickCount);
                    }
                    else{
                        this.eatAnimationState.start(this.tickCount);
                    }

                    this.isIdlingAnim = true;
                    this.idleAnimationTimein = 0;
                    this.idleAnimationTimeout = (int)Math.round(500 * Math.random()) + 500;

                    this.gameEvent(GameEvent.ENTITY_ACTION);
                }
            } else {
                --this.idleAnimationTimeout;

                if(this.isIdlingAnim){
                    this.idleAnimationTimein++;

                    if(this.idleAnimationTimein >= 100){
                        this.idleAnimationTimein = 0;
                        this.isIdlingAnim = false;
                        this.resetAnimations();
                    }
                }
            }
        }
        else{
            this.resetAnimations();
        }
    }

    public void resetAnimations(){
        if(this.idleAnimationState.isStarted()) {
            this.isIdlingAnim = false;
            this.idleAnimationState.stop();
        }

        if(this.eatAnimationState.isStarted()){
            this.isIdlingAnim = false;
            this.isEatingAnim = false;
            this.eatAnimationState.stop();
        }
    }

    public boolean inAnimation() {
        return !this.isIdlingAnim && !this.isEatingAnim;
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

    public StressBirdType getStressBirdType() {
        return this.stressBirdType;
    }

    public int getIdStressBirdType() {
        return this.stressBirdType.getId();
    }

    public void setStressBirdType(StressBirdType stressBirdType) {
        this.stressBirdType = stressBirdType;
    }

    public FlyPathType getFlyPathType() {
        return this.flyPathType;
    }

    public int getIdFlyPathType() {
        return this.flyPathType.getId();
    }

    public void setFlyPathType(FlyPathType flypathType) {
        this.flyPathType = flypathType;
    }

    public AquaticBirdType getAquaticBirdType() {
        return this.aquaticBirdType;
    }

    public int getIdAquaticBirdType() {
        return this.aquaticBirdType.getId();
    }

    public void setAquaticBirdType(AquaticBirdType aquaticBirdType) {
        this.aquaticBirdType = aquaticBirdType;
    }

    public boolean isNeedToFlyAway() {
        return needToFlyAway;
    }

    public void setNeedToFlyAway(boolean needToFlyAway) {
        this.needToFlyAway = needToFlyAway;
    }

    public double getFlySpeed() {
        return this.getAttributeValue(Attributes.FLYING_SPEED);
    }

    public double getGroundSpeed() {
        return this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    public double getFollowRange(){
        return this.getAttributeValue(Attributes.FOLLOW_RANGE);
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

    public int getViewRange() {
        return (int) this.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    //SaveData

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getIdVariant());
        compound.putBoolean("Flying", this.isFlying());
        compound.putBoolean("OnMigration", this.isOnMigration());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
        this.entityData.set(FLYING, compound.getBoolean("Flying"));
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

    public static enum StressBirdType {
        RUNNER(0),
        FIGHTER(1);

        private static final StressBirdType[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(StressBirdType::getId)).toArray(StressBirdType[]::new);
        private final int id;

        StressBirdType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static StressBirdType byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }

    public static enum FlyPathType {
        NORMAL(0),
        CHAOS(1),
        NEAR_GROUND(2);

        private static final FlyPathType[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(FlyPathType::getId)).toArray(FlyPathType[]::new);
        private final int id;

        FlyPathType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static FlyPathType byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }
}
