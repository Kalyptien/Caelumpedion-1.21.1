package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class OrganizeBOIDGoal extends Goal {
    private final SocialFlyingBirdEntity socialBird;
    private static final int INTERVAL_TICKS = 200;
    private int nextStartTick;

    public OrganizeBOIDGoal(SocialFlyingBirdEntity SocialBirdEntity) {
        this.socialBird = SocialBirdEntity;
        this.nextStartTick = this.nextStartTick(SocialBirdEntity);
    }

    protected int nextStartTick(SocialFlyingBirdEntity abstractSchoolingSocialBird) {
        return FollowFlockLeaderGoal.reducedTickDelay(INTERVAL_TICKS + abstractSchoolingSocialBird.getRandom().nextInt(INTERVAL_TICKS) % 30);
    }

    @Override
    public boolean canUse() {
        if (this.socialBird.hasFollowers()) return false;

        if (this.socialBird.isFollower()) return true;

        if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        }

        this.nextStartTick = this.nextStartTick(this.socialBird);
        Predicate<SocialFlyingBirdEntity> predicate = abstractSchoolingSocialBird -> (abstractSchoolingSocialBird.canBeFollowed() || !abstractSchoolingSocialBird.isFollower());
        List<? extends SocialFlyingBirdEntity> list = this.socialBird.level().getEntitiesOfClass(this.socialBird.getClass(), this.socialBird.getBoundingBox().inflate(32.0, 32.0, 32.0), predicate);
        SocialFlyingBirdEntity abstractSchoolingSocialBird2 = DataFixUtils.orElse(list.stream().filter(SocialFlyingBirdEntity::canBeFollowed).findAny(), this.socialBird);
        abstractSchoolingSocialBird2.addFollowers(list.stream().filter(abstractSchoolingSocialBird -> !abstractSchoolingSocialBird.isFollower()));
        return this.socialBird.isFollower();
    }

    @Override
    public boolean canContinueToUse() {
        return this.socialBird.isFollower() && this.socialBird.inRangeOfLeader();
    }

    @Override
    public void stop() {
        this.socialBird.stopFollowing();
    }
}
