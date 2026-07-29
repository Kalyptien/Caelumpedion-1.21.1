package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

import java.util.List;
import java.util.function.Predicate;

public class BirdPanicGoal extends PanicGoal {

    private FlyingBirdEntity bird;

    public BirdPanicGoal(FlyingBirdEntity mob) {
        super(mob, 0);
        bird = mob;
    }

    public boolean canUse() {
        if (!this.shouldPanic()) {
            return false;
        } else {
            return true;
        }
    }

    public void start() {
        this.bird.addCurrentStress(5);
        this.bird.setPanicMode(true);
        this.bird.setNeedToFlyAway(true);

        List<? extends FlyingBirdEntity> list = this.bird.level().getEntitiesOfClass(this.bird.getClass(), this.bird.getBoundingBox().inflate(bird.getViewRange(), bird.getViewRange(), bird.getViewRange()));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).addCurrentStress(2);
            list.get(i).setNeedToFlyAway(true);
        }
    }

    public void stop() {

    }

    public boolean canContinueToUse() {
        return false;
    }
}
