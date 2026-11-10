package com.kalyptien.caelumpedion.entity.ai.goal.piciformeGoal;

import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.PiciformeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class PiciformePeckNerbyGoal extends Goal {

    private final Predicate<BlockState> VALID_PECK_BLOCKS = (block) -> {
        if (block.hasProperty(BlockStateProperties.WATERLOGGED) && (Boolean)block.getValue(BlockStateProperties.WATERLOGGED)) {
            return false;
        }
        else if (this.bird.getIdVariant() == PiciformeEntity.PiciformeVariant.PicusPhantasiaTroglodyta.getId() && block.is(Blocks.POINTED_DRIPSTONE)) {
            return true;
        }
        else if (block.is(BlockTags.LOGS) || block.is(BlockTags.WOODEN_FENCES) || block.is(Blocks.IRON_BLOCK) || block.is(Blocks.IRON_BARS)) {
            return true;
        } else {
            return false;
        }
    };

    private PiciformeEntity bird;

    private BlockPos peckPos;

    public PiciformePeckNerbyGoal(PiciformeEntity bird) {
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
        else if (bird.getRandom().nextInt(reducedTickDelay(100) + 500) != 0) {
            return false;
        } else {

            Optional<BlockPos> optional = this.findNearbyPeckBlock();
            if (optional.isPresent()) {
                this.peckPos = (BlockPos) optional.get();
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean canContinueToUse() {
        if (this.peckPos == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void start() {
        super.start();
        this.bird.setFlying(true);
    }

    public void stop() {
        this.peckPos = null;
        this.bird.setFlying(false);
        this.bird.getNavigation().stop();
    }

    public void tick() {
        Vec3 vec3 = Vec3.atBottomCenterOf(this.peckPos);

        if(peckPos != null && vec3.distanceTo(this.bird.position()) <= 1.0f){
            this.bird.getLookControl().setLookAt(vec3.x(), vec3.y(), vec3.z());
            this.bird.setNeedPeckAnimation(true);
            this.bird.setBlockPosPecked(this.peckPos);
            this.peckPos = null;
        }
        else{
            this.bird.getLookControl().setLookAt(vec3.x(), vec3.y(), vec3.z());
            this.bird.getNavigation().moveTo((double) this.peckPos.getX(), (double) this.peckPos.getY() + 1, (double) this.peckPos.getZ(), bird.getAttributeBaseValue(Attributes.FLYING_SPEED)/2.0f);
        }
    }

    //Utils

    private Optional<BlockPos> findNearbyPeckBlock() {
        return this.findNearestBlock(this.VALID_PECK_BLOCKS, this.bird.getViewRange());
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
