package com.kalyptien.caelumpedion.entity.client;

import com.kalyptien.caelumpedion.entity.client.gruiforme.GruiformeAnimation;
import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.entity.custom.passeriforme.PasseriformeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class BirdHierarchicalModel <T extends BirdEntity> extends HierarchicalModel<T> {

    protected ModelPart root;
    protected ModelPart body;
    protected ModelPart head;

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.applyHeadRotation(netHeadYaw, headPitch);

        //> WALK
        if(entity.onGround() && (entity instanceof FlyingBirdEntity flyingBird ? !flyingBird.isFlying() : true)){
            if(
                    this.getCurrentBirdSpeed(entity) >= (entity.getAttributeValue(Attributes.MOVEMENT_SPEED))
                    || (entity instanceof NeutralMob neutralMob ? neutralMob.isAngry() : false)
            ){
                this.setupRunAnimation(limbSwing, limbSwingAmount);
            }
            else{
                this.setupWalkAnimation(limbSwing, limbSwingAmount);
            }
        }

        //> IDLE
        this.setupIdleAnimation(entity, limbSwing, limbSwingAmount, ageInTicks);
        this.setupEatAnimation(entity, limbSwing, limbSwingAmount, ageInTicks);
    }

    protected void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -90f, 90f);
        headPitch = Mth.clamp(headPitch, -45f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 260f);
        this.head.xRot = headPitch *  ((float)Math.PI / 260f);
    }

    protected double getCurrentBirdSpeed(T entity){
        Vec3 delta = entity.getDeltaMovement();
        return Math.sqrt(delta.x * delta.x + delta.y * delta.y + delta.z * delta.z);
    }

    protected void setupWalkAnimation(float limbSwing, float limbSwingAmount){
    }

    protected void setupRunAnimation(float limbSwing, float limbSwingAmount){
    }

    protected void setupIdleAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
    }

    protected void setupEatAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
    }
}
