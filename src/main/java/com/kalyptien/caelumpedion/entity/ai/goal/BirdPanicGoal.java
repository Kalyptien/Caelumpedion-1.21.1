package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

import java.util.function.Function;

public class BirdPanicGoal extends PanicGoal {

    private BirdEntity bird;

    public BirdPanicGoal(BirdEntity mob, double speedModifier, Function<PathfinderMob, TagKey<DamageType>> panicCausingDamageTypes) {
        super(mob, speedModifier, panicCausingDamageTypes);
        bird = mob;
    }

    public boolean canUse() {
        return super.canUse() && (bird instanceof FlyingBirdEntity flyingBird ? !flyingBird.isFlying() : true);
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse() && (bird instanceof FlyingBirdEntity flyingBird ? !flyingBird.isFlying() : true);
    }

    public void start() {
        super.start();

        this.bird.resetAnimations();

        if(bird instanceof FlyingBirdEntity flyingBird) flyingBird.setNeedToFlyAway(true);
    }
}
