package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BirdFlyGoal extends Goal {

    private FlyingBirdEntity bird;
    private Vec3 destination;

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
            bird.setFlying(false);
            return false;
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

        this.bird.setFlying(true);
        bird.getNavigation().moveTo(destination.x, destination.y, destination.z, bird.getFlySpeed());
    }

    public void tick() {
    }

    public boolean canContinueToUse() {
        return bird.isFlying() && !bird.getNavigation().isDone();
    }

    public void stop() {
        bird.clearNextNavigationArray();

        if(bird.getNextNavigationArray().isEmpty()){
            bird.lessCurrentStress(1);
            bird.setNeedToFlyAway(false);
            bird.setFlying(false);
        }
    }
}

