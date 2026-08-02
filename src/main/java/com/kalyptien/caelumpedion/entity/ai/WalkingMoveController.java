package com.kalyptien.caelumpedion.entity.ai;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.phys.Vec3;

public class WalkingMoveController  extends MoveControl {

    private FlyingBirdEntity bird;

    public WalkingMoveController(FlyingBirdEntity bird) {
        super(bird);
        this.bird = bird;
    }

    public void tick() {
        if (this.bird.canMove()) {
            super.tick();
        }
        else {
            bird.getNavigation().stop();
            this.setWantedPosition(bird.getX(), bird.getY(), bird.getZ(), 0.0);
        }
    }
}
