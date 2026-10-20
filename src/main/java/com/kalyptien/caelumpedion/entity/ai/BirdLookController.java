package com.kalyptien.caelumpedion.entity.ai;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.animal.Fox;

public class BirdLookController extends LookControl {

    private BirdEntity bird;

    public BirdLookController(BirdEntity bird) {
        super(bird);
        this.bird = bird;
    }

    public void tick() {
        if (!bird.isOnAnimation()) {
            super.tick();
        }

    }
}
