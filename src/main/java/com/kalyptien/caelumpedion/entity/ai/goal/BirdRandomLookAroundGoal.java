package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class BirdRandomLookAroundGoal extends RandomLookAroundGoal {

    private BirdEntity bird;

    public BirdRandomLookAroundGoal(BirdEntity bird) {
        super(bird);
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
