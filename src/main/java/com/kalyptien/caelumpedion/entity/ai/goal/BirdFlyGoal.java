package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BirdFlyGoal extends Goal {

    private FlyingBirdEntity entity;
    private Vec3 destination;

    public BirdFlyGoal(FlyingBirdEntity entity) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        if (entity.isVehicle() || entity.isPassenger()) {
            return false;
        }

        if(entity.isFlying() && entity.getNextNavigationArray().isEmpty()){
            entity.setFlying(false);
            return false;
        }

        Vec3 target = entity.getNextNavigationArray().isEmpty() ? null : entity.getNextNavigationArray().getFirst();
        if (target == null) {
            return false;
        } else {
            this.destination = new Vec3(target.x, target.y, target.z);
            return true;
        }
    }

    public void start() {
        this.entity.setFlying(true);
        entity.getNavigation().moveTo(destination.x, destination.y, destination.z, entity.getFlySpeed());

        entity.resetAnimations();

        if(entity.getEyePosition().y >= destination.y){
            entity.setPlaningAnim(true);
        }
        else{
            entity.setFlyingAnim(true);
        }
    }

    public void tick() {
        if(entity.getNextNavigationArray().size() == 1 && entity.getEyePosition().closerThan(destination, 3, 3)){
            entity.resetAnimations();
            entity.setLandingAnim(true);
        }
    }

    public boolean canContinueToUse() {
        return entity.isFlying() && !entity.getNavigation().isDone();
    }

    public void stop() {
        entity.clearNextNavigationArray();

        if(entity.getNextNavigationArray().isEmpty()){
            entity.setFlying(false);
        }
    }
}

