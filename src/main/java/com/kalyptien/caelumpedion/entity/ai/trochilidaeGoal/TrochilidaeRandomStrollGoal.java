package com.kalyptien.caelumpedion.entity.ai.trochilidaeGoal;

import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class TrochilidaeRandomStrollGoal extends RandomStrollGoal {
    public static final float PROBABILITY = 0.001F;
    protected final float probability;

    private FlyingBirdEntity bird;

    public TrochilidaeRandomStrollGoal(FlyingBirdEntity bird, double speedModifier) {
        this(bird, speedModifier, 0.001F);
        this.bird = bird;
    }

    public TrochilidaeRandomStrollGoal(FlyingBirdEntity bird, double speedModifier, float probability) {
        super(bird, speedModifier);
        this.probability = probability;
        this.bird = bird;
    }

    @Override
    public boolean canUse() {
        return !this.bird.isNeedEatAnimation() && super.canUse();
    }

    @Nullable
    protected Vec3 getPosition() {
        Vec3 vec3 = this.bird.getViewVector(0.0F);
        Vec3 vec32 = HoverRandomPos.getPos(this.bird, this.bird.getFlyRange(), this.bird.getFlyHeight(), vec3.x, vec3.z, 1.5707964F, 3, 1);
        return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(this.bird, this.bird.getFlyRange(), this.bird.getFlyHeight(), -2, vec3.x, vec3.z, 1.5707963705062866);
    }
}
