package com.kalyptien.caelumpedion.entity.client.anseriforme;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.passeriforme.PasseriformeAnimation;
import com.kalyptien.caelumpedion.entity.custom.AnseriformeEntity;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class AnseriformeModel<T extends AnseriformeEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "anseriforme"), "main");

    private final ModelPart anseriforme;
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
    
    public AnseriformeModel(ModelPart root) {
        this.anseriforme = root.getChild("anseriforme");
        this.body = this.anseriforme.getChild("body");
        this.head = this.anseriforme.getChild("head");
        
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

        PartDefinition anatidae = partdefinition.addOrReplaceChild("anseriforme", CubeListBuilder.create(), PartPose.offset(0.0F, 20.6583F, -1.5417F));

        PartDefinition body = anatidae.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.7417F, 1.375F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create(), PartPose.offset(-2.5F, -3.4167F, -2.8333F));

        PartDefinition normalWingR = wingR.addOrReplaceChild("normalWingR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontWingR = normalWingR.addOrReplaceChild("frontWingR", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition backWingR = normalWingR.addOrReplaceChild("backWingR", CubeListBuilder.create().texOffs(13, 2).addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.5F, 5.0F));

        PartDefinition flyingWaingR = wingR.addOrReplaceChild("flyingWingR", CubeListBuilder.create().texOffs(12, 12).addBox(0.0F, 0.0F, 0.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create(), PartPose.offset(2.5F, -3.4167F, -2.8333F));

        PartDefinition normalWingL = wingL.addOrReplaceChild("normalWingL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontWingL = normalWingL.addOrReplaceChild("frontWingL", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -2.0F, 0.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition backWingL = normalWingL.addOrReplaceChild("backWingL", CubeListBuilder.create().texOffs(13, 2).addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.5F, 5.0F));

        PartDefinition flyingWingL = wingL.addOrReplaceChild("flyingWingL", CubeListBuilder.create().texOffs(12, 12).mirror().addBox(5.0F, 0.0F, 0.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 0.0F, 0.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -2.5F, -1.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5167F, -2.3333F));

        PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create(), PartPose.offset(2.0F, 1.9833F, 1.1667F));

        PartDefinition backLegL = legL.addOrReplaceChild("backLegL", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontLegL = legL.addOrReplaceChild("frontLegL", CubeListBuilder.create().texOffs(20, 2).addBox(-1.5F, 0.0F, -2.7F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -0.3F));

        PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.9833F, 1.1667F));

        PartDefinition backLegR = legR.addOrReplaceChild("backLegR", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, -2.0F, 0.25F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -0.25F));

        PartDefinition frontLegR = legR.addOrReplaceChild("frontLegR", CubeListBuilder.create().texOffs(20, 2).addBox(-1.5F, 0.0F, -2.7F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -0.3F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0167F, 4.6667F));

        PartDefinition flyingTail = tail.addOrReplaceChild("flyingTail", CubeListBuilder.create().texOffs(-6, 22).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition normalTail = tail.addOrReplaceChild("normalTail", CubeListBuilder.create().texOffs(21, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = anatidae.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -3.2583F, -1.4583F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(20, 5).addBox(-1.5F, -6.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition beck = head.addOrReplaceChild("beck", CubeListBuilder.create().texOffs(8, 1).addBox(-1.5F, -0.5F, -2.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -1.5F));

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
                this.animateWalk(AnseriformeAnimation.ANSERIFORME_RUN, limbSwing, limbSwingAmount, 2f, 2f);
            }
            else{
                this.animateWalk(AnseriformeAnimation.ANSERIFORME_WALK, limbSwing, limbSwingAmount, 2f, 2f);
            }
        }

        if(entity.isFlying()){
            //> FLY
            this.animateWalk(AnseriformeAnimation.ANSERIFORME_FLY, limbSwing, limbSwingAmount, 3f, 3f);

            float partialTick = ageInTicks - entity.tickCount;
            float flyProgress = entity.getFlyProgress(partialTick);
            float rollAmount = entity.getFlightRoll(partialTick) / 57.295776F * flyProgress;
            float pitchAmount = entity.getFlightPitch(partialTick) / 57.295776F * flyProgress;

            anseriforme.xRot += pitchAmount;
            anseriforme.zRot += rollAmount;
        }

        //> IDLE
        this.animate(entity.eatAnimationState, AnseriformeAnimation.ANSERIFORME_EAT, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, AnseriformeAnimation.ANSERIFORME_IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -90f, 90f);
        headPitch = Mth.clamp(headPitch, -45f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 260f);
        this.head.xRot = headPitch *  ((float)Math.PI / 260f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        anseriforme.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return anseriforme;
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
