package com.kalyptien.caelumpedion.entity.ai;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class WalkingMoveController  extends MoveControl {

    public WalkingMoveController(FlyingBirdEntity mob) {
        super(mob);
    }

    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO && mob.onGround()) {
            ((FlyingBirdEntity) mob).setWalkingAnim(true);
        }
        else{
            ((FlyingBirdEntity) mob).setWalkingAnim(false);
        }
    }
}
