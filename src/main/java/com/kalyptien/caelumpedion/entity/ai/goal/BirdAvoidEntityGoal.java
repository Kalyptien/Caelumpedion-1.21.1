package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

import java.util.function.Predicate;

public class BirdAvoidEntityGoal extends AvoidEntityGoal {

    private FlyingBirdEntity bird;

    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;

    public BirdAvoidEntityGoal(FlyingBirdEntity bird, Class entityClassToAvoid, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier, Predicate predicateOnAvoidEntity) {
        super(bird, entityClassToAvoid, maxDistance, walkSpeedModifier, sprintSpeedModifier, predicateOnAvoidEntity);
        this.bird = bird;

        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
    }

    public void tick() {
        if (this.mob.distanceToSqr(this.toAvoid) < 49.0) {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
            this.bird.setNeedToFlyAway(true);
        } else {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }

    }
}
