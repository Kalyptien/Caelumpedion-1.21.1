package com.kalyptien.caelumpedion.entity.client.anseriforme;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.AnseriformeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class AnseriformeRenderer extends MobRenderer<AnseriformeEntity, AnseriformeModel<AnseriformeEntity>> {

    private static final Map<AnseriformeEntity.AnseriformeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(AnseriformeEntity.AnseriformeVariant.class), map -> {
                for (int i = 0; i < AnseriformeEntity.AnseriformeVariant.lenght(); i++) {
                    AnseriformeEntity.AnseriformeVariant currentVariant = AnseriformeEntity.AnseriformeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/anseriforme/" + currentVariant.getFileName() + ".png"));
                }

            });

    public AnseriformeRenderer(EntityRendererProvider.Context context) {
        super(context, new AnseriformeModel<>(context.bakeLayer(AnseriformeModel.LAYER_LOCATION)), 0.25f);
        this.addLayer(new PlayerItemInHandLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(AnseriformeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(AnseriformeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(entity.getVariant().getSizeModifier(),
                entity.getVariant().getSizeModifier(),
                entity.getVariant().getSizeModifier());

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}