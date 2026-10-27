package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.entity.ai.goal.boidGoal.OrganizeBOIDGoal;
import com.kalyptien.caelumpedion.entity.ai.goal.boidGoal.BirdBOIDFlyGoal;
import com.kalyptien.caelumpedion.util.CaelumpedionTags;
import net.minecraft.core.Holder;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public abstract class SocialFlyingBirdEntity extends FlyingBirdEntity {

    public SocialFlyingBirdEntity leader;
    public List<SocialFlyingBirdEntity> ownSchool = new ArrayList<>();

    BOIDType boidType = BOIDType.FOLLOW;

    protected int maxSchoolSize = 25;

    public SocialFlyingBirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new BirdBOIDFlyGoal(this));
        this.goalSelector.addGoal(6, new OrganizeBOIDGoal(this));
    }

    //BOIDS Getter/Setter

    public int getMaxSchoolSize() {
        return maxSchoolSize;
    }
    public void SetMaxSchoolSize(int i) {
        this.maxSchoolSize = i;
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public void startFollowing(SocialFlyingBirdEntity abstractSchoolingSocialBird) {
            this.leader = abstractSchoolingSocialBird;
            abstractSchoolingSocialBird.addToOwnSchoolFollower(this);
    }

    public void stopFollowing() {
        if (this.leader != null) {
            this.leader.removeFollowerFromOwnSchool(this);
            this.leader = null;
        }
    }

    private void addToOwnSchoolFollower(SocialFlyingBirdEntity entity) {
        this.ownSchool.add(entity);
    }

    private void removeFollowerFromOwnSchool(SocialFlyingBirdEntity entity) {
        this.ownSchool.remove(entity);
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.ownSchool.size() < this.getMaxSchoolSize();
    }

    public boolean hasFollowers() {
        return this.ownSchool.size() > 1;
    }

    public void addFollowers(Stream<? extends SocialFlyingBirdEntity> stream) {
        stream.limit(this.getMaxSchoolSize() - this.ownSchool.size()).filter(boidSocialBird -> boidSocialBird != this).forEach(boidSocialBird -> boidSocialBird.startFollowing(this));
    }

    public boolean inRangeOfLeader() {
        return Math.sqrt(this.distanceToSqr(this.leader)) <= (this.getViewRange()*2) * (this.getViewRange()*2);
    }

    public BOIDType getBOIDBirdType() {
        return this.boidType;
    }

    public int getIdBOIDBirdType() {
        return this.boidType.getId();
    }

    public void setBOIDBirdType(BOIDType boidType) {
        this.boidType = boidType;
    }

    //Getter/Setter

    public void setNeedToFlyAway(boolean needToFlyAway) {
        super.setNeedToFlyAway(needToFlyAway);

        if(this.leader != null && !this.leader.isNeedToFlyAway() && needToFlyAway){
            this.leader.setNeedToFlyAway(true);
        }
    }

    //Spawn

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData) {

        if(!(spawnGroupData instanceof BirdGroupData)){
            spawnGroupData = new BirdGroupData();
        }

        //BOID
        if(!(spawnType == MobSpawnType.SPAWN_EGG || spawnType == MobSpawnType.SPAWNER || spawnType == MobSpawnType.TRIAL_SPAWNER)){
            if(spawnGroupData instanceof BirdGroupData birdGroupData){
                if(!birdGroupData.leaderSet){
                    birdGroupData.setLeader(this);
                }
                else{
                    this.startFollowing(birdGroupData.leader);
                }
            }
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    //Enum

    public static enum BOIDType {
        FOLLOW(0),
        SWARM(1),
        FORMATION(2);

        private static final BOIDType[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(BOIDType::getId)).toArray(BOIDType[]::new);
        private final int id;

        BOIDType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static BOIDType byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }
}
