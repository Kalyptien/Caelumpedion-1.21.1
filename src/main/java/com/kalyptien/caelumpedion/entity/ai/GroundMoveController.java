package com.kalyptien.caelumpedion.entity.ai;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.animal.Fox;

public class GroundMoveController extends MoveControl{

    private BirdEntity bird;

    public GroundMoveController(BirdEntity bird) {
        super(bird);
        this.bird = bird;
    }

    public void tick() {
        if (!bird.isOnAnimation()) {
            super.tick();
        }
    }
}
