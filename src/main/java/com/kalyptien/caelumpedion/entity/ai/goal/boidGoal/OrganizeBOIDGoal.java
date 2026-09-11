package com.kalyptien.caelumpedion.entity.ai.goal.boidGoal;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class OrganizeBOIDGoal extends Goal {
    private final SocialFlyingBirdEntity socialBird;

    public OrganizeBOIDGoal(SocialFlyingBirdEntity SocialBirdEntity) {
        this.socialBird = SocialBirdEntity;
    }

    @Override
    public boolean canUse() {
        if (this.socialBird.hasFollowers()) return false;

        if (this.socialBird.isFollower()) return true;

        Predicate<SocialFlyingBirdEntity> predicate = abstractSchoolingSocialBird -> (abstractSchoolingSocialBird.canBeFollowed() || !abstractSchoolingSocialBird.isFollower());
        List<? extends SocialFlyingBirdEntity> list = this.socialBird.level().getEntitiesOfClass(this.socialBird.getClass(), this.socialBird.getBoundingBox().inflate(socialBird.getViewRange(), socialBird.getViewRange(), socialBird.getViewRange()), predicate);
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
