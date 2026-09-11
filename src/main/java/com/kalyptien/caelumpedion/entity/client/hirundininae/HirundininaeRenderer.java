package com.kalyptien.caelumpedion.entity.client.hirundininae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.passeriforme.HirundininaeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
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
    }

    @Override
    public ResourceLocation getTextureLocation(HirundininaeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(HirundininaeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}