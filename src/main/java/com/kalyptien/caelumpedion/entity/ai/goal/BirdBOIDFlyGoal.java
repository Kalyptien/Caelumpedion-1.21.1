package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BirdBOIDFlyGoal extends Goal {

    public final float separationInfluence = 0.2f;
    public final float boidRange = 1f;
    public final float alignmentInfluence= 0.5f;
    public final float cohesionInfluence = 0.15f;

    private int timeoutBeforeFlying;

    private final SocialFlyingBirdEntity socialBird;

    public BirdBOIDFlyGoal(SocialFlyingBirdEntity bird) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.socialBird = bird;
        timeoutBeforeFlying = (int)Math.round(200 * Math.random());
    }

    @Override
    public boolean canUse() {

        if (
            socialBird.isVehicle() || socialBird.isPassenger() || socialBird.hasFollowers()
        ) {
            return false;
        }

        if(socialBird.isFollower() && socialBird.leader.isFlying()){
            if(timeoutBeforeFlying <= 0){
                return true;
            }
            else{
                timeoutBeforeFlying--;
            }
        }

        return false;
    }

    @Override
    public void tick() {
        if(this.canContinueToUse()){
            SocialFlyingBirdEntity.BOIDType boidType = socialBird.getBOIDBirdType();

            if(boidType == SocialFlyingBirdEntity.BOIDType.FOLLOW){
                this.shareFollowPath();
            }
            else if(boidType == SocialFlyingBirdEntity.BOIDType.SWARM){
                this.shareSwarmPath();
            }
            else if(boidType == SocialFlyingBirdEntity.BOIDType.FORMATION){
                this.shareFormationPath();
            }
        }
    }

    @Override
    public void start() {
        socialBird.setFlying(true);
    }

    @Override
    public boolean canContinueToUse() {
        return socialBird.isFollower() && socialBird.leader.isFlying() && !socialBird.leader.getNextNavigationArray().isEmpty();
    }

    @Override
    public void stop() {
        socialBird.setFlying(false);
        this.timeoutBeforeFlying = (int)Math.round(50 * Math.random());
    }

    private void shareFollowPath(){

        //Follow the leader, but keep distance with it

        int seed = socialBird.getRandom().nextInt(100);

        Vec3 leaderDelta = socialBird.leader.getEyePosition().vectorTo(socialBird.leader.getNextNavigationArray().getFirst());

        Vec3 followerDestination;
        if(socialBird.leader.getNextNavigationArray().size() == 1){
            followerDestination = socialBird.position().add(leaderDelta.x,0,leaderDelta.z);
            Vec3 ground = groundPosition(followerDestination);

            followerDestination = new Vec3(followerDestination.x, ground.y, followerDestination.z);
        }
        else{
            followerDestination = socialBird.position().add(leaderDelta.x,leaderDelta.y,leaderDelta.z);
        }

        float influenceFromTheLeader = socialBird.distanceTo(socialBird.leader) / 100;

        double speed = socialBird.getFlySpeed() + (socialBird.getFlySpeed() * influenceFromTheLeader);

        Vec3 followerDelta = socialBird.getEyePosition().vectorTo(socialBird.leader.getNextNavigationArray().getFirst());

        followerDestination = new Vec3(followerDestination.x + (followerDelta.x * influenceFromTheLeader)
                , followerDestination.y + (followerDelta.y * influenceFromTheLeader * 0.5)
                , followerDestination.z + (followerDelta.z * influenceFromTheLeader));

        socialBird.getNavigation().moveTo(followerDestination.x + ((Nth(seed, 1)) * randomSign()),
                followerDestination.y  + ((Nth(seed, 1)) * randomSign()),
                followerDestination.z + ((Nth(seed, 2)) * randomSign()), speed);
    }

    private void shareSwarmPath(){
        //Follow the leader + BOID

        Vec3 delta = socialBird.getEyePosition().vectorTo(socialBird.leader.getEyePosition());

        float influenceFromTheLeader = socialBird.distanceTo(socialBird.leader) / 100;

        double speed = socialBird.getFlySpeed() + (socialBird.getFlySpeed() * influenceFromTheLeader);

        Vec3 followerDestination = socialBird.position().add(delta.x, delta.y, delta.z);

        followerDestination.add(random());
        followerDestination.add(separation());
        followerDestination.add(cohesion());
        followerDestination.add(alignment());

        socialBird.getNavigation().moveTo(followerDestination.x, followerDestination.y, followerDestination.z, speed);
    }

    private void shareFormationPath(){
        // TODO : Formation en V
    }

    //shareSwarmPath => BOID functions

    public Vec3 random() {
        var velocity = socialBird.getDeltaMovement();

        if (Mth.abs((float) velocity.x) < 0.1 && Mth.abs((float) velocity.z) < 0.1 && socialBird.hasFollowers())
            return new Vec3(randomSign() * 0.4, 0, randomSign() * 0.4);

        return Vec3.ZERO;
    }

    public int randomSign() {
        return socialBird.getRandom().nextBoolean() ? -1 : 1;
    }

    public Vec3 separation() {
        var c = Vec3.ZERO;

        for (SocialFlyingBirdEntity nearbyMob : socialBird.ownSchool) {
            if ((nearbyMob.position().subtract(socialBird.position()).length()) < boidRange && !nearbyMob.isDeadOrDying()) {
                c = c.subtract(nearbyMob.position().subtract(socialBird.position()));
            }
        }
        if (socialBird.isFollower()) {
            for (SocialFlyingBirdEntity nearbyMob : socialBird.leader.ownSchool) {
                if ((nearbyMob.position().subtract(socialBird.position()).length()) < boidRange && !nearbyMob.isDeadOrDying()) {
                    c = c.subtract(nearbyMob.position().subtract(socialBird.position()));
                }
            }
        }

        return c.scale(separationInfluence);
    }

    public Vec3 alignment() {
        var c = Vec3.ZERO;

        for (SocialFlyingBirdEntity nearbyMob : socialBird.ownSchool) {
            if (!nearbyMob.isDeadOrDying())
                c = c.add(nearbyMob.getDeltaMovement());
            c = c.scale(1f / socialBird.ownSchool.size());
        }
        if (socialBird.isFollower()) {
            for (SocialFlyingBirdEntity nearbyMob : socialBird.leader.ownSchool) {
                if (!nearbyMob.isDeadOrDying())
                    c = c.add(nearbyMob.getDeltaMovement());
            }
            c = c.scale(1f / socialBird.leader.ownSchool.size());
        }

        c = c.subtract(socialBird.getDeltaMovement());
        return c.scale(alignmentInfluence);
    }

    public Vec3 cohesion() {
        var c = Vec3.ZERO;

        for (SocialFlyingBirdEntity nearbyMob : socialBird.ownSchool) {
            if (!nearbyMob.isDeadOrDying())
                c = c.add(nearbyMob.position());
            c = c.scale(1f / socialBird.ownSchool.size());
        }
        if (socialBird.isFollower()) {
            for (SocialFlyingBirdEntity nearbyMob : socialBird.leader.ownSchool) {
                if (!nearbyMob.isDeadOrDying())
                    c = c.add(nearbyMob.position());
            }
            c = c.scale(1f / socialBird.leader.ownSchool.size());
        }

        c = c.subtract(socialBird.position());
        return c.scale(cohesionInfluence);
    }

    //Global functions

    public Vec3 groundPosition(Vec3 airPosition) {
        BlockPos.MutableBlockPos ground = new BlockPos.MutableBlockPos();
        ground.set(airPosition.x, airPosition.y, airPosition.z);
        boolean flag = false;
        while (ground.getY() < socialBird.level().getMaxBuildHeight() && !socialBird.level().getBlockState(ground).isSolid() && socialBird.level().getFluidState(ground).isEmpty()){
            ground.move(0, 1, 0);
            flag = true;
        }
        ground.move(0, -1, 0);
        while (ground.getY() > socialBird.level().getMinBuildHeight() && !socialBird.level().getBlockState(ground).isSolid() && socialBird.level().getFluidState(ground).isEmpty()) {
            ground.move(0, -1, 0);
        }
        return Vec3.atCenterOf(flag ? ground.above() : ground.below());
    }

    private double Nth ( int number, int index ) {
        if(number >= 10){
            return ((int)number / java.lang.Math.pow(10, index)) % 10;
        }
        return number;
    }
}
