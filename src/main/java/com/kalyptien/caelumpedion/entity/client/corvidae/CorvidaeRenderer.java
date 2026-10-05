package com.kalyptien.caelumpedion.entity.client.corvidae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.BirdHierarchicalModel;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.CorvidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.event.ModRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class CorvidaeRenderer extends MobRenderer<CorvidaeEntity, CorvidaeModel<CorvidaeEntity>> {

    private static final Map<CorvidaeEntity.CorvidaeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CorvidaeEntity.CorvidaeVariant.class), map -> {
                for (int i = 0; i < CorvidaeEntity.CorvidaeVariant.lenght(); i++) {
                    CorvidaeEntity.CorvidaeVariant currentVariant = CorvidaeEntity.CorvidaeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/corvidae/" + currentVariant.getFileName() + ".png"));
                }

            });

    public CorvidaeRenderer(EntityRendererProvider.Context context) {
        super(context, new CorvidaeModel<>(context.bakeLayer(CorvidaeModel.LAYER_LOCATION)), 0.25f);
        this.addLayer(new IridescentFeatherLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(CorvidaeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(CorvidaeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(entity.getVariant().getSize(),
                entity.getVariant().getSize(),
                entity.getVariant().getSize());

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    //Iridescent Feather Layer

    static class IridescentFeatherLayer<T extends BirdEntity, M extends BirdHierarchicalModel<T>> extends RenderLayer<T, M> {

        private static final Map<CorvidaeEntity.CorvidaeVariant, ResourceLocation> IRIDESCENT_BY_VARIANT =
                Util.make(Maps.newEnumMap(CorvidaeEntity.CorvidaeVariant.class), map -> {
                    for (int i = 0; i < CorvidaeEntity.CorvidaeVariant.lenght(); i++) {
                        CorvidaeEntity.CorvidaeVariant currentVariant = CorvidaeEntity.CorvidaeVariant.byId(i);
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
                VertexConsumer vertexconsumer = buffer.getBuffer(ModRenderTypes.iridescenteFeather(resourcelocation));

                this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F));
            }
        }
    }
}