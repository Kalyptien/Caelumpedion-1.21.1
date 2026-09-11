package com.kalyptien.caelumpedion.entity.ai.goal;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class BirdRandomStrollGoal extends RandomStrollGoal {
    public static final float PROBABILITY = 0.001F;
    protected final float probability;

    private FlyingBirdEntity bird;

    public BirdRandomStrollGoal(FlyingBirdEntity bird, double speedModifier) {
        this(bird, speedModifier, 0.001F);
        this.bird = bird;
    }

    public BirdRandomStrollGoal(FlyingBirdEntity bird, double speedModifier, float probability) {
        super(bird, speedModifier);
        this.probability = probability;
        this.bird = bird;
    }

    @Nullable
    protected Vec3 getPosition() {
        if(this.bird.getAquaticBirdType() == FlyingBirdEntity.AquaticBirdType.FULL){
            return super.getPosition();
        }
        else{
            if (this.bird.isInWaterOrBubble()) {
                Vec3 vec3 = LandRandomPos.getPos(this.bird, 15, 7);
                return vec3 == null ? super.getPosition() : vec3;
            } else {
                return this.bird.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.bird, 10, 7) : super.getPosition();
            }
        }
    }
}
