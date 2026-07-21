package com.kalyptien.caelumpedion.entity.custom.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class SocialFlyingBirdEntity extends FlyingBirdEntity {

    public SocialFlyingBirdEntity leader;
    public List<SocialFlyingBirdEntity> ownSchool = new ArrayList<>();
    private int maxSchoolSize = 50;
    public int cantFollowTimer;

    public SocialFlyingBirdEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //this.goalSelector.addGoal(3, new BoidSocialBirdSchoolingGoal(this, 0.2f, 0.4f, 8 / 20f, 1 / 20f));
        //this.goalSelector.addGoal(3, new OrganizeBoidSchoolingGoal(this));
    }

    //Tick

    @Override
    public void tick() {
        super.tick();
        if (this.cantFollowTimer > 0) {
            this.cantFollowTimer--;
            this.stopFollowing();
        }
    }

    //Schooling system / BOIDS

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
        if (this.cantFollowTimer == 0) {
            this.leader = abstractSchoolingSocialBird;
            abstractSchoolingSocialBird.addToOwnSchoolFollower(this);
        }
    }

    public void stopFollowing() {
        if (this.leader != null) {
            this.leader.removeFollowerFromOwnSchool(this);
            this.leader = null;
        }
    }

    private void addToOwnSchoolFollower(SocialFlyingBirdEntity entity) {
        if (entity.cantFollowTimer == 0) this.ownSchool.add(entity);
    }

    private void removeFollowerFromOwnSchool(SocialFlyingBirdEntity entity) {
        this.ownSchool.remove(entity);
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.ownSchool.size() < this.getMaxSchoolSize() && this.cantFollowTimer == 0 && this.isFlying();
    }

    public boolean hasFollowers() {
        return this.ownSchool.size() > 1;
    }

    public void addFollowers(Stream<? extends SocialFlyingBirdEntity> stream) {
        stream.limit(this.getMaxSchoolSize() - this.ownSchool.size()).filter(boidSocialBird -> boidSocialBird != this).forEach(boidSocialBird -> boidSocialBird.startFollowing(this));
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 300.0;
    }
}
