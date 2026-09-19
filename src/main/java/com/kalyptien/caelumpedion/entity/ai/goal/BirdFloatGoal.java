package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class BirdFloatGoal extends FloatGoal {

    private BirdEntity bird;

    public BirdFloatGoal(BirdEntity bird) {
        super(bird);

        this.bird = bird;
    }

    public boolean canUse() {
        if(
                this.bird.getIdAquaticBirdType() == BirdEntity.AquaticBirdType.FULL.getId() ||
                this.bird.getIdAquaticBirdType() == BirdEntity.AquaticBirdType.TALL.getId()
        ){
            return this.bird.isUnderWater() ? super.canUse() : false;
        }
        else {
            return super.canUse();
        }
    }

    public void tick() {
        if (this.bird.getRandom().nextFloat() < 0.8F) {
            this.bird.getJumpControl().jump();
        }

        Vec3 vec3 = LandRandomPos.getPos(this.bird, 16, 16);

        if(vec3 != null){
            this.bird.getNavigation().moveTo(vec3.x, vec3.y,  vec3.z, this.bird.getAttributeBaseValue(Attributes.FLYING_SPEED) * 2);
        }
    }
}
