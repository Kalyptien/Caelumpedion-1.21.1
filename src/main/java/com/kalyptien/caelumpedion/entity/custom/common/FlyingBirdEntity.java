package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.entity.ai.FlightPathNavigator;
import com.kalyptien.caelumpedion.entity.ai.FlyingMoveController;
import com.kalyptien.caelumpedion.entity.ai.WalkingMoveController;
import com.kalyptien.caelumpedion.entity.ai.goal.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import java.util.function.Predicate;

public abstract class FlyingBirdEntity extends Animal {

    //Global var

    protected int viewRange = 32;

    //Variant var

    protected static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.INT);

    //Anim var

    protected boolean isFlyingAnim = false;
    protected boolean isPlaningAnim = false;
    protected boolean isLandingAnim = false;

    protected boolean isIdlingAnim = false;
    protected boolean isEatingAnim = false;

    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState idleLookAnimationState = new AnimationState();

    protected int idleAnimationTimeout = 0;
    protected int idleAnimationTimein = 0;

    protected int eatAnimationTimeout = 0;
    protected int eatAnimationTimein = 0;

    //Enum var

    AquaticBirdType aquaticBirdType = AquaticBirdType.NONE;
    FlyingBirdType flyingBirdType = FlyingBirdType.WALKER;
    StressBirdType stressBirdType = StressBirdType.RUNNER;

    //Stress Var

    protected static final EntityDataAccessor<Integer> INITIAL_STRESS =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> CURRENT_STRESS =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> PANIC_MODE =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.BOOLEAN);

    protected int stressStep = 1;
    protected boolean needToFlyAway = false;

    //Flying Var

    protected static final EntityDataAccessor<Boolean> FLYING =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.BOOLEAN);

    protected List<Vec3> nextNavigationArray = new ArrayList<Vec3>();

    protected float flySpeed = 2f;
    protected int flyHeight = 50;
    protected int flyRange = 100;

    protected boolean isLandNavigator;

    private float flightPitch = 0;
    private float prevFlightPitch = 0;
    private float flightRoll = 0;
    private float prevFlightRoll = 0;

    private float flyProgress;
    private float prevFlyProgress;

    public FlyingBirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(FLYING, false);
        builder.define(INITIAL_STRESS, 0);
        builder.define(CURRENT_STRESS, 0);
        builder.define(PANIC_MODE, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BirdFlyGoal(this));
        this.goalSelector.addGoal(0, new PrepareFlyGoal(this));
        this.goalSelector.addGoal(0, new BirdPanicGoal(this));
        this.goalSelector.addGoal(0, new BirdPlayerNerbyGoal(this));

        this.goalSelector.addGoal(1, new FloatGoal(this));

        //this.goalSelector.addGoal(2, new BirdFoodNerbyGoal(this));

        this.goalSelector.addGoal(4, new BirdTemptGoal(this, 1.2, this::isFood, false));

        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, this.viewRange));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new WalkingMoveController(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this);
            this.navigation = new FlightPathNavigator(this, level(), 1.0F);
            this.isLandNavigator = false;
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

        if (!level().isClientSide) {
            if (this.isFlying()) {
                this.setNoGravity(true);
                if (this.isLandNavigator) {
                    switchNavigator(false);
                }
            } else {
                this.setNoGravity(false);
                if (!this.isLandNavigator) {
                    this.clearNextNavigationArray();
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
        if(!this.isFlying()){
            if(this.idleAnimationTimeout <= 0 && !this.isEatingAnim) {
                if(this.onGround() && this.isLandNavigator){

                    this.resetAnimations();

                    if(Math.random() >= 0.5){
                        this.idleLookAnimationState.start(this.tickCount);
                    }
                    else{
                        this.eatAnimationState.start(this.tickCount);
                    }

                    this.isIdlingAnim = true;
                    this.idleAnimationTimein = 0;
                    this.idleAnimationTimeout = (int)Math.round(500 * Math.random()) + 100;

                    this.lessCurrentStress(1);

                    this.getNavigation().stop();
                    this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0);

                    this.gameEvent(GameEvent.ENTITY_ACTION);
                }
            } else {
                --this.idleAnimationTimeout;
                this.idleAnimationTimein++;

                if(this.idleAnimationTimein >= 200){
                    this.idleAnimationTimein = 0;
                    this.isIdlingAnim = false;
                    this.resetAnimations();
                }

            }

            if(!this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && !this.isIdlingAnim){
                if(this.onGround() && this.isLandNavigator){

                    this.resetAnimations();

                    this.eatAnimationState.start(this.tickCount);

                    this.isEatingAnim = true;
                    this.eatAnimationTimein = 0;

                    this.lessCurrentStress(1);

                    this.getNavigation().stop();
                    this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0);

                    this.gameEvent(GameEvent.ENTITY_ACTION);
                }
            }
            else{
                this.eatAnimationTimein++;

                if(this.eatAnimationTimein >= 200){
                    this.eatAnimationTimein = 0;
                    this.isIdlingAnim = false;
                    this.resetAnimations();

                    this.lessCurrentStress(1);
                    this.lessInitialStress(1);
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
        }

        if(isFlying()){
            if(this.idleLookAnimationState.isStarted()) {
                this.isIdlingAnim = false;
                this.idleLookAnimationState.stop();
            }
            if(this.eatAnimationState.isStarted()){
                this.isIdlingAnim = false;
                this.isEatingAnim = false;
                this.eatAnimationState.stop();
            }
        }
    }

    public void resetAnimations(){
        if(this.idleLookAnimationState.isStarted()) {
            this.isIdlingAnim = false;
            this.idleLookAnimationState.stop();
        }
        if(this.eatAnimationState.isStarted()){
            this.isIdlingAnim = false;
            this.isEatingAnim = false;
            this.eatAnimationState.stop();
        }
    }

    public boolean canMove() {
        return !this.isIdlingAnim() && !this.isEatingAnim();
    }

    //Food/Breed

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Tags.Items.SEEDS);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isFood(itemstack)) {
            if (!this.level().isClientSide) {
                this.usePlayerItem(player, hand, itemstack);
                this.addParticlesAroundSelf(ParticleTypes.HEART);

                this.lessInitialStress(1);
                this.lessCurrentStress(1);

                return InteractionResult.SUCCESS;
            }

            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }

        return super.mobInteract(player, hand);
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            int i = itemstack.getCount();
            if (i > 1) {
                this.dropItemStack(itemstack.split(i - 1));
            }

            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(itemEntity, itemstack.getCount());
            itemEntity.discard();
        }
    }

    public void dropItemStack(ItemStack stack) {
        ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), stack);
        this.level().addFreshEntity(itementity);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemstack.isEmpty()) {
            this.spawnAtLocation(itemstack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

    }

    @Override
    public boolean canTakeItem(ItemStack itemstack) {
        EquipmentSlot equipmentslot = this.getEquipmentSlotForItem(itemstack);
        return !this.getItemBySlot(equipmentslot).isEmpty() ? false : equipmentslot == EquipmentSlot.MAINHAND && super.canTakeItem(itemstack);
    }

    //Misc

    protected void addParticlesAroundSelf(ParticleOptions particleOption) {
        //TODO : Dont work :c
        for(int i = 0; i < 5; ++i) {
            double d0 = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particleOption, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d0, d1, d2);
        }

    }

    //Getter / Setter

    public abstract int getIdVariant();

    public List<Vec3> getNextNavigationArray() {
        return nextNavigationArray;
    }

    public void addNextNavigationArray(Vec3 vector){
        this.nextNavigationArray.add(vector);
    }

    public void clearNextNavigationArray(){
        if(!this.nextNavigationArray.isEmpty())
            this.nextNavigationArray.removeFirst();
    }

    public void clearAllNavigationArray(){
            this.nextNavigationArray = new ArrayList<>();
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
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

    public AquaticBirdType getAquaticBirdType() {
        return this.aquaticBirdType;
    }

    public int getIdAquaticBirdType() {
        return this.aquaticBirdType.getId();
    }

    public void setAquaticBirdType(AquaticBirdType aquaticBirdType) {
        this.aquaticBirdType = aquaticBirdType;
    }

    public int getInitialStress() {
        return this.entityData.get(INITIAL_STRESS);
    }

    public void setInitialStress(int initialStress) {
        this.entityData.set(INITIAL_STRESS, initialStress);
    }

    public void addInitialStress(int value){
        this.setInitialStress(this.getInitialStress() + value);

        if(this.getCurrentStress() < this.getInitialStress()){
            this.setCurrentStress(this.getInitialStress());
        }
    }

    public void lessInitialStress(int value){
        if(this.getInitialStress() - value < 0){
            this.setInitialStress(0);
        }
        else{
            this.setInitialStress(this.getInitialStress() - value);
        }
    }

    public int getCurrentStress() {
        return this.entityData.get(CURRENT_STRESS);
    }

    public void setCurrentStress(int currentStress) {
        this.entityData.set(CURRENT_STRESS, currentStress);
    }

    public void addCurrentStress(int value){
        this.setCurrentStress(this.getCurrentStress() + (this.stressStep * value));

        if(this.getCurrentStress() >= 100){
            this.setPanicMode(true);
        }
    }

    public void lessCurrentStress(int value){
        if(this.getCurrentStress() - value < this.getInitialStress()){
            this.setCurrentStress(this.getInitialStress());
        }
        else{
            this.setCurrentStress(this.getCurrentStress() - value);
        }

        if(this.getCurrentStress() < 100){
            this.setPanicMode(false);
        }
    }

    public boolean isPanicMode() {
        return this.entityData.get(PANIC_MODE);
    }

    public void setPanicMode(boolean panicMode) {
        this.entityData.set(PANIC_MODE, panicMode);
    }

    public boolean isNeedToFlyAway() {
        return needToFlyAway;
    }

    public void setNeedToFlyAway(boolean needToFlyAway) {
        this.needToFlyAway = needToFlyAway;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public int getFlyHeight() {
        return flyHeight;
    }

    public int getFlyRange() {
        return flyRange;
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

    public boolean isEatingAnim() {
        return isEatingAnim;
    }

    public void setEatingAnim(boolean eatingAnim) {
        isEatingAnim = eatingAnim;
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
        return viewRange;
    }

    public void setViewRange(int viewRange) {
        this.viewRange = viewRange;
    }

    //SaveData

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getIdVariant());
        compound.putBoolean("Flying", this.isFlying());
        compound.putInt("Initial_Stress", this.getInitialStress());
        compound.putInt("Current_Stress", this.getInitialStress());
        compound.putBoolean("Panic_Mode", this.isPanicMode());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
        this.entityData.set(FLYING, compound.getBoolean("Flying"));
        this.entityData.set(INITIAL_STRESS, compound.getInt("Initial_Stress"));
        this.entityData.set(CURRENT_STRESS, compound.getInt("Current_Stress"));
        this.entityData.set(PANIC_MODE, compound.getBoolean("Panic_Mode"));
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
}
