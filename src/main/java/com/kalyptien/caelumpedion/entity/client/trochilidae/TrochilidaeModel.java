package com.kalyptien.caelumpedion.entity.client.trochilidae;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.FlyingBirdHierarchicalModel;
import com.kalyptien.caelumpedion.entity.custom.bird.apodiforme.TrochilidaeEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class TrochilidaeModel<T extends TrochilidaeEntity> extends FlyingBirdHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "trochilidae"), "main");

    public TrochilidaeModel(ModelPart root) {
        this.root = root.getChild("trochilidae");
        this.body = this.root.getChild("body");
        this.head = this.root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition trochilidae = partdefinition.addOrReplaceChild("trochilidae", CubeListBuilder.create(), PartPose.offset(0.0F, 20.416F, -0.075F));

        PartDefinition body = trochilidae.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.716F, 0.625F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -2.132F, -0.6501F, -0.7854F, 0.0F, 0.0F));

        PartDefinition normalWingL = wingL.addOrReplaceChild("normalWingL", CubeListBuilder.create().texOffs(8, 6).addBox(0.0F, 1.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(1.0F, 1.0F, 2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.99F, 0.0F));

        PartDefinition flyingWingL = wingL.addOrReplaceChild("flyingWingL", CubeListBuilder.create(), PartPose.offset(0.0F, -0.99F, 0.0F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.132F, -0.6501F, -0.7854F, 0.0F, 0.0F));

        PartDefinition normalWingR = wingR.addOrReplaceChild("normalWingR", CubeListBuilder.create().texOffs(8, 6).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-1.0F, 0.0F, 2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flyingWingR = wingR.addOrReplaceChild("flyingWingR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.04F, -0.5498F, -0.7854F, 0.0F, 0.0F));

        PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create(), PartPose.offset(1.0F, 1.868F, 0.0499F));

        PartDefinition backLegL = legL.addOrReplaceChild("backLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.6F, 0.4F));

        PartDefinition middleLegL = legL.addOrReplaceChild("middleLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.05F));

        PartDefinition cube_r1 = middleLegL.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.9F, -0.35F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7F, 0.7F, -0.3927F, 0.0F, 0.0F));

        PartDefinition frontLegL = legL.addOrReplaceChild("frontLegL", CubeListBuilder.create().texOffs(-1, 2).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.9F, -0.4F));

        PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.868F, 0.0499F));

        PartDefinition backLegR = legR.addOrReplaceChild("backLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.6F, 0.4F));

        PartDefinition middleLegR = legR.addOrReplaceChild("middleLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.1F));

        PartDefinition cube_r2 = middleLegR.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.9F, -0.35F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7F, 0.75F, -0.3927F, 0.0F, 0.0F));

        PartDefinition frontLegR = legR.addOrReplaceChild("frontLegR", CubeListBuilder.create().texOffs(-1, 2).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.9F, -0.4F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.768F, 1.7499F, -0.7854F, 0.0F, 0.0F));

        PartDefinition normalTail = tail.addOrReplaceChild("normalTail", CubeListBuilder.create().texOffs(8, 0).addBox(-1.5F, 0.2F, -0.1F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.1414F, -0.1414F));

        PartDefinition flyingTail = tail.addOrReplaceChild("flyingTail", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1414F, -0.1414F));

        PartDefinition head = trochilidae.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -0.716F, -0.625F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(7, 10).addBox(-1.0F, -2.5F, -1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition beck = head.addOrReplaceChild("beck", CubeListBuilder.create().texOffs(0, 6).addBox(-0.5F, -0.5F, -3.1F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.4F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    protected void setupWalkAnimation(float limbSwing, float limbSwingAmount){
    }

    protected void setupRunAnimation(float limbSwing, float limbSwingAmount){
    }

    protected void setupIdleAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
        this.animate(entity.idleAnimationState, TrochilidaeAnimation.TROCHILIDAE_IDLE, ageInTicks, 1f);
    }

    protected void setupEatAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
        this.animate(entity.eatAnimationState, TrochilidaeAnimation.TROCHILIDAE_EAT, ageInTicks, 1f);
    }

    protected void setupFlyAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(TrochilidaeAnimation.TROCHILIDAE_FLY, limbSwing, limbSwingAmount, 3f, 3f);
    }

    protected void setupSlowFallAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(TrochilidaeAnimation.TROCHILIDAE_FLY, limbSwing, limbSwingAmount, 3f, 3f);
    }

    protected void showFlyingPart(boolean show){
    }
}
