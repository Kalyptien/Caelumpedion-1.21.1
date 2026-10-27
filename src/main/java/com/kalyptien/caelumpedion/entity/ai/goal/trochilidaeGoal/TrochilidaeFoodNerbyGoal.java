package com.kalyptien.caelumpedion.entity.ai.goal.trochilidaeGoal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class TrochilidaeFoodNerbyGoal extends Goal {

    private final Predicate<BlockState> VALID_POLLINATION_BLOCKS = (p_28074_) -> {
        if (p_28074_.hasProperty(BlockStateProperties.WATERLOGGED) && (Boolean)p_28074_.getValue(BlockStateProperties.WATERLOGGED)) {
            return false;
        } else if (p_28074_.is(BlockTags.FLOWERS)) {
            return p_28074_.is(Blocks.SUNFLOWER) ? p_28074_.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER : true;
        } else {
            return false;
        }
    };

    private FlyingBirdEntity bird;

    private BlockPos flowerPos;

    public TrochilidaeFoodNerbyGoal(FlyingBirdEntity bird) {
        super();
        this.bird = bird;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (bird.isOnAnimation()) {
            return false;
        }
        if(bird.isOnMigration()){
            return false;
        }
        else if (bird.getRandom().nextInt(reducedTickDelay(1000) + 500) != 0) {
            return false;
        } else {

            Optional<BlockPos> optional = this.findNearbyFlower();
            if (optional.isPresent()) {
                this.flowerPos  = (BlockPos) optional.get();
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean canContinueToUse() {
        if (this.flowerPos == null) {
            return false;
        } else {
            return true;
        }
    }

    public void stop() {
        this.flowerPos = null;
        this.bird.getNavigation().stop();
    }

    public void tick() {
        Vec3 vec3 = Vec3.atBottomCenterOf(this.flowerPos).add(0.0, 0.5, 0.0);

        if(flowerPos != null && vec3.distanceTo(this.bird.position()) <= 1.0f){
            this.bird.setNeedEatAnimation(true);
            this.flowerPos = null;
        }
        else{
            this.bird.getLookControl().setLookAt(vec3.x(), vec3.y(), vec3.z());
            this.bird.getNavigation().moveTo((double) this.flowerPos.getX(), (double) this.flowerPos.getY() + 0.5, (double) this.flowerPos.getZ(), bird.getAttributeBaseValue(Attributes.FLYING_SPEED)/2.0f);
        }
    }

    //Utils

    private Optional<BlockPos> findNearbyFlower() {
        return this.findNearestBlock(this.VALID_POLLINATION_BLOCKS, this.bird.getViewRange());
    }

    private Optional<BlockPos> findNearestBlock(Predicate<BlockState> predicate, double distance) {
        BlockPos blockpos = this.bird.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int i = 0; (double)i <= distance; i = i > 0 ? -i : 1 - i) {
            for(int j = 0; (double)j < distance; ++j) {
                for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        blockpos$mutableblockpos.setWithOffset(blockpos, k, i - 1, l);
                        if (blockpos.closerThan(blockpos$mutableblockpos, distance) && predicate.test(this.bird.level().getBlockState(blockpos$mutableblockpos))) {
                            return Optional.of(blockpos$mutableblockpos);
                        }
                    }
                }
            }
        }

        return Optional.empty();
    }
}
