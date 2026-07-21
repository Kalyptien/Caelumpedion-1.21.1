package com.kalyptien.caelumpedion.entity.client.passeriforme;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.PasseriformeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class PasseriformeModel<T extends PasseriformeEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "passeriforme"), "main");

    private final ModelPart passeriforme;
    private final ModelPart body;
    private final ModelPart wingL;
    private final ModelPart wingR;
    private final ModelPart mainBody;
    private final ModelPart LegL;
    private final ModelPart BackLegL;
    private final ModelPart FrontLegL;
    private final ModelPart MiddleLegL;
    private final ModelPart LegR;
    private final ModelPart BackLegR;
    private final ModelPart FrontLegR;
    private final ModelPart MiddleLegR;
    private final ModelPart Tail;
    private final ModelPart head;
    private final ModelPart mainHead;
    private final ModelPart Beck;
    public PasseriformeModel(ModelPart root) {
        this.passeriforme = root.getChild("passeriforme");
        this.body = this.passeriforme.getChild("body");
        this.wingL = this.body.getChild("wingL");
        this.wingR = this.body.getChild("wingR");
        this.mainBody = this.body.getChild("mainBody");
        this.LegL = this.body.getChild("LegL");
        this.BackLegL = this.LegL.getChild("BackLegL");
        this.FrontLegL = this.LegL.getChild("FrontLegL");
        this.MiddleLegL = this.LegL.getChild("MiddleLegL");
        this.LegR = this.body.getChild("LegR");
        this.BackLegR = this.LegR.getChild("BackLegR");
        this.FrontLegR = this.LegR.getChild("FrontLegR");
        this.MiddleLegR = this.LegR.getChild("MiddleLegR");
        this.Tail = this.body.getChild("Tail");
        this.head = this.passeriforme.getChild("head");
        this.mainHead = this.head.getChild("mainHead");
        this.Beck = this.head.getChild("Beck");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition passeriforme = partdefinition.addOrReplaceChild("passeriforme", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = passeriforme.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -1.0F));

        PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create().texOffs(8, 7).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, -1).addBox(1.0F, 0.0F, 2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.2F, 0.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create().texOffs(8, 7).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(10, -1).addBox(-1.0F, 0.0F, 2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.2F, 0.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0059F, -0.5913F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition LegL = body.addOrReplaceChild("LegL", CubeListBuilder.create(), PartPose.offset(1.0F, 3.0F, 2.0F));

        PartDefinition BackLegL = LegL.addOrReplaceChild("BackLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = BackLegL.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 0).addBox(-0.5F, -0.004F, 0.841F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.404F, -0.741F, 0.7854F, 0.0F, 0.0F));

        PartDefinition FrontLegL = LegL.addOrReplaceChild("FrontLegL", CubeListBuilder.create().texOffs(13, 3).addBox(-0.5F, 0.0F, -0.9F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.9F, -0.4F));

        PartDefinition MiddleLegL = LegL.addOrReplaceChild("MiddleLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.55F));

        PartDefinition cube_r2 = MiddleLegL.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 5).addBox(-0.5F, 0.9F, -0.35F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -1.1781F, 0.0F, 0.0F));

        PartDefinition LegR = body.addOrReplaceChild("LegR", CubeListBuilder.create(), PartPose.offset(-1.0F, 3.0F, 2.0F));

        PartDefinition BackLegR = LegR.addOrReplaceChild("BackLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r3 = BackLegR.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 1).addBox(-0.5F, -0.004F, 0.841F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.404F, -0.741F, 0.7854F, 0.0F, 0.0F));

        PartDefinition FrontLegR = LegR.addOrReplaceChild("FrontLegR", CubeListBuilder.create().texOffs(13, 2).addBox(-0.5F, 0.0F, -0.9F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.9F, -0.4F));

        PartDefinition MiddleLegR = LegR.addOrReplaceChild("MiddleLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.5F));

        PartDefinition cube_r4 = MiddleLegR.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 4).addBox(-0.5F, 0.9F, -0.35F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.05F, -1.1781F, 0.0F, 0.0F));

        PartDefinition Tail = body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(-4, 12).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3F, 3.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition head = passeriforme.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -1.0F));

        PartDefinition mainHead = head.addOrReplaceChild("mainHead", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -2.5F, -1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 12).addBox(0.0F, -4.5F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Beck = head.addOrReplaceChild("Beck", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -0.5F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.4F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        this.animate(entity.walkAnimationState, PasseriformeAnimation.PASSERIFORME_WALK, ageInTicks, 1f);

        //IDLE
        this.animate(entity.idlePickAnimationState, PasseriformeAnimation.PASSERIFORME_IDLE_PICK, ageInTicks, 1f);
        this.animate(entity.idleLookAnimationState, PasseriformeAnimation.PASSERIFORME_IDLE_LOOK, ageInTicks, 1f);

        //FLY
        this.animate(entity.flyAnimationState, PasseriformeAnimation.PASSERIFORME_FLY, ageInTicks, 1f);
        this.animate(entity.planeAnimationState, PasseriformeAnimation.PASSERIFORME_PLANE, ageInTicks, 1f);
        this.animate(entity.landAnimationState, PasseriformeAnimation.PASSERIFORME_LAND, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        passeriforme.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return passeriforme;
    }
}
