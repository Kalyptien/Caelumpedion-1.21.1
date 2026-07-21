package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class OrganizeBoidSchoolingGoal extends Goal {
    private final SocialFlyingBirdEntity mob;
    private static final int INTERVAL_TICKS = 200;
    private int nextStartTick;

    public OrganizeBoidSchoolingGoal(SocialFlyingBirdEntity boidSocialBirdEntity) {
        this.mob = boidSocialBirdEntity;
        this.nextStartTick = this.nextStartTick(boidSocialBirdEntity);
    }

    protected int nextStartTick(SocialFlyingBirdEntity abstractSchoolingSocialBird) {
        return FollowFlockLeaderGoal.reducedTickDelay(INTERVAL_TICKS + abstractSchoolingSocialBird.getRandom().nextInt(INTERVAL_TICKS) % 20);
    }

    @Override
    public boolean canUse() {
        if(!this.mob.isFlying()) return false;

        if (this.mob.hasFollowers()) return false;

        if (this.mob.isFollower()) return true;

        if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        }
        this.nextStartTick = this.nextStartTick(this.mob);
        Predicate<SocialFlyingBirdEntity> predicate = abstractSchoolingSocialBird -> (abstractSchoolingSocialBird.canBeFollowed() || !abstractSchoolingSocialBird.isFollower()) && abstractSchoolingSocialBird.isFlying();
        List<? extends SocialFlyingBirdEntity> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(16.0, 16.0, 16.0), predicate);
        SocialFlyingBirdEntity abstractSchoolingSocialBird2 = DataFixUtils.orElse(list.stream().filter(SocialFlyingBirdEntity::canBeFollowed).findAny(), this.mob);
        abstractSchoolingSocialBird2.addFollowers(list.stream().filter(abstractSchoolingSocialBird -> !abstractSchoolingSocialBird.isFollower()));
        return this.mob.isFollower();
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isFollower() && this.mob.inRangeOfLeader() && this.mob.isFlying();
    }

    @Override
    public void stop() {
        this.mob.stopFollowing();
    }
}
