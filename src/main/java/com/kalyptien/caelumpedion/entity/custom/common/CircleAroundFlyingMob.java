package com.kalyptien.caelumpedion.entity.custom.common;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public interface CircleAroundFlyingMob {

    public AttackPhase getAttackPhase();

    public void setAttackPhase(AttackPhase attackPhase);

    public BlockPos getAnchorPoint();

    abstract void setAnchorPoint(BlockPos anchorPoint);

    public Vec3 getMoveTargetPoint();

    public void setMoveTargetPoint(Vec3 moveTargetPoint);

    public boolean isCyclingAround();

    public void setCyclingAround(boolean cyclingAround);

    public class CircleAroundFlyingGoal extends Goal{
        protected int interval;

        private int nextSweepTick;

        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        private FlyingBirdEntity bird;
        private CircleAroundFlyingMob mob;

        private double speedModifier;

        public CircleAroundFlyingGoal(FlyingBirdEntity bird) {
            this.bird = bird;
            if(bird instanceof CircleAroundFlyingMob mob){
                this.mob = mob;
            }
            this.setInterval(2000);
        }

        public boolean canUse() {
            if (this.bird.hasControllingPassenger()) {
                return false;
            } else if(this.bird.isFlying() && !this.mob.isCyclingAround()){
                return false;
            } else if (this.bird.getTarget() != null) {
                return true;
            } else {
                if (this.bird.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                    return false;
                }
                return true;
            }
        }

        public void start() {
            this.bird.resetAnimations();

            this.bird.setFlying(true);
            this.mob.setCyclingAround(true);

            this.distance = 5.0F + bird.getRandom().nextFloat() * 10.0F;
            this.height = (-bird.getRandom().nextFloat() * 9.0F) + (this.bird.getFlyHeight());
            this.clockwise = bird.getRandom().nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        @Override
        public void stop() {
            this.speedModifier = 0;
            this.setInterval(2000);
            this.bird.setFlying(false);
            this.mob.setCyclingAround(false);
        }

        public boolean canContinueToUse() {
            if(this.bird.getTarget() == null){
                return this.bird.getRandom().nextInt(1000) != 0;
            }
            else{
                if(!this.bird.getTarget().isAlive()){
                    return false;
                }

                return true;
            }
        }

        @Override
        public void tick() {
            if(bird.getTarget() != null && mob.getAttackPhase() == AttackPhase.SWOOP){
                this.sweepAttack();
            } else if (bird.getTarget() == null || mob.getAttackPhase() == AttackPhase.CIRCLE) {
                circleAround();
            }

            //Dynamic speed
            int sign = this.bird.getEyePosition().y < mob.getMoveTargetPoint().y ? -1 : 1;
            double speedPercent = Math.min(1, Math.abs((this.bird.getEyePosition().y - this.mob.getMoveTargetPoint().y) / 100.0f));
            this.speedModifier = (bird.getFlySpeed()/1.1f) * speedPercent * sign;

            this.bird.getNavigation().moveTo(mob.getMoveTargetPoint().x, mob.getMoveTargetPoint().y, mob.getMoveTargetPoint().z, this.bird.getFlySpeed() + this.speedModifier);
        }

        protected void sweepAttack(){

            LivingEntity livingentity = bird.getTarget();

            if (livingentity == null) {
                mob.setAttackPhase(AttackPhase.CIRCLE);
            } else if (!livingentity.isAlive()) {
                this.bird.setTarget(null);
                mob.setAttackPhase(AttackPhase.CIRCLE);
            } else {
                if (this.bird.getRandom().nextInt(reducedTickDelay(1000)) == 0) {
                    this.bird.setTarget(null);
                    mob.setAttackPhase(AttackPhase.CIRCLE);
                }

                if (livingentity instanceof Player) {
                    Player player = (Player)livingentity;
                    if (livingentity.isSpectator() || player.isCreative()) {
                        this.bird.setTarget(null);
                        mob.setAttackPhase(AttackPhase.CIRCLE);
                    }
                }
            }

            if (livingentity != null) {
                mob.setMoveTargetPoint(new Vec3(livingentity.getX(), livingentity.getY(0.5), livingentity.getZ()));
                if (bird.getBoundingBox().inflate(0.20000000298023224).intersects(livingentity.getBoundingBox())) {
                    bird.doHurtTarget(livingentity);
                    mob.setAttackPhase(AttackPhase.CIRCLE);
                } else if (bird.horizontalCollision || bird.hurtTime > 0) {
                    mob.setAttackPhase(AttackPhase.CIRCLE);
                }
            }
        }

        protected void circleAround(){
            if (bird.getRandom().nextInt(this.adjustedTickDelay(350)) == 0) {
                this.height = (-bird.getRandom().nextFloat() * 9.0F) + (this.bird.getFlyHeight());
            }

            if (bird.getRandom().nextInt(this.adjustedTickDelay(250)) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (bird.getRandom().nextInt(this.adjustedTickDelay(450)) == 0) {
                this.angle = bird.getRandom().nextFloat() * 2.0F * 3.1415927F;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (mob.getMoveTargetPoint().y < bird.getY() && !bird.level().isEmptyBlock(bird.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (mob.getMoveTargetPoint().y > bird.getY() && !bird.level().isEmptyBlock(bird.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }
        }

        protected boolean touchingTarget() {
            return mob.getMoveTargetPoint().distanceToSqr(bird.getX(), bird.getY(), bird.getZ()) < 4.0;
        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(mob.getAnchorPoint())) {
                mob.setAnchorPoint(bird.blockPosition());
            }

            this.angle += this.clockwise * 15.0F * 0.017453292F;
            mob.setMoveTargetPoint(Vec3.atLowerCornerOf(mob.getAnchorPoint()).add((double)(this.distance * Mth.cos(this.angle)), (double)(-4.0F + this.height), (double)(this.distance * Mth.sin(this.angle))));
        }

        public void setInterval(int newchance) {
            this.interval = newchance;
        }
    }

    public class AttackStrategyGoal extends Goal {
        private int nextSweepTick;
        private FlyingBirdEntity bird;
        private CircleAroundFlyingMob mob;

        public AttackStrategyGoal(FlyingBirdEntity bird) {
            this.bird = bird;
            if(bird instanceof CircleAroundFlyingMob mob){
                this.mob = mob;
            }
        }

        public boolean canUse() {
            LivingEntity livingentity = bird.getTarget();
            return (livingentity != null ? bird.canAttack(livingentity, TargetingConditions.DEFAULT) : false) && this.mob.isCyclingAround();
        }

        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(50);
            mob.setAttackPhase(CircleAroundFlyingMob.AttackPhase.CIRCLE);
            this.setAnchorAboveTarget();
        }

        public void stop() {
            mob.setAnchorPoint(bird.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, mob.getAnchorPoint()).above(10 + bird.getRandom().nextInt(20)));
        }

        public void tick() {
            if (mob.getAttackPhase() == CircleAroundFlyingMob.AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    mob.setAttackPhase(CircleAroundFlyingMob.AttackPhase.SWOOP);
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay((8 + bird.getRandom().nextInt(4)) * 20);
                }
            }

        }

        private void setAnchorAboveTarget() {
            mob.setAnchorPoint(bird.getTarget().blockPosition().above(20 + bird.getRandom().nextInt(20)));
            if (mob.getAnchorPoint().getY() < bird.level().getSeaLevel()) {
                mob.setAnchorPoint(new BlockPos(mob.getAnchorPoint().getX(), bird.level().getSeaLevel() + 1, mob.getAnchorPoint().getZ()));
            }

        }
    }

    public class ScavangerNearestAttackableTargetGoal extends NearestAttackableTargetGoal{

        private int unseenTicks;

        public ScavangerNearestAttackableTargetGoal(Mob mob, Class targetType, int randomInterval, boolean mustSee, boolean mustReach, @Nullable Predicate targetPredicate) {
            super(mob, targetType, randomInterval, mustSee, mustReach, targetPredicate);
        }

        @Override
        public void start() {
            super.start();
            this.unseenTicks = 0;
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
                livingentity = this.targetMob;
            }

            if (livingentity == null) {
                return false;
            } else if (!this.mob.canAttack(livingentity)) {
                return false;
            } else {
                Team team = this.mob.getTeam();
                Team team1 = livingentity.getTeam();
                if (team != null && team1 == team) {
                    return false;
                } else {
                    double d0 = this.getFollowDistance();
                    if (this.mob.distanceToSqr(livingentity) > d0 * d0 * 32) {
                        return false;
                    } else {
                        if (this.mustSee) {
                            if (this.mob.getSensing().hasLineOfSight(livingentity)) {
                                this.unseenTicks = 0;
                            } else if (++this.unseenTicks > reducedTickDelay(this.unseenMemoryTicks)) {
                                return false;
                            }
                        }

                        this.mob.setTarget(livingentity);
                        return true;
                    }
                }
            }
        }
    }

    static enum AttackPhase {
        CIRCLE,
        SWOOP;

        private AttackPhase() {
        }
    }
}
