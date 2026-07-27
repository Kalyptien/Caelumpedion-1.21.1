package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.ai.FlyingMoveController;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PrepareFlyGoal extends Goal {

    private FlyingBirdEntity bird;

    private double longFlyChance = 0.0;

    public PrepareFlyGoal(FlyingBirdEntity entity) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.bird = entity;
    }

    @Override
    public boolean canUse() {

        if(bird.getAquaticBirdType() == FlyingBirdEntity.AquaticBirdType.NONE && bird.isInWaterOrBubble()){
            return true;
        }

        if(bird instanceof SocialFlyingBirdEntity){
            if(((SocialFlyingBirdEntity) bird).isFollower()){
                return false;
            }
        }

        if (
                bird.isVehicle()
                || bird.isPassenger()
                || bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.WALKER
                || bird.isFlying()
                || !bird.getNextNavigationArray().isEmpty()
                || (!bird.isFlying() && bird.getRandom().nextInt(100) != 0)
        ) {
            return false;
        }

        return true;
    }

    @Override
    public void start() {

        if(bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.SHORT_FlYER){
            longFlyChance = 0.75;
        }
        else if (bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.LONG_FLYER){
            longFlyChance = 0.15;
        }
        else if (bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.WALKER){
            longFlyChance = 1.0;
        }

        bird.clearNextNavigationArray();
        this.createFlyPath();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    private void createFlyPath() {

        //Setup
        int range;
        int height;

        int numberOfMiddleDestination;

        double stepX;
        double stepZ;

        FlyType flyType;

        int seed = bird.getRandom().nextInt(100);
        double seedPercent = seed /100.0;

        if(seedPercent < longFlyChance){
            flyType = FlyType.SHORT;
            range = bird.getFlyRange() / 4;
            height = bird.getFlyHeight() / 4;
        }
        else {
            flyType = FlyType.LONG;
            range = bird.getFlyRange();
            height = bird.getFlyHeight();
        }

        double finalX = ((range * seedPercent) * 2) - (range + (Nth(seed, 1)))  * randomSign();
        double finalZ = ((range * seedPercent) * 2) - (range + (Nth(seed, 2)))  * randomSign();

        //Generate the final position
        Vec3 finalDestination = bird.position().add(finalX,0,finalZ);

        Vec3 finalGround = groundPosition(finalDestination);

        // Check if water is below the final position
        boolean tooMuchWaterFinalDestination = false;
        if(bird.getAquaticBirdType() != FlyingBirdEntity.AquaticBirdType.FULL){
            BlockPos.MutableBlockPos aquaticGround = new BlockPos.MutableBlockPos();

            aquaticGround.set(finalDestination.x, finalGround.y - 1, finalDestination.z);
            if(!bird.level().getBlockState(aquaticGround).isSolid() && bird.getAquaticBirdType() == FlyingBirdEntity.AquaticBirdType.NONE){
                tooMuchWaterFinalDestination = true;
            }

            aquaticGround.set(finalDestination.x, finalGround.y - 2, finalDestination.z);
            if(!bird.level().getBlockState(aquaticGround).isSolid() && bird.getAquaticBirdType() == FlyingBirdEntity.AquaticBirdType.TALL){
                tooMuchWaterFinalDestination = true;
            }
        }

        if(tooMuchWaterFinalDestination){
            finalDestination = new Vec3(finalDestination.x - finalX, finalGround.y, finalDestination.z - finalZ);
        }
        else{
            finalDestination = new Vec3(finalDestination.x, finalGround.y, finalDestination.z);
        }

        //Generate in between positions

        numberOfMiddleDestination = seed % 6;
        stepX = finalX /numberOfMiddleDestination;
        stepZ = finalZ / numberOfMiddleDestination;

        for (int i = 0; i < numberOfMiddleDestination; i++) {

            Vec3 middleDestination = bird.position().add(
                    ((stepX * i) - Nth(seed, 1)) * randomSign(),
                    0,
                    ((stepZ * i) - Nth(seed, 2)) * randomSign());

            Vec3 middleGround = groundPosition(middleDestination);

            double y = Math.sin((Math.PI / numberOfMiddleDestination) * i) * height + middleGround.y;

            this.bird.addNextNavigationArray(new Vec3(middleDestination.x, y, middleDestination.z));
        }

        this.bird.addNextNavigationArray(finalDestination);
    }

    public Vec3 groundPosition(Vec3 airPosition) {
        BlockPos.MutableBlockPos ground = new BlockPos.MutableBlockPos();
        ground.set(airPosition.x, airPosition.y, airPosition.z);
        boolean flag = false;
        while (ground.getY() < bird.level().getMaxBuildHeight() && !bird.level().getBlockState(ground).isSolid() && bird.level().getFluidState(ground).isEmpty()){
            ground.move(0, 1, 0);
            flag = true;
        }
        ground.move(0, -1, 0);
        while (ground.getY() > bird.level().getMinBuildHeight() && !bird.level().getBlockState(ground).isSolid() && bird.level().getFluidState(ground).isEmpty()) {
            ground.move(0, -1, 0);
        }
        return Vec3.atCenterOf(flag ? ground.above() : ground.below());
    }

    private double Nth ( int number, int index ) {
        if(number >= 10){
            return ((int)number / java.lang.Math.pow(10, index)) % 10;
        }
        return number;
    }

    public int randomSign() {
        return bird.getRandom().nextBoolean() ? -1 : 1;
    }

    protected static enum FlyType {
        SHORT,
        LONG,
        MIGRATION,
        PANIC;

        private FlyType() {
        }
    }
}
