package com.kalyptien.caelumpedion.entity.client.piciforme;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.PiciformeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class PiciformeRenderer extends MobRenderer<PiciformeEntity, PiciformeModel<PiciformeEntity>> {

    private static final Map<PiciformeEntity.PiciformeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PiciformeEntity.PiciformeVariant.class), map -> {
                for (int i = 0; i < PiciformeEntity.PiciformeVariant.lenght(); i++) {
                    PiciformeEntity.PiciformeVariant currentVariant = PiciformeEntity.PiciformeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/piciforme/" + currentVariant.getFileName() + ".png"));
                }

            });

    public PiciformeRenderer(EntityRendererProvider.Context context) {
        super(context, new PiciformeModel<>(context.bakeLayer(PiciformeModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(PiciformeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(PiciformeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}