package com.kalyptien.caelumpedion.entity.client.ramphastidae;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.piciforme.RamphastidaeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class RamphastidaeRenderer extends MobRenderer<RamphastidaeEntity, RamphastidaeModel<RamphastidaeEntity>> {

    private static final Map<RamphastidaeEntity.RamphastidaeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(RamphastidaeEntity.RamphastidaeVariant.class), map -> {
                for (int i = 0; i < RamphastidaeEntity.RamphastidaeVariant.lenght(); i++) {
                    RamphastidaeEntity.RamphastidaeVariant currentVariant = RamphastidaeEntity.RamphastidaeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/piciforme/ramphastidae/" + currentVariant.getFileName() + ".png"));
                }

            });

    public RamphastidaeRenderer(EntityRendererProvider.Context context) {
        super(context, new RamphastidaeModel<>(context.bakeLayer(RamphastidaeModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(RamphastidaeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(RamphastidaeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1,1,1);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}