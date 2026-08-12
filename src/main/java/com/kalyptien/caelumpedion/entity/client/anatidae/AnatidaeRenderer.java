package com.kalyptien.caelumpedion.entity.client.anatidae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.AnatidaeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class AnatidaeRenderer extends MobRenderer<AnatidaeEntity, AnatidaeModel<AnatidaeEntity>> {

    private static final Map<AnatidaeEntity.AnatidaeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(AnatidaeEntity.AnatidaeVariant.class), map -> {
                for (int i = 0; i < AnatidaeEntity.AnatidaeVariant.lenght(); i++) {
                    AnatidaeEntity.AnatidaeVariant currentVariant = AnatidaeEntity.AnatidaeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/anatidae/" + currentVariant.getFileName() + ".png"));
                }

            });

    public AnatidaeRenderer(EntityRendererProvider.Context context) {
        super(context, new AnatidaeModel<>(context.bakeLayer(AnatidaeModel.LAYER_LOCATION)), 0.25f);
        this.addLayer(new PlayerItemInHandLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(AnatidaeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(AnatidaeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(entity.getVariant().getSizeModifier(),
                entity.getVariant().getSizeModifier(),
                entity.getVariant().getSizeModifier());

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}