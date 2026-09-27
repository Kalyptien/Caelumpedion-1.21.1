package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class BirdLookAtPlayerGoal extends LookAtPlayerGoal {

    private BirdEntity bird;

    public BirdLookAtPlayerGoal(BirdEntity bird, Class<? extends LivingEntity> lookAtType, float lookDistance) {
        super(bird, lookAtType, lookDistance);
        this.bird = bird;
    }

    @Override
    public boolean canUse() {
        if(!bird.isOnAnimation()){
            return super.canUse();
        }
        else{
            return false;
        }
    }
}
