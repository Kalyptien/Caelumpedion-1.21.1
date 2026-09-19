package com.kalyptien.caelumpedion.entity.client.anseriforme;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.FlyingBirdHierarchicalModel;
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

public class AnseriformeModel<T extends AnseriformeEntity> extends FlyingBirdHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "anseriforme"), "main");
    
    public AnseriformeModel(ModelPart root) {
        this.root = root.getChild("anseriforme");
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

    protected void setupWalkAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(AnseriformeAnimation.ANSERIFORME_WALK, limbSwing, limbSwingAmount, 2f, 10f);
    }

    protected void setupRunAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(AnseriformeAnimation.ANSERIFORME_RUN, limbSwing, limbSwingAmount, 2f, 2f);
    }

    protected void setupIdleAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
        this.animate(entity.idleAnimationState, AnseriformeAnimation.ANSERIFORME_IDLE, ageInTicks, 1f);
    }

    protected void setupEatAnimation(T entity,float limbSwing, float limbSwingAmount, float ageInTicks){
        this.animate(entity.eatAnimationState, AnseriformeAnimation.ANSERIFORME_EAT, ageInTicks, 1f);
    }

    protected void setupFlyAnimation(float limbSwing, float limbSwingAmount){
        this.animateWalk(AnseriformeAnimation.ANSERIFORME_FLY, limbSwing, limbSwingAmount, 3f, 3f);
    }

    protected void setupSlowFallAnimation(float limbSwing, float limbSwingAmount){
    }
}
