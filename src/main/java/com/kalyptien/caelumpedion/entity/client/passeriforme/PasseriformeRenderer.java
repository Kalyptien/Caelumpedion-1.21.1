package com.kalyptien.caelumpedion.entity.client.passeriforme;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.PasseriformeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class PasseriformeRenderer extends MobRenderer<PasseriformeEntity, PasseriformeModel<PasseriformeEntity>> {

    private static final Map<PasseriformeEntity.PasseriformeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PasseriformeEntity.PasseriformeVariant.class), map -> {
                for (int i = 0; i < PasseriformeEntity.PasseriformeVariant.lenght(); i++) {
                    PasseriformeEntity.PasseriformeVariant currentVariant = PasseriformeEntity.PasseriformeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/" + currentVariant.getFileName() + ".png"));
                }

            });

    public PasseriformeRenderer(EntityRendererProvider.Context context) {
        super(context, new PasseriformeModel<>(context.bakeLayer(PasseriformeModel.LAYER_LOCATION)), 0.25f);
        this.addLayer(new PlayerItemInHandLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(PasseriformeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(PasseriformeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}