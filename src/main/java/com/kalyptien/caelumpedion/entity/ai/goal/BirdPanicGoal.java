package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
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
            return this.findRandomPosition();
        }
    }

    public void start() {
        super.start();

        if (!bird.canMove()) {
            bird.resetAnimations();
        }

        if(bird.getStressBirdType() == FlyingBirdEntity.StressBirdType.RUNNER){
            this.bird.setNeedToFlyAway(true);
        }

        if(!bird.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()){
            this.bird.dropEquipment();
        }

        List<? extends FlyingBirdEntity> list = this.bird.level().getEntitiesOfClass(this.bird.getClass(), this.bird.getBoundingBox().inflate(bird.getViewRange(), bird.getViewRange(), bird.getViewRange()));
        for (int i = 0; i < list.size(); i++) {

            if (!list.get(i).canMove()) {
                list.get(i).resetAnimations();
            }

            if(bird.getStressBirdType() == FlyingBirdEntity.StressBirdType.RUNNER){
                list.get(i).setNeedToFlyAway(true);
            }

            if(!list.get(i).getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()){
                list.get(i).dropEquipment();
            }
        }
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse() && !bird.isFlying();
    }
}
