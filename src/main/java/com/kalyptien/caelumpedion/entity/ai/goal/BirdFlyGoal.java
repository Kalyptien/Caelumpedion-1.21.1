package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BirdFlyGoal extends Goal {

    private FlyingBirdEntity entity;
    private double x;
    private double y;
    private double z;

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
        }

        Vec3 target = entity.getNextNavigationArray().isEmpty() ? null : entity.getNextNavigationArray().getFirst();
        if (target == null) {
            return false;
        } else {
            this.x = target.x;
            this.y = target.y;
            this.z = target.z;
            return true;
        }
    }

    public void start() {
        this.entity.setFlying(true);
        entity.getNavigation().moveTo(this.x, this.y, this.z, entity.getFlySpeed());
    }

    public void tick() {
        if (entity.isFlying() && entity.onGround() && entity.getTimeFlying() > 40) {
            entity.setFlying(false);
        }
    }

    public boolean canContinueToUse() {
        return entity.isFlying() && !entity.getNavigation().isDone() && entity.getGroundedFor() <= 0;
    }

    public void stop() {
        entity.removeNextNavigationArray();

        if(entity.getNextNavigationArray().isEmpty()){
            entity.setFlying(false);
        }
    }
}

