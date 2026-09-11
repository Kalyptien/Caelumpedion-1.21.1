package com.kalyptien.caelumpedion.entity.client.gruiforme;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.GruiformeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class GruiformeRenderer extends MobRenderer<GruiformeEntity, GruiformeModel<GruiformeEntity>> {

    private static final Map<GruiformeEntity.GruiformeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GruiformeEntity.GruiformeVariant.class), map -> {
                for (int i = 0; i < GruiformeEntity.GruiformeVariant.lenght(); i++) {
                    GruiformeEntity.GruiformeVariant currentVariant = GruiformeEntity.GruiformeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/gruiforme/" + currentVariant.getFileName() + ".png"));
                }

            });

    public GruiformeRenderer(EntityRendererProvider.Context context) {
        super(context, new GruiformeModel<>(context.bakeLayer(GruiformeModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(GruiformeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(GruiformeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1f,1f,1f);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}