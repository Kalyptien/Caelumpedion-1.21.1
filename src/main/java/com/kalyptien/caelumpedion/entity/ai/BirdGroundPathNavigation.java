package com.kalyptien.caelumpedion.entity.ai;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;

public class BirdGroundPathNavigation extends GroundPathNavigation {

    BirdEntity bird;

    public BirdGroundPathNavigation(BirdEntity bird, Level level) {
        super(bird, level);
        this.bird = bird;
    }

    protected boolean hasValidPathType(PathType pathType) {
        if (pathType == PathType.WATER && this.bird.getIdAquaticBirdType() == BirdEntity.AquaticBirdType.FULL.getId()) {
            return true;
        } else {
            return super.hasValidPathType(pathType);
        }
    }

    public boolean isStableDestination(BlockPos pos) {
        return (this.level.getBlockState(pos).is(Blocks.WATER) && this.bird.getIdAquaticBirdType() == BirdEntity.AquaticBirdType.FULL.getId() )
                || super.isStableDestination(pos);
    }
}
