package com.kalyptien.caelumpedion.entity.client.hirundininae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.BirdHierarchicalModel;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.HirundininaeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.event.CaelumpedionRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class HirundininaeRenderer extends MobRenderer<HirundininaeEntity, HirundininaeModel<HirundininaeEntity>> {

    private static final Map<HirundininaeEntity.HirundininaeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(HirundininaeEntity.HirundininaeVariant.class), map -> {
                for (int i = 0; i < HirundininaeEntity.HirundininaeVariant.lenght(); i++) {
                    HirundininaeEntity.HirundininaeVariant currentVariant = HirundininaeEntity.HirundininaeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/hirundininae/" + currentVariant.getFileName() + ".png"));
                }

            });

    public HirundininaeRenderer(EntityRendererProvider.Context context) {
        super(context, new HirundininaeModel<>(context.bakeLayer(HirundininaeModel.LAYER_LOCATION)), 0.25f);
        this.addLayer(new IridescentFeatherLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(HirundininaeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(HirundininaeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(entity.getVariant().getSize(),
                entity.getVariant().getSize(),
                entity.getVariant().getSize());

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    //Iridescent Feather Layer

    static class IridescentFeatherLayer<T extends BirdEntity, M extends BirdHierarchicalModel<T>> extends RenderLayer<T, M> {

        private static final Map<HirundininaeEntity.HirundininaeVariant, ResourceLocation> IRIDESCENT_BY_VARIANT =
                Util.make(Maps.newEnumMap(HirundininaeEntity.HirundininaeVariant.class), map -> {
                    for (int i = 0; i < HirundininaeEntity.HirundininaeVariant.lenght(); i++) {
                        HirundininaeEntity.HirundininaeVariant currentVariant = HirundininaeEntity.HirundininaeVariant.byId(i);
                        if(currentVariant.isIridescent()){
                            map.put(currentVariant,
                                    ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/corvidae/" + currentVariant.getFileName() + "_iridescent.png"));
                        }
                    }

                });

        public IridescentFeatherLayer(RenderLayerParent<T, M> renderer) {
            super(renderer);
        }

        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation resourcelocation = (ResourceLocation)IRIDESCENT_BY_VARIANT.get(livingEntity.getVariant());
            if (resourcelocation != null && !livingEntity.isInvisible()) {
                VertexConsumer vertexconsumer = buffer.getBuffer(CaelumpedionRenderTypes.iridescenteFeather(resourcelocation));

                this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F));
            }
        }
    }
}