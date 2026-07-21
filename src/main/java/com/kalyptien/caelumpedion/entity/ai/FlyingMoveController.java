package com.kalyptien.caelumpedion.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class FlyingMoveController extends MoveControl {

    private final Mob parentEntity;

    public FlyingMoveController(Mob mob) {
        super(mob);
        this.parentEntity = mob;
    }

    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            Vec3 vector3d = new Vec3(this.wantedX - parentEntity.getX(), this.wantedY - parentEntity.getY(), this.wantedZ - parentEntity.getZ());
            double d0 = vector3d.length();
            double width = parentEntity.getBoundingBox().getSize();
            Vec3 vector3d1 = vector3d.scale(this.speedModifier * 0.05D / d0);
            parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().add(vector3d1).scale(0.95D).add(0, -0.01, 0));
            if (d0 < width) {
                this.operation = Operation.WAIT;
            } else if (d0 >= width) {
                float yaw = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                parentEntity.setYRot(Mth.approachDegrees(parentEntity.getYRot(), yaw, 8));
            }
        }
    }
}
