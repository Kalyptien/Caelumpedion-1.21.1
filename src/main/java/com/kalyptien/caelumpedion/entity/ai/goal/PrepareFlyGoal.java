package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.ai.FlyingMoveController;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class PrepareFlyGoal extends Goal {

    private FlyingBirdEntity bird;

    //Setup
    int range;
    int height;

    double numberOfMiddleDestination;

    double stepX;
    double stepZ;

    FlyType flyType;

    int seed ;
    double seedPercent;

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
                || bird.isFlying()
                || !bird.getNextNavigationArray().isEmpty()
        ) {
            return false;
        }

        if(bird.isNeedToFlyAway()){
            return true;
        }

        if(bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.WALKER && bird.getRandom().nextInt(3000) != 0) {
            return false;
        } else if (!bird.isFlying() && bird.getRandom().nextInt(1500) != 0) {
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        bird.clearNextNavigationArray();
        this.createFlyPath();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    private void createFlyPath() {

        int seed = bird.getRandom().nextInt(100);
        this.seedPercent = seed/100.0f;

        if(this.bird.isOnMigration()){
            this.flyType = FlyType.MIGRATION;
            range = bird.getFlyRange() * 5;
            height = bird.getFlyHeight() * 5;
        } else if (bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.WALKER) {
            this.flyType = FlyType.LONG;
            range = bird.getFlyRange() / 4;
            height = bird.getFlyHeight() / 4;
        } else if(this.bird.isNeedToFlyAway()){
            this.flyType = FlyType.PANIC;
            range = bird.getFlyRange() * 2;
            height = bird.getFlyHeight() * 2;
        }
        else{
            double longFlyChance = 0.0;

            if(bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.SHORT_FlYER){
                longFlyChance = 0.75;
            }
            else if (bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.LONG_FLYER){
                longFlyChance = 0.15;
            }

            if(seedPercent < longFlyChance){
                this.flyType = FlyType.SHORT;
                range = bird.getFlyRange() / 4;
                height = bird.getFlyHeight() / 4;
            }
            else {
                this.flyType = FlyType.LONG;
                range = bird.getFlyRange();
                height = bird.getFlyHeight();
            }
        }

        double finalX;
        double finalZ;

        if(flyType == FlyType.SHORT){
            finalX = ((range * seedPercent) * 2) - ((range + (Nth(seed, 1)))  * randomSign()) + ((range/10.0f) * randomSign());
            finalZ = ((range * seedPercent) * 2) - ((range + (Nth(seed, 2)))  * randomSign()) + ((range/10.0f) * randomSign());
        }
        else{
            finalX = ((range * seedPercent) * 2) - ((range + (Nth(seed, 1)))  * randomSign()) + ((range/2.0f) * randomSign());
            finalZ = ((range * seedPercent) * 2) - ((range + (Nth(seed, 2)))  * randomSign()) + ((range/2.0f) * randomSign());
        }

        //Generate the final position
        Vec3 finalDestination = bird.position().add(finalX,0,finalZ);
        Vec3 finalGround = groundPosition(finalDestination);

        finalDestination = new Vec3(finalDestination.x, finalGround.y, finalDestination.z);

        //Generate in between positions

        if(flyType == FlyType.MIGRATION){
            numberOfMiddleDestination = seed % 12;
        }
        else{
            numberOfMiddleDestination = seed % 6;
        }

        stepX = finalX /numberOfMiddleDestination;
        stepZ = finalZ / numberOfMiddleDestination;

        for (int i = 0; i < numberOfMiddleDestination; i++) {

            Vec3 middleDestination;
            if(bird.getFlyPathType() == FlyingBirdEntity.FlyPathType.CHAOS && (flyType == FlyType.SHORT || flyType == FlyType.PANIC) ){
                 middleDestination = bird.position().add(
                        ((stepX * i) - Nth(seed, 1)) * randomSign(),
                        0,
                        ((stepZ * i) - Nth(seed, 2)) * randomSign());
            }
            else{
                middleDestination = bird.position().add(stepX * i, 0, stepZ * i);
            }

            Vec3 middleGround = groundPosition(middleDestination);

            double y;
            if(flyType == FlyType.MIGRATION){
                y = height;
            }
            else if(flyType == FlyType.PANIC && bird.getFlyPathType() == FlyingBirdEntity.FlyPathType.CHAOS){
                y = Math.sin((bird.getRandom().nextInt(5) / numberOfMiddleDestination) * i) * height + middleGround.y;
            } else if (bird.getFlyPathType() == FlyingBirdEntity.FlyPathType.NEAR_GROUND && seedPercent < 0.5) {
                if(i >= numberOfMiddleDestination/4){
                    y = Math.sin((Math.PI / numberOfMiddleDestination) * i) * height + middleGround.y;
                }
                else{
                    y = (height/4) + middleGround.y;
                }
            } else {
                y = Math.sin((Math.PI / numberOfMiddleDestination) * i) * height + middleGround.y;
            }

            this.bird.addNextNavigationArray(new Vec3(middleDestination.x, y, middleDestination.z));
        }

        this.bird.addNextNavigationArray(finalDestination);
    }

    //Utils

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
