package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class BirdPanicGoal extends PanicGoal {

    private FlyingBirdEntity bird;

    public BirdPanicGoal(FlyingBirdEntity mob, double speedModifier, Function<PathfinderMob, TagKey<DamageType>> panicCausingDamageTypes) {
        super(mob, speedModifier, panicCausingDamageTypes);
        bird = mob;
    }

    public boolean canUse() {
        return super.canUse() && !bird.isFlying();
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse() && !bird.isFlying();
    }

    public void start() {
        super.start();

        bird.resetAnimations();
        this.bird.setNeedToFlyAway(true);
    }
}
