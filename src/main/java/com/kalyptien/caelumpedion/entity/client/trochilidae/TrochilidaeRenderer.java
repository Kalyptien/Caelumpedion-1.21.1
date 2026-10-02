package com.kalyptien.caelumpedion.entity.client.trochilidae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.bird.apodiforme.TrochilidaeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
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
}