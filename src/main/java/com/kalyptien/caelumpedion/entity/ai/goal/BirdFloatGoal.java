package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class BirdFloatGoal extends FloatGoal {

    private FlyingBirdEntity bird;

    public BirdFloatGoal(FlyingBirdEntity bird) {
        super(bird);

        this.bird = bird;
    }

    public boolean canUse() {
        if(this.bird.getIdAquaticBirdType() == FlyingBirdEntity.AquaticBirdType.FULL.getId()){
            return false;
        }
        else {
            return super.canUse();
        }
    }
}
