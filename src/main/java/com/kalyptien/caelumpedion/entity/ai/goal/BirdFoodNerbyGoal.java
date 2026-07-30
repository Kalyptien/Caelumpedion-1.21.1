package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class BirdFoodNerbyGoal extends Goal {

    FlyingBirdEntity bird;
    Predicate<ItemEntity> foodPredicate;

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
            } else if (bird.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                return false;
            } else {
                List<ItemEntity> list = bird.level().getEntitiesOfClass(ItemEntity.class, bird.getBoundingBox().inflate(bird.getViewRange(), bird.getViewRange(), bird.getViewRange()), this.foodPredicate);
                return !list.isEmpty() && bird.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
            }
        }
    }

    public void tick() {

        List<ItemEntity> list = bird.level().getEntitiesOfClass(ItemEntity.class, bird.getBoundingBox().inflate(bird.getViewRange(), bird.getViewRange(), bird.getViewRange()), this.foodPredicate);
        ItemStack itemstack = bird.getItemBySlot(EquipmentSlot.MAINHAND);

        if (itemstack.isEmpty() && !list.isEmpty()) {
            if(bird.distanceTo(list.getFirst()) <= 1.0f){
                bird.pickUpItem(list.getFirst());
            }
            else {
                bird.getNavigation().moveTo((Entity)list.getFirst(), bird.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + 1f);
            }
        }
    }

    public void start() {
        List<ItemEntity> list = bird.level().getEntitiesOfClass(ItemEntity.class, bird.getBoundingBox().inflate(bird.getViewRange(), bird.getViewRange(), bird.getViewRange()), this.foodPredicate);
        if (!list.isEmpty()) {
            bird.getNavigation().moveTo((Entity)list.getFirst(), bird.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
        }
    }
}
