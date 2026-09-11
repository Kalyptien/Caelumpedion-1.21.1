package com.kalyptien.caelumpedion.entity.client.hirundininae;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.passeriforme.PasseriformeAnimation;
import com.kalyptien.caelumpedion.entity.custom.passeriforme.HirundininaeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class HirundininaeModel<T extends HirundininaeEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "passeriforme"), "main");

    private final ModelPart passeriforme;
    private final ModelPart body;
    private final ModelPart head;

    private final ModelPart tail;
    private final ModelPart normalTail;
    private final ModelPart flyingTail;

    public HirundininaeModel(ModelPart root) {
        this.passeriforme = root.getChild("passeriforme");
        this.body = this.passeriforme.getChild("body");
        this.head = this.passeriforme.getChild("head");

        this.tail = this.body.getChild("tail");
        this.normalTail = this.tail.getChild("normalTail");
        this.flyingTail = this.tail.getChild("flyingTail");

        this.normalTail.visible = true;
        this.flyingTail.visible = false;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition passeriforme = partdefinition.addOrReplaceChild("passeriforme", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = passeriforme.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -1.0F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -1.4F, 0.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingL = wingL.addOrReplaceChild("normalWingL", CubeListBuilder.create().texOffs(8, 7).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, -1).addBox(1.0F, 0.0F, 2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flyingWingL = wingL.addOrReplaceChild("flyingWingL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -1.4F, 0.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingR = wingR.addOrReplaceChild("normalWingR", CubeListBuilder.create().texOffs(8, 7).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(10, -1).addBox(-1.0F, 0.0F, 2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flyingWingR = wingR.addOrReplaceChild("flyingWingR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0059F, -0.5913F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create(), PartPose.offset(1.0F, 3.0F, 2.0F));

        PartDefinition backLegL = legL.addOrReplaceChild("backLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = backLegL.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 0).addBox(-0.5F, -0.004F, 0.841F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.404F, -0.741F, 0.7854F, 0.0F, 0.0F));

        PartDefinition frontLegL = legL.addOrReplaceChild("frontLegL", CubeListBuilder.create().texOffs(13, 3).addBox(-0.5F, 0.0F, -0.9F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.9F, -0.4F));

        PartDefinition middleLegL = legL.addOrReplaceChild("middleLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.55F));

        PartDefinition cube_r2 = middleLegL.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 5).addBox(-0.5F, 0.9F, -0.35F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -1.1781F, 0.0F, 0.0F));

        PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create(), PartPose.offset(-1.0F, 3.0F, 2.0F));

        PartDefinition backLegR = legR.addOrReplaceChild("backLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r3 = backLegR.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 1).addBox(-0.5F, -0.004F, 0.841F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.404F, -0.741F, 0.7854F, 0.0F, 0.0F));

        PartDefinition frontLegR = legR.addOrReplaceChild("frontLegR", CubeListBuilder.create().texOffs(13, 2).addBox(-0.5F, 0.0F, -0.9F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.9F, -0.4F));

        PartDefinition middleLegR = legR.addOrReplaceChild("middleLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.5F));

        PartDefinition cube_r4 = middleLegR.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 4).addBox(-0.5F, 0.9F, -0.35F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.05F, -1.1781F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.3F, 3.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition normalTail = tail.addOrReplaceChild("normalTail", CubeListBuilder.create().texOffs(-4, 12).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flyingTail = tail.addOrReplaceChild("flyingTail", CubeListBuilder.create().texOffs(-5, 16).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = passeriforme.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -1.0F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -2.5F, -1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 12).addBox(0.0F, -4.5F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition beck = head.addOrReplaceChild("beck", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -0.5F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.4F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.applyHeadRotation(netHeadYaw, headPitch);

        //GLOBAL ANIMATION

        this.showFlyingPart(entity.isFlying());

        //> WALK
        if(entity.onGround() && !entity.isFlying()){
            double currentSpeed = this.getCurrentBirdSpeed(entity);

            if(currentSpeed >= (entity.getAttributeValue(Attributes.MOVEMENT_SPEED) - (entity.getAttributeValue(Attributes.MOVEMENT_SPEED)/4))){
                this.animateWalk(PasseriformeAnimation.PASSERIFORME_RUN, limbSwing, limbSwingAmount, 2f, 2f);
            }
            else{
                this.animateWalk(PasseriformeAnimation.PASSERIFORME_WALK, limbSwing, limbSwingAmount, 2f, 2f);
            }
        }

        if(entity.isFlying()){
            //> FLY
            this.animateWalk(PasseriformeAnimation.PASSERIFORME_FLY, limbSwing, limbSwingAmount, 5f, 5f);

            float partialTick = ageInTicks - entity.tickCount;
            float flyProgress = entity.getFlyProgress(partialTick);
            float rollAmount = entity.getFlightRoll(partialTick) / 57.295776F * flyProgress;
            float pitchAmount = entity.getFlightPitch(partialTick) / 57.295776F * flyProgress;

            passeriforme.xRot += pitchAmount;
            passeriforme.zRot += rollAmount;
        }

        //> IDLE
        this.animate(entity.eatAnimationState, PasseriformeAnimation.PASSERIFORME_EAT, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, PasseriformeAnimation.PASSERIFORME_IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -90f, 90f);
        headPitch = Mth.clamp(headPitch, -45f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 260f);
        this.head.xRot = headPitch *  ((float)Math.PI / 260f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        passeriforme.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return passeriforme;
    }

    private void showFlyingPart(boolean show){
        this.normalTail.visible = !show;

        this.flyingTail.visible = show;
    }

    private double getCurrentBirdSpeed(T entity){
        Vec3 delta = entity.getDeltaMovement();
        return Math.sqrt(delta.x * delta.x + delta.y * delta.y + delta.z * delta.z);
    }
}
