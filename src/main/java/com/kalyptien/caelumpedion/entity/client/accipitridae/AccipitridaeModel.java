package com.kalyptien.caelumpedion.entity.client.accipitridae;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.FlyingBirdHierarchicalModel;
import com.kalyptien.caelumpedion.entity.client.passeriforme.PasseriformeAnimation;
import com.kalyptien.caelumpedion.entity.custom.bird.accipitriforme.AccipitridaeEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class AccipitridaeModel<T extends AccipitridaeEntity> extends FlyingBirdHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "accipitriforme"), "main");

    protected ModelPart global;

    public AccipitridaeModel(ModelPart root) {
        this.root = root.getChild("accipitriforme");
        this.root = root.getChild("trochilidae");
        this.global = this.root.getChild("global");
        this.body = this.global.getChild("body");
        this.head = this.global.getChild("head");
        
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

        PartDefinition accipitriforme = partdefinition.addOrReplaceChild("accipitriforme", CubeListBuilder.create(), PartPose.offset(0.5F, 17.5F, -2.75F));

        PartDefinition global = accipitriforme.addOrReplaceChild("global", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = global.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 6.75F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -15.4F, -7.2F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingL = wingL.addOrReplaceChild("normalWingL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition backWingL = normalWingL.addOrReplaceChild("backWingL", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -4.5F, -2.5F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 4.5F, 2.5F));

        PartDefinition frontWingL = normalWingL.addOrReplaceChild("frontWingL", CubeListBuilder.create().texOffs(26, 29).addBox(0.0F, -4.5F, -4.0F, 0.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.5F, 9.0F));

        PartDefinition flyingWingL = wingL.addOrReplaceChild("flyingWingL", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, 0.0F, 0.0F, 0.0F, 12.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -15.4F, -7.3F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingR = wingR.addOrReplaceChild("normalWingR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontWingR = normalWingR.addOrReplaceChild("frontWingR", CubeListBuilder.create().texOffs(26, 29).addBox(0.0F, -4.5F, -4.0F, 0.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 4.5F, 9.0F));

        PartDefinition backWingR = normalWingR.addOrReplaceChild("backWingR", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -4.5F, -2.5F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 4.5F, 2.5F));

        PartDefinition flyginWingR = wingR.addOrReplaceChild("flyginWingR", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, 0.0F, 0.0F, 0.0F, 12.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create(), PartPose.offset(-0.5F, -10.9635F, -6.5097F));

        PartDefinition cube_r1 = mainBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 26).addBox(-4.0F, -4.5F, -4.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.9635F, -0.3903F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r2 = mainBody.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.5F, -1.0F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.9635F, -1.4903F, -0.3927F, 0.0F, 0.0F));

        PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create(), PartPose.offset(1.5F, -7.4F, -4.0F));

        PartDefinition backLegL = legL.addOrReplaceChild("backLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r3 = backLegL.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 1.0F, 1.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition middleLegL = legL.addOrReplaceChild("middleLegL", CubeListBuilder.create(), PartPose.offset(-0.1F, 3.1F, 1.2F));

        PartDefinition cube_r4 = middleLegL.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5624F, -0.1226F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.4624F, -0.0774F, -0.3927F, 0.0F, 0.0F));

        PartDefinition frontLegL = legL.addOrReplaceChild("frontLegL", CubeListBuilder.create().texOffs(-3, 3).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, 5.8F, 0.1F));

        PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create(), PartPose.offset(-2.5F, -7.4F, -4.0F));

        PartDefinition backLegR = legR.addOrReplaceChild("backLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r5 = backLegR.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(26, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 1.0F, 1.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition middleLegR = legR.addOrReplaceChild("middleLegR", CubeListBuilder.create(), PartPose.offset(0.1F, 3.1F, 1.2F));

        PartDefinition cube_r6 = middleLegR.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5624F, -0.1226F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.4624F, -0.0774F, -0.3927F, 0.0F, 0.0F));

        PartDefinition frontLegR = legR.addOrReplaceChild("frontLegR", CubeListBuilder.create().texOffs(-3, 3).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 5.8F, 0.1F));

        PartDefinition Tail = body.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -10.7F, 2.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalTail = Tail.addOrReplaceChild("normalTail", CubeListBuilder.create(), PartPose.offset(0.0F, -0.7F, 0.0F));

        PartDefinition cube_r7 = normalTail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(5, 37).addBox(-3.0F, 0.0F, -4.0F, 7.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 2.2345F, 3.7147F, -0.3927F, 0.0F, 0.0F));

        PartDefinition flyingTail = Tail.addOrReplaceChild("flyingTail", CubeListBuilder.create(), PartPose.offset(0.0F, -0.7F, 0.0F));

        PartDefinition cube_r8 = flyingTail.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(30, 45).addBox(-6.5F, -0.05F, 0.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.75F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition head = global.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-0.5F, -3.0F, -3.35F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(0, 29).addBox(-2.0F, -2.5F, -3.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.6F));

        PartDefinition beck = head.addOrReplaceChild("beck", CubeListBuilder.create().texOffs(0, 6).addBox(-0.5F, 1.15F, -3.05F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 2).addBox(-1.0F, -0.85F, -3.05F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.65F, -2.85F));

        PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.3F, -1.0F));

        PartDefinition frontNeck = neck.addOrReplaceChild("frontNeck", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5071F, -3.5858F));

        PartDefinition cube_r9 = frontNeck.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(14, 16).addBox(-2.0F, -5.0F, -1.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -2.2929F, 1.5858F, -0.7854F, 0.0F, 0.0F));

        PartDefinition backNeck = neck.addOrReplaceChild("backNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r10 = backNeck.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 37).addBox(-2.0F, -3.4142F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.7F, 1.1F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    protected void setupWalkAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(AccipitridaeAnimation.ACCIPITRIFORME_WALK, limbSwing, limbSwingAmount, 2f, 10f);
    }

    protected void setupRunAnimation(float limbSwing, float limbSwingAmount){
    }

    protected void setupIdleAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
        this.animate(entity.idleAnimationState, AccipitridaeAnimation.ACCIPITRIFORME_IDLE, ageInTicks, 1f);
    }

    protected void setupEatAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
        this.animate(entity.eatAnimationState, AccipitridaeAnimation.ACCIPITRIFORME_EAT, ageInTicks, 1f);
    }

    protected void setupFlyAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(AccipitridaeAnimation.ACCIPITRIFORME_FLY, limbSwing, limbSwingAmount, 3f, 3f);
    }

    protected void setupSlowFallAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
    }
}
