package com.kalyptien.caelumpedion.entity.client.ramphastidae;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.piciforme.RamphastidaeEntity;
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

public class RamphastidaeModel<T extends RamphastidaeEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "ramphastidae"), "main");

    private final ModelPart ramphastidae;
    private final ModelPart body;
    private final ModelPart head;

    private final ModelPart wingR;
    private final ModelPart normalWingR;
    private final ModelPart flyingWingR;
    private final ModelPart wingL;
    private final ModelPart normalWingL;
    private final ModelPart flyingWingL;
    private final ModelPart tail;
    private final ModelPart flyingTail;
    private final ModelPart normalTail;
    
    public RamphastidaeModel(ModelPart root) {
        this.ramphastidae = root.getChild("ramphastidae");
        this.body = this.ramphastidae.getChild("body");
        this.head = this.ramphastidae.getChild("head");
        
        this.wingR = this.body.getChild("wingR");
        this.normalWingR = this.wingR.getChild("normalWingR");
        this.flyingWingR = this.wingR.getChild("flyingWingR");
        this.wingL = this.body.getChild("wingL");
        this.normalWingL = this.wingL.getChild("normalWingL");
        this.flyingWingL = this.wingL.getChild("flyingWingL");
        this.tail = this.body.getChild("tail");
        this.flyingTail = this.tail.getChild("flyingTail");
        this.normalTail = this.tail.getChild("normalTail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ramphastidae = partdefinition.addOrReplaceChild("ramphastidae", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = ramphastidae.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -1.0F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -5.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingL = wingL.addOrReplaceChild("normalWingL", CubeListBuilder.create().texOffs(12, 11).addBox(0.0F, 0.0F, 0.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, -3).addBox(1.0F, 0.0F, 3.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flyingWingL = wingL.addOrReplaceChild("flyingWingL", CubeListBuilder.create().texOffs(12, 15).addBox(-4.0F, 0.0F, 0.0F, 0.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -5.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingR = wingR.addOrReplaceChild("normalWingR", CubeListBuilder.create().texOffs(12, 11).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, -3).addBox(-1.0F, 0.0F, 3.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flyingWingR = wingR.addOrReplaceChild("flyingWingR", CubeListBuilder.create().texOffs(12, 15).addBox(0.0F, 0.0F, 0.0F, 0.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create(), PartPose.offset(0.0F, -1.4239F, 1.8827F));

        PartDefinition cube_r1 = mainBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -3.5F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9239F, -0.3827F, -0.3927F, 0.0F, 0.0F));

        PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create(), PartPose.offset(1.4F, 0.4667F, 1.05F));

        PartDefinition backLegL = legL.addOrReplaceChild("backLegL", CubeListBuilder.create(), PartPose.offset(0.0F, -0.6199F, -0.3794F));

        PartDefinition cube_r2 = backLegL.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 19).mirror().addBox(-0.5F, -2.0539F, -1.4683F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 1.3325F, 1.6446F, 0.3927F, 0.0F, 0.0F));

        PartDefinition frontLegL = legL.addOrReplaceChild("frontLegL", CubeListBuilder.create().texOffs(-2, 3).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.4333F, 0.45F));

        PartDefinition middleLegL = legL.addOrReplaceChild("middleLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 1.8309F, 1.1322F));

        PartDefinition cube_r3 = middleLegL.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.9763F, -0.0635F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.7025F, -0.2322F, -0.3927F, 0.0F, 0.0F));

        PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create(), PartPose.offset(-1.4F, 0.2667F, 1.0333F));

        PartDefinition backLegR = legR.addOrReplaceChild("backLegR", CubeListBuilder.create(), PartPose.offset(0.0F, -0.4199F, -0.4627F));

        PartDefinition cube_r4 = backLegR.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(12, 19).addBox(-1.5F, -2.0539F, -1.4683F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.3325F, 1.6446F, 0.3927F, 0.0F, 0.0F));

        PartDefinition frontLegR = legR.addOrReplaceChild("frontLegR", CubeListBuilder.create().texOffs(-2, 3).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.6333F, 0.4667F));

        PartDefinition middleLegR = legR.addOrReplaceChild("middleLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0309F, 1.1489F));

        PartDefinition cube_r5 = middleLegR.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.9763F, -0.0635F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.7025F, -0.2322F, -0.3927F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, 5.8F, 0.3927F, 0.0F, 0.0F));

        PartDefinition normalTail = tail.addOrReplaceChild("normalTail", CubeListBuilder.create().texOffs(15, 11).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flyingTail = tail.addOrReplaceChild("flyingTail", CubeListBuilder.create().texOffs(13, 16).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = ramphastidae.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -2.2F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, -5.5F, -2.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.7F));

        PartDefinition beck = head.addOrReplaceChild("beck", CubeListBuilder.create().texOffs(0, 21).addBox(-0.5F, -1.5F, -5.1F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, -1.7F));

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
                this.animateWalk(RamphastidaeAnimation.RAMPHASTIDAE_RUN, limbSwing, limbSwingAmount, 2f, 2f);
            }
            else{
                this.animateWalk(RamphastidaeAnimation.RAMPHASTIDAE_WALK, limbSwing, limbSwingAmount, 2f, 3f);
            }
        }

        if(entity.isFlying()){
            //> FLY
            this.animateWalk(RamphastidaeAnimation.RAMPHASTIDAE_FLY, limbSwing, limbSwingAmount, 3f, 3f);

            float partialTick = ageInTicks - entity.tickCount;
            float flyProgress = entity.getFlyProgress(partialTick);
            float rollAmount = entity.getFlightRoll(partialTick) / 57.295776F * flyProgress;
            float pitchAmount = entity.getFlightPitch(partialTick) / 57.295776F * flyProgress;

            ramphastidae.xRot += pitchAmount;
            ramphastidae.zRot += rollAmount;
        }

        //> IDLE
        this.animate(entity.eatAnimationState, RamphastidaeAnimation.RAMPHASTIDAE_EAT, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, RamphastidaeAnimation.RAMPHASTIDAE_IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -90f, 90f);
        headPitch = Mth.clamp(headPitch, -45f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 260f);
        this.head.xRot = headPitch *  ((float)Math.PI / 260f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        ramphastidae.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return ramphastidae;
    }

    private void showFlyingPart(boolean show){
            this.normalTail.visible = !show;
            this.normalWingL.visible = !show;
            this.normalWingR.visible = !show;

            this.flyingTail.visible = show;
            this.flyingWingL.visible = show;
            this.flyingWingR.visible = show;
    }

    private double getCurrentBirdSpeed(T entity){
        Vec3 delta = entity.getDeltaMovement();
        return Math.sqrt(delta.x * delta.x + delta.y * delta.y + delta.z * delta.z);
    }
}
