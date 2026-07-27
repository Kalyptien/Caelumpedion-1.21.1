package com.kalyptien.caelumpedion.entity.custom.common;

import com.kalyptien.caelumpedion.entity.ai.goal.OrganizeBOIDGoal;
import com.kalyptien.caelumpedion.entity.ai.goal.BirdBOIDFlyGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

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

    private boolean hadShareIsNextDestinations = false;

    public SocialFlyingBirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new BirdBOIDFlyGoal(this));
        this.goalSelector.addGoal(1, new OrganizeBOIDGoal(this));
    }

    //Tick

    @Override
    public void tick() {
        super.tick();
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
        return Math.sqrt(this.distanceToSqr(this.leader)) <= 30;
    }

    public boolean hadShareIsNextDestinations() {
        return hadShareIsNextDestinations;
    }

    public void setHadShareIsNextDestinations(boolean hadShareIsNextDestinations) {
        this.hadShareIsNextDestinations = hadShareIsNextDestinations;
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
