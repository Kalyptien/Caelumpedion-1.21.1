package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.entity.ai.FlightPathNavigator;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class FlyingBirdEntity extends Animal {
    protected static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> FLYING =
            SynchedEntityData.defineId(FlyingBirdEntity.class, EntityDataSerializers.BOOLEAN);

    //Anim Boolean

    protected boolean isFlyingAnim = false;
    protected boolean isPlaningAnim = false;
    protected boolean isLandingAnim = false;

    protected boolean isIdlingAnim = false;

    // Enum var

    AquaticBirdType aquaticBirdType = AquaticBirdType.NONE;
    FlyingBirdType flyingBirdType = FlyingBirdType.WALKER;

    //Flying Var

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
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BirdFlyGoal(this));

        this.goalSelector.addGoal(1, new FloatGoal(this));

        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 32.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(5, new PrepareFlyGoal(this));

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
    }

    public void resetAnimations(){
        this.setFlyingAnim(false);
        this.setPlaningAnim(false);
        this.setLandingAnim(false);
    }

    public boolean canMove() {
        return !this.isIdlingAnim();
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

    public AquaticBirdType getAquaticBirdType() {
        return this.aquaticBirdType;
    }

    public int getIdAquaticBirdType() {
        return this.aquaticBirdType.getId();
    }

    public void setAquaticBirdType(AquaticBirdType aquaticBirdType) {
        this.aquaticBirdType = aquaticBirdType;
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

    public float getFlightPitch(float partialTick) {
        return (prevFlightPitch + (flightPitch - prevFlightPitch) * partialTick);
    }

    public float getFlightRoll(float partialTick) {
        return (prevFlightRoll + (flightRoll - prevFlightRoll) * partialTick);
    }

    public float getFlyProgress(float partialTick) {
        return (prevFlyProgress + (flyProgress - prevFlyProgress) * partialTick) * 0.2F;
    }

    //SaveData

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getIdVariant());
        compound.putBoolean("Flying", this.isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
        this.entityData.set(FLYING, compound.getBoolean("Flying"));
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
}
