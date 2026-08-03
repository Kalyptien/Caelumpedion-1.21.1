package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.block.ModBlocks;
import com.kalyptien.caelumpedion.block.entity.BirdFeederBlockEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BirdFoodNerbyGoal extends Goal {

    FlyingBirdEntity bird;
    Predicate<ItemEntity> foodPredicate;

    ItemEntity itemToFollow;
    BlockPos blockPosToFollow;
    int slotToPickup;


    public BirdFoodNerbyGoal(FlyingBirdEntity entity) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.bird = entity;
        foodPredicate = item -> (!item.hasPickUpDelay() && item.isAlive() && bird.isFood(item.getItem()));
    }

    @Override
    public boolean canUse() {
        if (!bird.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        } else {
            if (!bird.canMove()) {
                return false;
            } else if (bird.getRandom().nextInt(reducedTickDelay(1000)) != 0) {
                return false;
            } else {
                List<ItemEntity> list = bird.level().getEntitiesOfClass(ItemEntity.class, bird.getBoundingBox().inflate(bird.getViewRange(), bird.getViewRange(), bird.getViewRange()), this.foodPredicate);
                if(!list.isEmpty()){
                    itemToFollow = list.getFirst();
                    return true;
                }

                BlockPos blockPos = findNearbyFeederWithSpace();

                if(blockPos != null){
                    boolean findAFeederWithFood = false;
                    BlockEntity blockEntity = bird.level().getBlockEntity(blockPos);

                    if(blockEntity instanceof BirdFeederBlockEntity birdFeeder){
                        for (int j = 0; j < birdFeeder.inventory.getSlots(); j++) {
                            if(bird.isFood(birdFeeder.inventory.getStackInSlot(j))){
                                findAFeederWithFood = true;
                                this.blockPosToFollow = blockPos;
                                this.slotToPickup = j;
                                break;
                            }
                        }
                    }

                    if(findAFeederWithFood){
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                return false;
            }
        }
    }

    public void tick() {

        ItemStack itemstack = bird.getItemBySlot(EquipmentSlot.MAINHAND);

        if (itemstack.isEmpty()) {

            if(itemToFollow != null && bird.distanceTo(itemToFollow) <= 1.0f){
                bird.pickUpItem(itemToFollow);
            }
            else if(blockPosToFollow != null && bird.distanceToSqr(blockPosToFollow.getX(), blockPosToFollow.getY(), blockPosToFollow.getZ()) <= 1.0f) {
                bird.pickUpItemFromFeeder((BirdFeederBlockEntity) bird.level().getBlockEntity(blockPosToFollow), slotToPickup);
            }
            else {
                if(itemToFollow != null){
                    bird.getNavigation().moveTo((Entity)itemToFollow, bird.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + 1f);
                }
                else {
                    bird.getNavigation().moveTo(blockPosToFollow.getX(), blockPosToFollow.getY(), blockPosToFollow.getZ(), bird.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + 1f);
                }
            }
        }
    }

    public void start() {
        if(itemToFollow != null){
            bird.getNavigation().moveTo((Entity)itemToFollow, bird.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + 1f);
        }
        else {
            bird.getNavigation().moveTo(blockPosToFollow.getX(), blockPosToFollow.getY(), blockPosToFollow.getZ(), bird.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + 1f);
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.itemToFollow = null;
        this.blockPosToFollow = null;
    }

    protected BlockPos findNearbyFeederWithSpace() {
        int i = this.bird.getViewRange();
        int j = this.bird.getViewRange();
        BlockPos blockpos = this.bird.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
            for (int l = 0; l < i; ++l) {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        blockpos$mutableblockpos.setWithOffset(blockpos, i1, k - 1, j1);
                        if (this.bird.isWithinRestriction(blockpos$mutableblockpos) && this.isValidTarget(this.bird.level(), blockpos$mutableblockpos)) {
                            return blockpos$mutableblockpos;
                        }
                    }
                }
            }
        }

        return null;
    }

    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.is(ModBlocks.BIRD_FEEDER);
    }
}
