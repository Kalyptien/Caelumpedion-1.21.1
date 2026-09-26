package com.kalyptien.caelumpedion.entity.client.corvidae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.CorvidaeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
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
    }

    @Override
    public ResourceLocation getTextureLocation(CorvidaeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(CorvidaeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}