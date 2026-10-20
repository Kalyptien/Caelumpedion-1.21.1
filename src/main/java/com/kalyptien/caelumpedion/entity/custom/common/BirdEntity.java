package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.block.entity.BirdFeederBlockEntity;
import com.kalyptien.caelumpedion.entity.ai.BirdGroundPathNavigation;
import com.kalyptien.caelumpedion.entity.ai.BirdLookController;
import com.kalyptien.caelumpedion.entity.ai.GroundMoveController;
import com.kalyptien.caelumpedion.entity.ai.goal.*;
import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.RamphastidaeEntity;
import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import com.kalyptien.caelumpedion.util.VoronoiLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.DensityFunction;
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

    protected static final EntityDataAccessor<Boolean> NEED_EAT_ANIM =
            SynchedEntityData.defineId(BirdEntity.class, EntityDataSerializers.BOOLEAN);

    protected int idleAnimationTimeout = 0;
    protected int idleAnimationTimein = 0;

    protected int eatAnimationTimein = 0;

    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState fallAnimationState = new AnimationState();

    //Anim var : Aquatic Bird

    public final AnimationState eatInWaterAnimationState = new AnimationState();
    public final AnimationState idleInWaterAnimationState = new AnimationState();

    //Enum var

    FlyingBirdEntity.AquaticBirdType aquaticBirdType = AquaticBirdType.NONE;

    //Global var

    public int featherTime;

    public BirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.featherTime = this.random.nextInt(8000) + 8000;
        this.moveControl = new GroundMoveController(this);
        this.lookControl = new BirdLookController(this);
        this.navigation = new BirdGroundPathNavigation(this, level());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(ON_ANIMATION, false);
        builder.define(NEED_EAT_ANIM, false);
    }

    @Override
    protected void registerGoals() {
        // Goal
        this.goalSelector.addGoal(0, new BirdFloatGoal(this));

        this.goalSelector.addGoal(1, new BirdPanicGoal(this, 2.0f, (bird) -> {
            return bird instanceof NeutralMob ? DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES : DamageTypeTags.PANIC_CAUSES;
        }));

        this.goalSelector.addGoal(5, new BirdTemptGoal(this, 1.25f, this::isFood, false));

        this.goalSelector.addGoal(6, new BirdFoodNerbyGoal(this));

        this.goalSelector.addGoal(8, new BirdRandomStrollGoal(this, 1.0));

        this.goalSelector.addGoal(10, new BirdLookAtPlayerGoal(this, Player.class, this.getViewRange()));
        this.goalSelector.addGoal(10, new BirdRandomLookAroundGoal(this));
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

    //AiStep

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.featherTime <= 0) {
            FeatherColor featherColor = this.getVariant().getFeatherColor(this.random.nextInt(10));
            String featherItemId = featherColor.getFeatherItemId();
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(featherItemId.split(":")[0], featherItemId.split(":")[1]));

            this.spawnAtLocation(item);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.featherTime = this.random.nextInt(8000) + 8000;
        }
    }

    // Custom Loot

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);

        int nbOfDrop = this.random.nextInt(5);
        for (int i = 0; i < nbOfDrop; i++) {
            FeatherColor featherColor = this.getVariant().getFeatherColor(this.random.nextInt(10));
            String featherItemId = featherColor.getFeatherItemId();
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(featherItemId.split(":")[0], featherItemId.split(":")[1]));

            this.spawnAtLocation(item);
        }
    }

    @Override
    protected void dropFromLootTable(DamageSource damageSource, boolean attackedRecently) {
        super.dropFromLootTable(damageSource, attackedRecently);
    }

    //Tick

    @Override
    public void tick(){
        super.tick();

        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
    }

    //Animation

    protected void setupAnimationStates() {

        if(this.isNeedEatAnimation()){
            if(!this.eatAnimationState.isStarted()){
                this.eatAnimationState.start(this.tickCount);
                this.setOnAnimation(true);
                this.eatAnimationTimein = 0;
            }
            else{
                this.eatAnimationTimein++;

                if(eatAnimationTimein >= 70){
                    this.resetAnimations();
                    this.setOnAnimation(false);
                    this.setNeedEatAnimation(false);
                }
            }
        }
        else{
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

            if(this.idleAnimationTimein >= 70){
                this.resetAnimations();
                this.setOnAnimation(false);
            }
        }
    }

    public void resetAnimations(){
        if(this.idleAnimationState.isStarted()){
            this.idleAnimationState.stop();
        }

        if(this.eatAnimationState.isStarted()){
            this.eatAnimationState.stop();
        }

        if(this.fallAnimationState.isStarted()){
            this.fallAnimationState.stop();
        }

        if(this.idleInWaterAnimationState.isStarted()){
            this.idleInWaterAnimationState.stop();
        }

        if(this.eatInWaterAnimationState.isStarted()){
            this.eatInWaterAnimationState.stop();
        }

        this.eatAnimationTimein = 0;
        this.idleAnimationTimein = 0;
        this.setOnAnimation(false);
        this.setNeedEatAnimation(false);
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

    public void pickUpItemFromGround(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        if (!itemstack.isEmpty()) {
            this.onItemPickup(itemEntity);
            itemstack.shrink(itemstack.getCount());
            if (itemstack.isEmpty()) {
                itemEntity.discard();
            }
            this.setNeedEatAnimation(true);
        }
    }

    public void pickUpItemFromFeeder(BirdFeederBlockEntity feeder, int slot) {
        ItemStack itemstack = feeder.inventory.getStackInSlot(slot);
        if (!itemstack.isEmpty()) {
            itemstack.shrink(itemstack.getCount());
            feeder.inventory.setStackInSlot(slot, itemstack);
            this.setNeedEatAnimation(true);
        }
    }

    //Getter / Setter

    public abstract int getIdVariant();

    public abstract BirdVariant getVariant();

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

    public boolean isNeedEatAnimation() {
        return this.entityData.get(NEED_EAT_ANIM);
    }

    public void setNeedEatAnimation(boolean needEatAnimation) {
        this.entityData.set(NEED_EAT_ANIM, needEatAnimation);
    }

    public double getFlySpeed() {
        return this.getAttributeValue(Attributes.FLYING_SPEED);
    }

    public double getGroundSpeed() {
        return this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    public int getViewRange() {
        return (int) this.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    //Save Data

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getIdVariant());
        compound.putInt("FeatherFallTime", this.featherTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
        if (compound.contains("FeatherFallTime")) {
            this.featherTime = compound.getInt("FeatherFallTime");
        }
    }

    // spawn

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if(spawnType == MobSpawnType.SPAWN_EGG || spawnType == MobSpawnType.SPAWNER || spawnType == MobSpawnType.TRIAL_SPAWNER){
            this.setRandomVariant();
        }
        else{
            if (spawnGroupData == null) {

                Holder<Biome> holder = level.getBiome(this.blockPosition());

                IRLRegion IrlRegion = VoronoiLevel.getVoronoiIrlRegion(holder ,this.blockPosition());

                int randomInt = random.nextInt(this.getVariantLength());

                boolean isVariantApply = false;

                //Check By IrlRegion/Biome

                isVariantApply = this.setVariantFromIrlRegionBiome(holder, IrlRegion, randomInt);

                //Check By Biome

                if(!isVariantApply) isVariantApply = this.setVariantFromBiome(holder, randomInt);

                //Check By IrlRegion

                if(!isVariantApply) isVariantApply = this.setVariantFromIrlRegion(IrlRegion, randomInt);

                //Go crazy

                if(!isVariantApply) this.setRandomVariant();

                spawnGroupData = new BirdGroupData(this.getIdVariant());
            } else {
                if(spawnGroupData instanceof BirdGroupData birdGroupData)
                    this.setVariantFromId(birdGroupData.variantId);
            }
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    protected abstract void setRandomVariant();

    protected abstract boolean setVariantFromIrlRegionBiome(Holder<Biome> holder, IRLRegion IrlRegionKey, int randomInt);

    protected abstract boolean setVariantFromBiome(Holder<Biome> holder, int randomInt);

    protected abstract boolean setVariantFromIrlRegion(IRLRegion IrlRegionKey, int randomInt);

    protected abstract void setVariantFromId(int id);

    protected abstract int getVariantLength();

    //SpawnGroup

    public static class BirdGroupData extends AgeableMob.AgeableMobGroupData {
        public final int variantId;

        public BirdGroupData(int variantId) {
            super(false);
            this.variantId = variantId;
        }
    }

    //Enum

    public interface BirdVariant{
        int getId();
        String getFileName();
        float getSize();
        boolean isIridescent();
        FeatherColor[] getFeatherColors();
        FeatherColor getFeatherColor(int id);
        FeatherColor getChildFeatherColor();
        IRLRegion[] getBiomeRegion();
        TagKey<Biome> getBiomeTagKey();
        static int lenght(){return 0;};
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
