package com.kalyptien.caelumpedion.entity.client.columbiforme;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.client.BirdHierarchicalModel;
import com.kalyptien.caelumpedion.entity.client.corvidae.CorvidaeRenderer;
import com.kalyptien.caelumpedion.entity.custom.bird.columbiforme.ColumbiformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.CorvidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.BirdEntity;
import com.kalyptien.caelumpedion.event.ModRenderTypes;
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

public class ColumbiformeRenderer extends MobRenderer<ColumbiformeEntity, ColumbiformeModel<ColumbiformeEntity>> {

    private static final Map<ColumbiformeEntity.ColumbiformeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ColumbiformeEntity.ColumbiformeVariant.class), map -> {
                for (int i = 0; i < ColumbiformeEntity.ColumbiformeVariant.lenght(); i++) {
                    ColumbiformeEntity.ColumbiformeVariant currentVariant = ColumbiformeEntity.ColumbiformeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/columbiforme/" + currentVariant.getFileName() + ".png"));
                }

            });

    public ColumbiformeRenderer(EntityRendererProvider.Context context) {
        super(context, new ColumbiformeModel<>(context.bakeLayer(ColumbiformeModel.LAYER_LOCATION)), 0.25f);
        this.addLayer(new IridescentFeatherLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ColumbiformeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(ColumbiformeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(entity.getVariant().getSize(),
                entity.getVariant().getSize(),
                entity.getVariant().getSize());

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    //Iridescent Feather Layer

    static class IridescentFeatherLayer<T extends BirdEntity, M extends BirdHierarchicalModel<T>> extends RenderLayer<T, M> {

        private static final Map<ColumbiformeEntity.ColumbiformeVariant, ResourceLocation> IRIDESCENT_BY_VARIANT =
                Util.make(Maps.newEnumMap(ColumbiformeEntity.ColumbiformeVariant.class), map -> {
                    for (int i = 0; i < ColumbiformeEntity.ColumbiformeVariant.lenght(); i++) {
                        ColumbiformeEntity.ColumbiformeVariant currentVariant = ColumbiformeEntity.ColumbiformeVariant.byId(i);
                        if(currentVariant.isIridescent()){
                            map.put(currentVariant,
                                    ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/columbiforme/" + currentVariant.getFileName() + "_iridescent.png"));
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