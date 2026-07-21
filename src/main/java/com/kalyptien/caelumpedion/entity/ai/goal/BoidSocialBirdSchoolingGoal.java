package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class BoidSocialBirdSchoolingGoal extends Goal {

    public final float separationInfluence;
    public final float separationRange;
    public final float alignmentInfluence;
    public final float cohesionInfluence;
    private final SocialFlyingBirdEntity mob;
    private int timer = 0;

    public BoidSocialBirdSchoolingGoal(SocialFlyingBirdEntity mob, float separationInfluence, float separationRange, float alignmentInfluence, float cohesionInfluence) {
        this.mob = mob;
        this.separationInfluence = separationInfluence;
        this.separationRange = separationRange;
        this.alignmentInfluence = alignmentInfluence;
        this.cohesionInfluence = cohesionInfluence;
    }

    @Override
    public boolean canUse() {
        return mob.isFlying() && (mob.isFollower() || mob.hasFollowers()) && mob.cantFollowTimer == 0;
    }

    public void tick() {
        if (mob.horizontalCollision) timer++;
        if (timer > 60) mob.cantFollowTimer = 60;

        mob.addDeltaMovement(random());
        mob.addDeltaMovement(separation());
        mob.addDeltaMovement(cohesion());
        mob.addDeltaMovement(alignment());
    }

    public Vec3 random() {
        var velocity = mob.getDeltaMovement();

        if (Mth.abs((float) velocity.x) < 0.1 && Mth.abs((float) velocity.z) < 0.1 && mob.hasFollowers())
            return new Vec3(randomSign() * 0.4, 0, randomSign() * 0.4);

        return Vec3.ZERO;
    }

    public int randomSign() {
        return mob.getRandom().nextBoolean() ? -1 : 1;
    }

    public Vec3 separation() {
        var c = Vec3.ZERO;

        for (SocialFlyingBirdEntity nearbyMob : mob.ownSchool) {
            if ((nearbyMob.position().subtract(mob.position()).length()) < separationRange && !nearbyMob.isDeadOrDying()) {
                c = c.subtract(nearbyMob.position().subtract(mob.position()));
            }
        }
        if (mob.isFollower()) {
            for (SocialFlyingBirdEntity nearbyMob : mob.leader.ownSchool) {
                if ((nearbyMob.position().subtract(mob.position()).length()) < separationRange && !nearbyMob.isDeadOrDying()) {
                    c = c.subtract(nearbyMob.position().subtract(mob.position()));
                }
            }
        }

        return c.scale(separationInfluence);
    }

    public Vec3 alignment() {
        var c = Vec3.ZERO;

        for (SocialFlyingBirdEntity nearbyMob : mob.ownSchool) {
            if (!nearbyMob.isDeadOrDying())
                c = c.add(nearbyMob.getDeltaMovement());
            c = c.scale(1f / mob.ownSchool.size());
        }
        if (mob.isFollower()) {
            for (SocialFlyingBirdEntity nearbyMob : mob.leader.ownSchool) {
                if (!nearbyMob.isDeadOrDying())
                    c = c.add(nearbyMob.getDeltaMovement());
            }
            c = c.scale(1f / mob.leader.ownSchool.size());
        }

        c = c.subtract(mob.getDeltaMovement());
        return c.scale(alignmentInfluence);
    }

    public Vec3 cohesion() {
        var c = Vec3.ZERO;

        for (SocialFlyingBirdEntity nearbyMob : mob.ownSchool) {
            if (!nearbyMob.isDeadOrDying())
                c = c.add(nearbyMob.position());
            c = c.scale(1f / mob.ownSchool.size());
        }
        if (mob.isFollower()) {
            for (SocialFlyingBirdEntity nearbyMob : mob.leader.ownSchool) {
                if (!nearbyMob.isDeadOrDying())
                    c = c.add(nearbyMob.position());
            }
            c = c.scale(1f / mob.leader.ownSchool.size());
        }

        c = c.subtract(mob.position());
        return c.scale(cohesionInfluence);
    }
}