package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class BirdPlayerNerbyGoal extends Goal {

    FlyingBirdEntity bird;
    List<? extends Player> list;

    boolean playerIsRunning = false;
    boolean playerIsSneaking = false;
    boolean playerIsWalking = false;
    double distanceWithNearestPlayer = 9999999999.0;
    Player neartesPlayer = null;

    public BirdPlayerNerbyGoal(FlyingBirdEntity entity) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.bird = entity;
    }

    @Override
    public boolean canUse() {

        if(!bird.isFlying() && bird.getRandom().nextInt(10) != 0){
            Predicate predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR;
            list = this.bird.level().getEntitiesOfClass(Player.class,
                    this.bird.getBoundingBox().inflate(bird.getViewRange(), bird.getViewRange(), bird.getViewRange()), predicate);

            return !list.isEmpty();
        }

        return false;
    }

    public void start() {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).isCrouching()){
                this.playerIsSneaking = true;
            } else if (list.get(i).isSprinting()) {
                this.playerIsRunning = true;
            }
            else{
                this.playerIsWalking = true;
            }

            double distanceToBird = list.get(i).distanceTo(bird);

            if(distanceToBird < distanceWithNearestPlayer){
                distanceWithNearestPlayer = distanceToBird;
                neartesPlayer = list.get(i);
            }
        }

        if(bird.getCurrentStress() >= 25 && bird.getCurrentStress() < 50){
            if(
                    distanceWithNearestPlayer <= 1f
                            || (this.playerIsRunning && distanceWithNearestPlayer <= 5.0f)
            ){
                bird.addCurrentStress(1);
                bird.setNeedToFlyAway(true);
            }
            else if (distanceWithNearestPlayer <= 5f){
                Vec3 destination = DefaultRandomPos.getPosAway(bird, 16, 7, neartesPlayer.getEyePosition());
                bird.getNavigation().moveTo(destination.x, destination.y, destination.z, bird.getFlySpeed());
            }
        }
        else if(bird.getCurrentStress() >= 50 && bird.getCurrentStress() < 75){
            if(
                    distanceWithNearestPlayer <= 3f
                    || (this.playerIsRunning && distanceWithNearestPlayer <= 10f)
            ){
                bird.addCurrentStress(1);
                bird.setNeedToFlyAway(true);
            }
            else if (distanceWithNearestPlayer <= 5f){
                Vec3 destination = DefaultRandomPos.getPosAway(bird, 16, 7, neartesPlayer.getEyePosition());
                bird.getNavigation().moveTo(destination.x, destination.y, destination.z, bird.getFlySpeed());
            }
        }
        else if(bird.getCurrentStress() >= 75 && bird.getCurrentStress() < 100){
            if(
                    (this.playerIsSneaking &&  distanceWithNearestPlayer <= 3.0f)
                    || (this.playerIsWalking && distanceWithNearestPlayer <= 10f)
                    || (this.playerIsRunning && distanceWithNearestPlayer <= 15f)
            ){
                bird.addCurrentStress(1);
                bird.setNeedToFlyAway(true);
            }
        }
        else if(bird.getCurrentStress() >= 100){
            bird.addCurrentStress(2);
            bird.setPanicMode(true);
            bird.setNeedToFlyAway(true);
        }
    }

    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void stop() {
        this.playerIsSneaking = false;
        this.playerIsRunning = false;
        this.playerIsWalking = false;
        this.distanceWithNearestPlayer = 9999999999.0f;
    }
}
