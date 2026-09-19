package com.kalyptien.caelumpedion.entity.client;

import com.kalyptien.caelumpedion.entity.client.gruiforme.GruiformeAnimation;
import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class FlyingBirdHierarchicalModel<T extends FlyingBirdEntity> extends BirdHierarchicalModel<T> {

    protected ModelPart wingR;
    protected ModelPart normalWingR;
    protected ModelPart flyingWingR;
    protected ModelPart wingL;
    protected ModelPart normalWingL;
    protected ModelPart flyingWingL;
    protected ModelPart tail;
    protected ModelPart flyingTail;
    protected ModelPart normalTail;

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);

        //GLOBAL ANIMATION

        this.showFlyingPart(entity.isFlying());

        if(entity.isFlying()){
            //> FLY
            this.setupFlyAnimation(limbSwing, limbSwingAmount);

            float partialTick = ageInTicks - entity.tickCount;
            float flyProgress = entity.getFlyProgress(partialTick);
            float rollAmount = entity.getFlightRoll(partialTick) / 57.295776F * flyProgress;
            float pitchAmount = entity.getFlightPitch(partialTick) / 57.295776F * flyProgress;

            root.xRot += pitchAmount;
            root.zRot += rollAmount;
        } else if (!entity.isFlying() && !entity.onGround()) {
            this.setupSlowFallAnimation(limbSwing, limbSwingAmount);
        }

    }

    protected void showFlyingPart(boolean show){
        this.normalTail.visible = !show;
        this.normalWingL.visible = !show;
        this.normalWingR.visible = !show;

        this.flyingTail.visible = show;
        this.flyingWingL.visible = show;
        this.flyingWingR.visible = show;
    }

    protected void setupFlyAnimation(float limbSwing, float limbSwingAmount){
    }

    protected void setupSlowFallAnimation(float limbSwing, float limbSwingAmount){
    }
}
