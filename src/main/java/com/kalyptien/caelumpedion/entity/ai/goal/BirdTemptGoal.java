package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class BirdTemptGoal extends TemptGoal {

    BirdEntity bird;

    public BirdTemptGoal(BirdEntity mob, double speedModifier, Predicate<ItemStack> items, boolean canScare) {
        super(mob, speedModifier, items, canScare);
        this.bird = mob;
    }

    public boolean canUse() {

        if (bird instanceof FlyingBirdEntity flyingBird &&  flyingBird.isFlying()){
            return false;
        }

        return super.canUse();
    }
}
