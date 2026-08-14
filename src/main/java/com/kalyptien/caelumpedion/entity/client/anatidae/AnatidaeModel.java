package com.kalyptien.caelumpedion.entity.client.anatidae;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.AnatidaeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class AnatidaeModel<T extends AnatidaeEntity> extends HierarchicalModel<T> implements ArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "anatidae"), "main");

    private final ModelPart anatidae;
    private final ModelPart body;
    private final ModelPart head;
    public AnatidaeModel(ModelPart root) {
        this.anatidae = root.getChild("anatidae");
        this.body = this.anatidae.getChild("body");
        this.head = this.anatidae.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition anatidae = partdefinition.addOrReplaceChild("anatidae", CubeListBuilder.create(), PartPose.offset(0.0F, 20.7583F, -1.5417F));

        PartDefinition body = anatidae.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.7417F, 1.375F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create(), PartPose.offset(-2.5F, -3.2167F, -2.8333F));

        PartDefinition FrontWingR = wingR.addOrReplaceChild("FrontWingR", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition BackWingR = wingR.addOrReplaceChild("BackWingR", CubeListBuilder.create().texOffs(13, 2).addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.5F, 5.0F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create(), PartPose.offset(2.5F, -3.2167F, -2.8333F));

        PartDefinition FrontWingL = wingL.addOrReplaceChild("FrontWingL", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -2.0F, 0.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition BackWingL = wingL.addOrReplaceChild("BackWingL", CubeListBuilder.create().texOffs(13, 2).addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.5F, 5.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -2.5F, -1.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5167F, -2.3333F));

        PartDefinition LegL = body.addOrReplaceChild("LegL", CubeListBuilder.create(), PartPose.offset(2.0F, 1.9833F, 1.1667F));

        PartDefinition BackLegL = LegL.addOrReplaceChild("BackLegL", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition FrontLegL = LegL.addOrReplaceChild("FrontLegL", CubeListBuilder.create().texOffs(20, 2).addBox(-1.5F, 0.0F, -2.7F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -0.3F));

        PartDefinition LegR = body.addOrReplaceChild("LegR", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.9833F, 1.1667F));

        PartDefinition BackLegR = LegR.addOrReplaceChild("BackLegR", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, -2.0F, 0.25F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -0.25F));

        PartDefinition FrontLegR = LegR.addOrReplaceChild("FrontLegR", CubeListBuilder.create().texOffs(20, 2).addBox(-1.5F, 0.0F, -2.7F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -0.3F));

        PartDefinition Tail = body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(21, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0167F, 4.6667F));

        PartDefinition head = anatidae.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -3.2583F, -1.4583F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(20, 5).addBox(-1.5F, -6.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Beck = head.addOrReplaceChild("Beck", CubeListBuilder.create().texOffs(8, 1).addBox(-1.5F, -0.5F, -2.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -1.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.applyHeadRotation(netHeadYaw, headPitch);

        //GLOBAL ANIMATION

        //> WALK
        if(entity.onGround() && !entity.isFlying()){
            this.animateWalk(AnatidaeAnimation.ANATIDAE_WALK, limbSwing, limbSwingAmount, 2f, 2f);
        }

        if(entity.isInWaterOrBubble() && !entity.isFlying()){
            this.animateWalk(AnatidaeAnimation.ANATIDAE_SWIM, limbSwing, limbSwingAmount, 2f, 2f);
        }

        if(entity.isFlying()){
            //> FLY
            this.animateWalk(AnatidaeAnimation.ANATIDAE_FLY, limbSwing, limbSwingAmount, 3f, 3f);

            float partialTick = ageInTicks - entity.tickCount;
            float flyProgress = entity.getFlyProgress(partialTick);
            float rollAmount = entity.getFlightRoll(partialTick) / 57.295776F * flyProgress;
            float pitchAmount = entity.getFlightPitch(partialTick) / 57.295776F * flyProgress;

            anatidae.xRot += pitchAmount;
            anatidae.zRot += rollAmount;
        }

        //> IDLE
        this.animate(entity.eatAnimationState, AnatidaeAnimation.ANATIDAE_EAT, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, AnatidaeAnimation.ANATIDAE_IDLE, ageInTicks, 1f);

        //> IDLE WATER
        this.animate(entity.idleWaterAnimationState, AnatidaeAnimation.ANATIDAE_DIVE, ageInTicks, 1f);
        this.animate(entity.inWaterAnimationState, AnatidaeAnimation.ANATIDAE_IN_WATER, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -90f, 90f);
        headPitch = Mth.clamp(headPitch, -45f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 260f);
        this.head.xRot = headPitch *  ((float)Math.PI / 260f);
    }

    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        this.anatidae.translateAndRotate(poseStack);
        this.head.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);

        poseStack.scale(0.3F, 0.3F, 0.3F);
        poseStack.translate(0.0F, 0.05F, 0.0F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        anatidae.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return anatidae;
    }
}
