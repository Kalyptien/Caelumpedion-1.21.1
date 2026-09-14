package com.kalyptien.caelumpedion.entity.client.gruiforme;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.GruiformeEntity;
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

public class GruiformeModel<T extends GruiformeEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "gruiforme"), "main");

    private final ModelPart gruiforme;
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
    
    public GruiformeModel(ModelPart root) {
        this.gruiforme = root.getChild("gruiforme");
        this.body = this.gruiforme.getChild("body");
        this.head = this.gruiforme.getChild("head");
        
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

        PartDefinition gruiforme = partdefinition.addOrReplaceChild("gruiforme", CubeListBuilder.create(), PartPose.offset(0.25F, 9.9F, -0.5F));

        PartDefinition body = gruiforme.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.0833F, -3.5797F, 0.5162F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create(), PartPose.offsetAndRotation(2.3333F, -1.4203F, -4.7162F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingL = wingL.addOrReplaceChild("normalWingL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontWingL = normalWingL.addOrReplaceChild("frontWingL", CubeListBuilder.create().texOffs(26, -6).addBox(0.0F, -4.761F, 2.7697F, 0.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.661F, 3.2303F));

        PartDefinition backWingL = normalWingL.addOrReplaceChild("backWingL", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, -4.761F, 3.2697F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 4.661F, -3.2697F));

        PartDefinition flyginWingL = wingL.addOrReplaceChild("flyingWingL", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(0.0F, -1.1F, 0.0F, 0.0F, 12.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.6667F, -1.4203F, -4.7162F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingR = wingR.addOrReplaceChild("normalWingR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontWingR = normalWingR.addOrReplaceChild("frontWingR", CubeListBuilder.create().texOffs(26, -6).addBox(0.0F, -4.5F, -3.0F, 0.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 4.4F, 9.0F));

        PartDefinition backWingR = normalWingR.addOrReplaceChild("backWingR", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, -4.5F, -2.5F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 4.4F, 2.5F));

        PartDefinition flyginWingR = wingR.addOrReplaceChild("flyingWingR", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -1.1F, 0.0F, 0.0F, 12.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create(), PartPose.offset(-0.1667F, 3.7102F, -1.7809F));

        PartDefinition cube_r1 = mainBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.85F, 2.25F, 7.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.1805F, -7.4854F, -0.3927F, 0.0F, 0.0F));

        PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create(), PartPose.offset(2.05F, 6.8403F, -0.2509F));

        PartDefinition backLegL = legL.addOrReplaceChild("backLegL", CubeListBuilder.create(), PartPose.offset(0.0333F, -0.4788F, -0.2694F));

        PartDefinition cube_r2 = backLegL.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 7).addBox(-0.5F, -0.4972F, 0.0334F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.05F, 3.8655F, 1.3207F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r3 = backLegL.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(28, 17).addBox(-1.0F, -1.3491F, -1.0367F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, 1.0674F, 0.2409F, 0.3927F, 0.0F, 0.0F));

        PartDefinition middleLegL = legL.addOrReplaceChild("middleLegL", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0167F, 4.7394F, 1.6347F));

        PartDefinition frontLegL = legL.addOrReplaceChild("frontLegL", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0167F, 10.7394F, 1.6347F));

        PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create(), PartPose.offset(-2.1667F, 6.8332F, -0.2582F));

        PartDefinition backLegR = legR.addOrReplaceChild("backLegR", CubeListBuilder.create(), PartPose.offset(0.0F, -0.4929F, -0.284F));

        PartDefinition cube_r4 = backLegR.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 7).addBox(-0.5F, -0.4972F, 0.0334F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.8866F, 1.3426F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r5 = backLegR.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(28, 17).addBox(-1.0F, -1.4381F, -0.9087F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.1775F, 0.1347F, 0.3927F, 0.0F, 0.0F));

        PartDefinition middleLegR = legR.addOrReplaceChild("middleLegR", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.7465F, 1.642F));

        PartDefinition frontLegR = legR.addOrReplaceChild("frontLegR", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.7465F, 1.642F));

        PartDefinition Tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.1667F, 3.058F, 4.5099F));

        PartDefinition normalTail = Tail.addOrReplaceChild("normalTail", CubeListBuilder.create(), PartPose.offset(0.0F, 1.9205F, 1.5972F));

        PartDefinition cube_r6 = normalTail.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(-5, 39).addBox(-3.5F, 0.0982F, -4.8651F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.603F, 1.7418F, -0.7854F, 0.0F, 0.0F));

        PartDefinition flyingTail = Tail.addOrReplaceChild("flyingTail", CubeListBuilder.create(), PartPose.offset(0.0F, 2.6794F, 6.4028F));

        PartDefinition cube_r7 = flyingTail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(22, 28).addBox(-4.5F, 0.8527F, -7.7247F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5104F, 0.9959F, -0.3927F, 0.0F, 0.0F));

        PartDefinition head = gruiforme.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-0.25F, 0.5F, -6.5F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.4F, -1.0F));

        PartDefinition beck = head.addOrReplaceChild("beck", CubeListBuilder.create().texOffs(16, 32).addBox(-0.5F, -1.0F, -5.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.9F, -3.5F));

        PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(16, 17).addBox(-1.5F, -12.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
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

            if(currentSpeed >= (entity.getAttributeValue(Attributes.MOVEMENT_SPEED))){
                this.animateWalk(GruiformeAnimation.GRUIFORME_RUN, limbSwing, limbSwingAmount, 2f, 2f);
            }
            else{
                this.animateWalk(GruiformeAnimation.GRUIFORME_WALK, limbSwing, limbSwingAmount, 2f, 10f);
            }
        }

        if(entity.isFlying()){
            //> FLY
            this.animateWalk(GruiformeAnimation.GRUIFORME_FLY, limbSwing, limbSwingAmount, 3f, 3f);

            float partialTick = ageInTicks - entity.tickCount;
            float flyProgress = entity.getFlyProgress(partialTick);
            float rollAmount = entity.getFlightRoll(partialTick) / 57.295776F * flyProgress;
            float pitchAmount = entity.getFlightPitch(partialTick) / 57.295776F * flyProgress;

            gruiforme.xRot += pitchAmount;
            gruiforme.zRot += rollAmount;
        }

        //> IDLE
        this.animate(entity.eatAnimationState, GruiformeAnimation.GRUIFORME_EAT, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, GruiformeAnimation.GRUIFORME_IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -90f, 90f);
        headPitch = Mth.clamp(headPitch, -45f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 260f);
        this.head.xRot = headPitch *  ((float)Math.PI / 260f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        gruiforme.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return gruiforme;
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
