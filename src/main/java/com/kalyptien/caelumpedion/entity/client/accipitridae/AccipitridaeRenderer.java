package com.kalyptien.caelumpedion.entity.client.accipitridae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.bird.accipitriforme.AccipitridaeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class AccipitridaeRenderer extends MobRenderer<AccipitridaeEntity, AccipitridaeModel<AccipitridaeEntity>> {

    private static final Map<AccipitridaeEntity.AccipitridaeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(AccipitridaeEntity.AccipitridaeVariant.class), map -> {
                for (int i = 0; i < AccipitridaeEntity.AccipitridaeVariant.lenght(); i++) {
                    AccipitridaeEntity.AccipitridaeVariant currentVariant = AccipitridaeEntity.AccipitridaeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/accipitriforme/" + currentVariant.getFileName() + ".png"));
                }

            });

    public AccipitridaeRenderer(EntityRendererProvider.Context context) {
        super(context, new AccipitridaeModel<>(context.bakeLayer(AccipitridaeModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(AccipitridaeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(AccipitridaeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1, 1, 1);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}