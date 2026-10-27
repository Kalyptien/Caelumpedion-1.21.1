package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.ai.CircleAroundFlyingMob;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BirdRandomFlyingGoal extends WaterAvoidingRandomFlyingGoal {

    private FlyingBirdEntity bird;

    //Move
    private final List<Vec3> destinationNodeArray = new ArrayList<Vec3>();
    private double speedModifier;

    //Destination Calcul
    protected int range;
    protected int height;

    protected double numberOfMiddleDestination;

    protected double stepX;
    protected double stepZ;

    protected FlyType flyType;

    public BirdRandomFlyingGoal(FlyingBirdEntity bird, double speed) {
        super(bird, speed);
        this.bird = bird;
        this.setInterval(1000);
    }

    public boolean canUse() {
        if (this.bird.hasControllingPassenger()) {
            return false;
        } else if(this.bird instanceof CircleAroundFlyingMob mob && mob.isCyclingAround()){
            return false;
        } else if(this.bird.isFlying()){
            return false;
        } else {
            if (!this.forceTrigger
                    || !bird.isNeedToFlyAway()
                    || (bird.getIdAquaticBirdType() != FlyingBirdEntity.AquaticBirdType.FULL.getId() && !bird.isInWaterOrBubble())
            ) {
                if(this.bird.isOnAnimation()){
                    return false;
                }

                if (this.bird.getNoActionTime() >= 100) {
                    return false;
                }

                if (this.bird.getRandom().nextInt((bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.WALKER ? 1500 : 0) + reducedTickDelay(this.interval)) != 0) {
                    return false;
                }
            }

            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    public boolean canContinueToUse() {
        return !this.bird.hasControllingPassenger() && this.bird.isFlying() && !this.destinationNodeArray.isEmpty();
    }

    public void start() {
        this.bird.resetAnimations();

        this.bird.setFlying(true);
        this.bird.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.bird.getFlySpeed() + this.speedModifier);
    }

    @Override
    public void tick() {
        //Get next destination if needed
        if(bird.getNavigation().isDone()){
            if(!this.destinationNodeArray.isEmpty()) this.destinationNodeArray.removeFirst();

            if(!this.destinationNodeArray.isEmpty()){
                Vec3 vec3 = this.destinationNodeArray.getFirst();
                if(vec3 != null){
                    this.wantedX = vec3.x;
                    this.wantedY = vec3.y + 1;
                    this.wantedZ = vec3.z;
                }
            }
            else{
                if(bird.isTooHight(3)){
                    Vec3 vec3 = new Vec3(bird.getX(), bird.getY() - 2, bird.getZ());

                    this.destinationNodeArray.add(vec3);

                    this.wantedX = vec3.x;
                    this.wantedY = vec3.y;
                    this.wantedZ = vec3.z;
                }
            }
        }

        //Dynamic speed
        int sign = this.bird.getEyePosition().y < wantedY ? -1 : 1;
        double speedPercent = Math.min(1, Math.abs((this.bird.getEyePosition().y - this.wantedY) / 100.0f));
        this.speedModifier = (bird.getFlySpeed()/1.1f) * speedPercent * sign;

        //Go to
        this.bird.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.bird.getFlySpeed() + this.speedModifier);
    }

    public void stop() {
        this.speedModifier = 0;
        bird.setNeedToFlyAway(false);
        bird.setFlying(false);
        destinationNodeArray.clear();
    }

    //Destination Calcul

    @Nullable
    protected Vec3 getPosition() {
        this.destinationNodeArray.clear();

        this.defineFlyType();
        this.defineFlySize();
        this.createFlyPath();

        return this.destinationNodeArray.getFirst();
    }

    protected void defineFlyType(){
        if(this.bird.isOnMigration()){
            this.flyType = FlyType.MIGRATION;
        } else if (bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.WALKER) {
            this.flyType = FlyType.LONG;
        } else if (this.bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.LONG_FLYER) {
            if(this.randomPercent() <= 0.25){
                this.flyType = FlyType.SHORT;
            }
            else{
                this.flyType = FlyType.LONG;
            }
        } else if (this.bird.getFlyingBirdType() == FlyingBirdEntity.FlyingBirdType.SHORT_FlYER) {
            if(this.randomPercent() <= 0.25){
                this.flyType = FlyType.LONG;
            }
            else{
                this.flyType = FlyType.SHORT;
            }
        }
        else {
            this.flyType = FlyType.LONG;
        }
    }

    protected void defineFlySize(){
        if(this.flyType == FlyType.MIGRATION){
            range = bird.getFlyRange() * 5;
            height = bird.getFlyHeight() * 2;
        } else if (this.flyType == FlyType.LONG) {
            range = bird.getFlyRange() * 2;
            height = bird.getFlyHeight();
        } else if(this.flyType == FlyType.SHORT){
            range = bird.getFlyRange();
            height = bird.getFlyHeight() / 2;
        }
        else{
            range = bird.getFlyRange();
            height = bird.getFlyHeight();
        }
    }

    protected Vec3 getFinalDestination(){
        Vec3 finalPos = LandRandomPos.getPos(this.bird, range, height);

        if(finalPos == null){
            finalPos = new Vec3((range * this.randomPercent()) * randomSign(), (height * this.randomPercent()), (range * this.randomPercent()) * randomSign());

        }

        return bird.position().add(groundPosition(finalPos));
    }

    protected void createFlyPath() {

        //Get final destination

        Vec3 finalDestination;
        if (this.mob.getRandom().nextFloat() >= 0.75) {
            finalDestination = this.getTreePos();
        }
        else{
            finalDestination = getFinalDestination();
        }

        if(finalDestination == null){
            finalDestination = getFinalDestination();
        }

        double finalX = finalDestination.x;
        double finalZ = finalDestination.z;

        //Generate in between positions

        numberOfMiddleDestination = bird.getRandom().nextInt(1) + 1;

        stepX = finalX /numberOfMiddleDestination;
        stepZ = finalZ / numberOfMiddleDestination;

        for (double i = 1.0; i < (numberOfMiddleDestination + 1); i++) {
            Vec3 middleDestination = this.generateInBetweenDestination(i);
            this.destinationNodeArray.add(new Vec3(middleDestination.x, middleDestination.y, middleDestination.z));
        }

        this.destinationNodeArray.add(finalDestination);
    }

    //Middle Destination

    protected Vec3 generateInBetweenDestination(double iteration){

        Vec3 destination = bird.position().add(stepX * iteration, 0, stepZ * iteration);

        double ground = groundPosition(destination).y;

        double y = (Math.sin((Math.PI / numberOfMiddleDestination) * iteration) * height) + ground;

        return new Vec3(destination.x, y, destination.z);
    }

    protected Vec3 generateInBetweenChaosDestination(double iteration){

        Vec3 destination = bird.position().add(stepX * iteration, 0, stepZ * iteration);

        double ground = groundPosition(destination).y;

        double y = (Math.sin((Math.PI / numberOfMiddleDestination) * iteration) * height) + ground;

        return new Vec3(
                destination.x + this.randomModifier()
                , y  + this.randomModifier()
                , destination.z + this.randomModifier());
    }

    protected Vec3 generateInBetweenNearGroundDestination(double iteration){

        Vec3 destination = bird.position().add(stepX * iteration, 0, stepZ * iteration);

        double ground = groundPosition(destination).y;

        double y;
        if(iteration == 1){
            y = (height * 1.5) + ground;
        }
        else {
            y = ground + 5;
        }

        return new Vec3(destination.x,y,destination.z);
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

    @Nullable
    private Vec3 getTreePos() {
        BlockPos blockpos = this.mob.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
        Iterator var4 = BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 3.0), Mth.floor(this.mob.getY() - 6.0), Mth.floor(this.mob.getZ() - 3.0), Mth.floor(this.mob.getX() + 3.0), Mth.floor(this.mob.getY() + 6.0), Mth.floor(this.mob.getZ() + 3.0)).iterator();

        BlockPos blockpos1;
        boolean flag;
        do {
            do {
                if (!var4.hasNext()) {
                    return null;
                }

                blockpos1 = (BlockPos)var4.next();
            } while(blockpos.equals(blockpos1));

            BlockState blockstate = this.mob.level().getBlockState(blockpos$mutableblockpos1.setWithOffset(blockpos1, Direction.DOWN));
            flag = blockstate.getBlock() instanceof LeavesBlock || blockstate.is(BlockTags.LOGS);
        } while(!flag || !this.mob.level().isEmptyBlock(blockpos1) || !this.mob.level().isEmptyBlock(blockpos$mutableblockpos.setWithOffset(blockpos1, Direction.UP)));

        return Vec3.atBottomCenterOf(blockpos1);
    }

    protected double Nth ( int number, int index ) {
        if(number >= 10){
            return ((int)number / java.lang.Math.pow(10, index)) % 10;
        }
        return number;
    }

    protected int randomSign() {
        return bird.getRandom().nextBoolean() ? -1 : 1;
    }

    protected double randomNumber(){
        return bird.getRandom().nextInt(100);
    }

    protected double randomPercent(){
        return randomNumber() / 100;
    }

    protected double randomModifier(){
        return 5 * (randomNumber()/100) * randomSign();
    }

    protected static enum FlyType {
        SHORT,
        LONG,
        MIGRATION;

        private FlyType() {
        }
    }

}
