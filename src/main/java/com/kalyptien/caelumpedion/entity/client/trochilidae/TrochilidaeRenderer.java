package com.kalyptien.caelumpedion.entity.client.trochilidae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.BirdHierarchicalModel;
import com.kalyptien.caelumpedion.entity.custom.bird.apodiforme.TrochilidaeEntity;
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

public class TrochilidaeRenderer extends MobRenderer<TrochilidaeEntity, TrochilidaeModel<TrochilidaeEntity>> {

    private static final Map<TrochilidaeEntity.TrochilidaeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(TrochilidaeEntity.TrochilidaeVariant.class), map -> {
                for (int i = 0; i < TrochilidaeEntity.TrochilidaeVariant.lenght(); i++) {
                    TrochilidaeEntity.TrochilidaeVariant currentVariant = TrochilidaeEntity.TrochilidaeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/apodiforme/trochilidae/" + currentVariant.getFileName() + ".png"));
                }

            });

    public TrochilidaeRenderer(EntityRendererProvider.Context context) {
        super(context, new TrochilidaeModel<>(context.bakeLayer(TrochilidaeModel.LAYER_LOCATION)), 0.25f);
        this.addLayer(new IridescentFeatherLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(TrochilidaeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(TrochilidaeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(entity.getVariant().getSize(),
                entity.getVariant().getSize(),
                entity.getVariant().getSize());

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    //Iridescent Feather Layer

    static class IridescentFeatherLayer<T extends BirdEntity, M extends BirdHierarchicalModel<T>> extends RenderLayer<T, M> {

        private static final Map<TrochilidaeEntity.TrochilidaeVariant, ResourceLocation> IRIDESCENT_BY_VARIANT =
                Util.make(Maps.newEnumMap(TrochilidaeEntity.TrochilidaeVariant.class), map -> {
                    for (int i = 0; i < TrochilidaeEntity.TrochilidaeVariant.lenght(); i++) {
                        TrochilidaeEntity.TrochilidaeVariant currentVariant = TrochilidaeEntity.TrochilidaeVariant.byId(i);
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