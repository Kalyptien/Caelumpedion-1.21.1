package com.kalyptien.caelumpedion.entity.custom;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public class PasseriformeEntity extends SocialFlyingBirdEntity {

    public final AnimationState idlePickAnimationState = new AnimationState();
    public final AnimationState idleLookAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public PasseriformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6d)
                .add(Attributes.MOVEMENT_SPEED, 0.20D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 12D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1 ,new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
    }

    @Override
    public void tick() {
        super.tick();
    }

    // ANIM

    protected void setupAnimationStates() {
        super.setupAnimationStates();

        if(!this.isFlying()){
            if(this.idleAnimationTimeout <= 0) {
                if(!this.idleLookAnimationState.isStarted() || !this.idlePickAnimationState.isStarted()){

                    if(Math.random() >= 0.5){
                        this.idleLookAnimationState.start(this.tickCount);
                    }
                    else{
                        this.idlePickAnimationState.start(this.tickCount);
                    }

                    this.idleAnimationTimeout = (int)Math.round(500 * Math.random());
                    this.gameEvent(GameEvent.ENTITY_ACTION);
                }
            } else {

                if(this.idleLookAnimationState.isStarted()) {
                    this.idleLookAnimationState.stop();
                }
                if(this.idlePickAnimationState.isStarted()){
                    this.idlePickAnimationState.stop();
                }

                --this.idleAnimationTimeout;
            }
        }

        if(isFlying()){

            if(this.idleLookAnimationState.isStarted()) {
                this.idleLookAnimationState.stop();
            }
            if(this.idlePickAnimationState.isStarted()){
                this.idlePickAnimationState.stop();
            }
        }
    }

    //Getter / Setter

    public int getDataVariant() {
        return this.entityData.get(VARIANT);
    }

    public PasseriformeVariant getVariant() {
        return PasseriformeVariant.byId(this.getDataVariant());
    }

    public void setVariant(PasseriformeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if(spawnType == MobSpawnType.SPAWN_EGG){
            PasseriformeVariant variant = Util.getRandom(PasseriformeVariant.values(), this.random);
            this.setVariant(variant);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Variant

    public static enum PasseriformeVariant {
        CardinalisCardinalis(0),
        CyanistesCaeruleus(1),
        CyanocittaCristata(2),
        ErithacusRubecula(3),
        LophophanesCristatus(4),
        PasserDomesticus(5),
        PeriparusAter(6),
        PhoenicurusOchruros(7),;

        private static final PasseriformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(PasseriformeVariant::getId)).toArray(PasseriformeVariant[]::new);
        private final int id;

        PasseriformeVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static PasseriformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }
}
