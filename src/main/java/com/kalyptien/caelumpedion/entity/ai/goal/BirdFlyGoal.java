package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BirdFlyGoal extends Goal {

    private FlyingBirdEntity bird;
    private Vec3 destination;
    double speedModifier = 0;

    public BirdFlyGoal(FlyingBirdEntity entity) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.bird = entity;
    }

    @Override
    public boolean canUse() {
        if (bird.isVehicle() || bird.isPassenger()) {
            bird.clearAllNavigationArray();
        }

        if(bird.isFlying() && bird.getNextNavigationArray().isEmpty()){
            this.destination = new Vec3(bird.getX(), 0, bird.getZ());
            return true;
        }

        if(bird instanceof SocialFlyingBirdEntity){
            if(((SocialFlyingBirdEntity) bird).isFollower() && !bird.getNextNavigationArray().isEmpty()){
                ((SocialFlyingBirdEntity) bird).stopFollowing();
            }
        }

        Vec3 target = bird.getNextNavigationArray().isEmpty() ? null : bird.getNextNavigationArray().getFirst();
        if (target == null) {
            return false;
        } else {
            this.destination = new Vec3(target.x, target.y, target.z);
            return true;
        }
    }

    public void start() {
        if (!bird.canMove()) {
            bird.resetAnimations();
        }

        if(this.bird.getEyePosition().y > destination.y){
            speedModifier += (bird.getFlySpeed()/4.0f) * (destination.distanceToSqr(destination.x, destination.y, destination.z) / 100);
        }
        else{
            speedModifier += -(bird.getFlySpeed()/4.0f) * ((destination.distanceToSqr(destination.x, destination.y, destination.z) / 100) * 2);
        }

        if(speedModifier < 0){
            speedModifier = 0;
        }

        this.bird.setFlying(true);
        bird.getNavigation().moveTo(destination.x, destination.y, destination.z, bird.getFlySpeed() + speedModifier);
    }

    public void tick() {
    }

    public boolean canContinueToUse() {
        return bird.isFlying() && !bird.getNavigation().isDone();
    }

    public void stop() {
        bird.clearNextNavigationArray();

        if(bird.getNextNavigationArray().isEmpty()){
            this.speedModifier = 0;
            bird.setNeedToFlyAway(false);
            bird.setFlying(false);
        }
    }
}

