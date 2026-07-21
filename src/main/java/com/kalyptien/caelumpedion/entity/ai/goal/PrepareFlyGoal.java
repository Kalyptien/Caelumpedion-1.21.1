package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PrepareFlyGoal extends Goal {

    private FlyingBirdEntity entity;

    private boolean shortFly;

    public PrepareFlyGoal(FlyingBirdEntity entity, boolean shortFly) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.entity = entity;
        this.shortFly = shortFly;
    }

    @Override
    public boolean canUse() {
        if (entity.isVehicle() || entity.isPassenger()) {
            return false;
        }

        if(!entity.getNextNavigationArray().isEmpty() || entity.isFlying()){
            return false;
        }

        if (!entity.isFlying() && entity.getRandom().nextInt(100) != 0 && shortFly) {
            return false;
        }

        if (!entity.isFlying() && entity.getRandom().nextInt(200) != 0 && !shortFly) {
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        this.findFlightPos();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    private void findFlightPos() {

        int range;
        int height;
        int numberOfMiddleDestinations;

        Vec3 finalHeightAdjusted;

        if(shortFly){
            range = entity.getShortFlyRange();
            height = entity.getShortFlyHeight();
            numberOfMiddleDestinations = 1;

            finalHeightAdjusted = entity.position().add(entity.getRandom().nextInt(range * 2) - range, 0, entity.getRandom().nextInt(range * 2) - range);
        }
        else{
            range = entity.getLongFlyRange();
            height = entity.getLongFlyHeight();
            numberOfMiddleDestinations = entity.getRandom().nextInt(5) + 1;

            finalHeightAdjusted = entity.position().add(entity.getRandom().nextInt(range * 2) + range/2, 0, entity.getRandom().nextInt(range * 2) + range/2);
        }

        //Final Destination

        Vec3 finalGround = groundPosition(finalHeightAdjusted);
        finalHeightAdjusted = new Vec3(finalHeightAdjusted.x, finalGround.y, finalHeightAdjusted.z);

        BlockHitResult finalResult = entity.level().clip(new ClipContext(entity.getEyePosition(), finalHeightAdjusted, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        Vec3 finalDestination = finalResult.getLocation();

        // Middle Destination

        Vec3 middleDestination;

        for (int i = numberOfMiddleDestinations; i > 0; i--) {

            Vec3 middleHeightAdjusted = entity.position().add(
                    (entity.getRandom().nextInt(Math.abs((int)finalDestination.x / i)) + entity.getEyePosition().x) * Integer.signum((int)finalDestination.x),
                    0,
                    (entity.getRandom().nextInt(Math.abs((int)finalDestination.z / i)) + entity.getEyePosition().x) * Integer.signum((int)finalDestination.z));

            Vec3 ground = groundPosition(middleHeightAdjusted);
            middleHeightAdjusted = new Vec3(middleHeightAdjusted.x, ground.y + height + entity.getRandom().nextInt(height), middleHeightAdjusted.z);

            BlockHitResult middleResult = entity.level().clip(new ClipContext(entity.getEyePosition(), middleHeightAdjusted, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
            if (middleResult.getType() == HitResult.Type.MISS) {
                middleDestination = new Vec3(middleHeightAdjusted.x, ground.y + (height + entity.getRandom().nextInt(height)) * 3, middleHeightAdjusted.z);
            } else {
                middleDestination = middleResult.getLocation();
            }

            this.entity.addNextNavigationArray(middleDestination);
        }


        this.entity.addNextNavigationArray(finalDestination);
    }

    public Vec3 groundPosition(Vec3 airPosition) {
        BlockPos.MutableBlockPos ground = new BlockPos.MutableBlockPos();
        ground.set(airPosition.x, airPosition.y, airPosition.z);
        boolean flag = false;
        while (ground.getY() < entity.level().getMaxBuildHeight() && !entity.level().getBlockState(ground).isSolid() && entity.level().getFluidState(ground).isEmpty()){
            ground.move(0, 1, 0);
            flag = true;
        }
        ground.move(0, -1, 0);
        while (ground.getY() > entity.level().getMinBuildHeight() && !entity.level().getBlockState(ground).isSolid() && entity.level().getFluidState(ground).isEmpty()) {
            ground.move(0, -1, 0);
        }
        return Vec3.atCenterOf(flag ? ground.above() : ground.below());
    }
}
