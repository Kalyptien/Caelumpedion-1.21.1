package com.kalyptien.caelumpedion.entity.client.accipitriforme;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.FlyingBirdHierarchicalModel;
import com.kalyptien.caelumpedion.entity.custom.AccipitriformeEntity;
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
import net.minecraft.world.phys.Vec3;

public class AccipitriformeModel<T extends AccipitriformeEntity> extends FlyingBirdHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "accipitriforme"), "main");

    private final ModelPart neck;
    private final ModelPart thinNeck;
    private final ModelPart thickNeck;

    private final ModelPart beck;
    
    public AccipitriformeModel(ModelPart root) {
        this.root = root.getChild("accipitriforme");
        this.body = this.root.getChild("body");
        this.head = this.root.getChild("head");
        
        this.wingR = this.body.getChild("wingR");
        this.normalWingR = this.wingR.getChild("normalWingR");
        this.flyingWingR = this.wingR.getChild("flyingWingR");
        this.wingL = this.body.getChild("wingL");
        this.normalWingL = this.wingL.getChild("normalWingL");
        this.flyingWingL = this.wingL.getChild("flyingWingL");
        this.tail = this.body.getChild("tail");
        this.flyingTail = this.tail.getChild("flyingTail");
        this.normalTail = this.tail.getChild("normalTail");

        this.neck = this.head.getChild("neck");
        this.thinNeck = this.neck.getChild("thinNeck");
        this.thickNeck = this.neck.getChild("thickNeck");

        this.beck = this.head.getChild("beck");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition accipitriforme = partdefinition.addOrReplaceChild("accipitriforme", CubeListBuilder.create(), PartPose.offset(0.5F, 17.5F, -2.75F));

        PartDefinition body = accipitriforme.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 6.75F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -14.9F, -8.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingL = wingL.addOrReplaceChild("normalWingL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition backWingL = normalWingL.addOrReplaceChild("backWingL", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -4.5F, -2.5F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 4.5F, 2.5F));

        PartDefinition frontWingL = normalWingL.addOrReplaceChild("frontWingL", CubeListBuilder.create().texOffs(41, 0).addBox(0.0F, -4.5F, -4.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.5F, 9.0F));

        PartDefinition flyingWingL = wingL.addOrReplaceChild("flyingWingL", CubeListBuilder.create().texOffs(1, 31).addBox(0.0F, 0.0F, 0.0F, 0.0F, 12.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, -14.9F, -8.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition normalWingR = wingR.addOrReplaceChild("normalWingR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontWingR = normalWingR.addOrReplaceChild("frontWingR", CubeListBuilder.create().texOffs(41, 0).addBox(0.0F, -4.5F, -4.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 4.5F, 9.0F));

        PartDefinition backWingR = normalWingR.addOrReplaceChild("backWingR", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -4.5F, -2.5F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 4.5F, 2.5F));

        PartDefinition flyginWingR = wingR.addOrReplaceChild("flyingWingR", CubeListBuilder.create().texOffs(1, 31).addBox(0.0F, 0.0F, 0.0F, 0.0F, 12.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create(), PartPose.offset(-0.5F, -10.5997F, -4.463F));

        PartDefinition cube_r1 = mainBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.5F, -3.0F, 8.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.6997F, -3.537F, -0.3927F, 0.0F, 0.0F));

        PartDefinition legL = body.addOrReplaceChild("legL", CubeListBuilder.create(), PartPose.offset(2.0F, -7.4F, -3.0F));

        PartDefinition backLegL = legL.addOrReplaceChild("backLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = backLegL.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 1.0F, 1.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition middleLegL = legL.addOrReplaceChild("middleLegL", CubeListBuilder.create(), PartPose.offset(-0.1F, 3.1F, 1.2F));

        PartDefinition cube_r3 = middleLegL.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 8).addBox(-0.5F, -0.5624F, -0.1226F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.4624F, -0.0774F, -0.3927F, 0.0F, 0.0F));

        PartDefinition frontLegL = legL.addOrReplaceChild("frontLegL", CubeListBuilder.create().texOffs(2, 8).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, 5.8F, 0.1F));

        PartDefinition legR = body.addOrReplaceChild("legR", CubeListBuilder.create(), PartPose.offset(-3.0F, -7.4F, -3.0F));

        PartDefinition backLegR = legR.addOrReplaceChild("backLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = backLegR.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 1.0F, 1.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition middleLegR = legR.addOrReplaceChild("middleLegR", CubeListBuilder.create(), PartPose.offset(0.1F, 3.1F, 1.2F));

        PartDefinition cube_r5 = middleLegR.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 8).addBox(-0.5F, -0.5624F, -0.1226F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.4624F, -0.0774F, -0.3927F, 0.0F, 0.0F));

        PartDefinition frontLegR = legR.addOrReplaceChild("frontLegR", CubeListBuilder.create().texOffs(2, 8).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 5.8F, 0.1F));

        PartDefinition Tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.5F, -11.4F, 2.0F));

        PartDefinition normalTail = Tail.addOrReplaceChild("normalTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r6 = normalTail.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(33, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5345F, 3.7147F, -0.3927F, 0.0F, 0.0F));

        PartDefinition flyingTail = Tail.addOrReplaceChild("flyingTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = flyingTail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(1, 34).addBox(-8.5F, -0.05F, 0.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.05F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition head = accipitriforme.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-0.5F, -3.0F, -3.25F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(30, 22).addBox(-2.0F, -1.5F, -6.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.9F, 2.0F));

        PartDefinition beck = head.addOrReplaceChild("beck", CubeListBuilder.create().texOffs(7, 9).addBox(-1.0F, 1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 6).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -7.4F, -4.0F));

        PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.3F, -1.0F));

        PartDefinition thinNeck = neck.addOrReplaceChild("thinNeck", CubeListBuilder.create(), PartPose.offset(0.5F, -8.3F, -1.5F));

        PartDefinition cube_r8 = thinNeck.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(18, 20).addBox(-2.0F, -8.0F, -1.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 1.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition thickNeck = neck.addOrReplaceChild("thickNeck", CubeListBuilder.create(), PartPose.offset(0.5F, -8.3F, -1.5F));

        PartDefinition cube_r9 = thickNeck.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, -8.0F, -1.0F, 5.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 1.0F, -0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    protected void setupWalkAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(AccipitriformeAnimation.ACCIPITRIFORME_WALK, limbSwing, limbSwingAmount, 2f, 10f);
    }

    protected void setupRunAnimation(float limbSwing, float limbSwingAmount){
    }

    protected void setupIdleAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
        this.animate(entity.idleAnimationState, AccipitriformeAnimation.ACCIPITRIFORME_IDLE, ageInTicks, 1f);
    }

    protected void setupEatAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
        this.animate(entity.eatAnimationState, AccipitriformeAnimation.ACCIPITRIFORME_EAT, ageInTicks, 1f);
    }

    protected void setupFlyAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(AccipitriformeAnimation.ACCIPITRIFORME_FLY, limbSwing, limbSwingAmount, 3f, 3f);
    }

    protected void setupSlowFallAnimation(float limbSwing, float limbSwingAmount){
    }

    public void showTickNeck(boolean thickNeck){
        this.thinNeck.visible = !thickNeck;
        this.thickNeck.visible = thickNeck;
    }
}
