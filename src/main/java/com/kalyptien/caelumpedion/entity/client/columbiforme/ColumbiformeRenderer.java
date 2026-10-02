package com.kalyptien.caelumpedion.entity.client.columbiforme;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.bird.columbiforme.ColumbiformeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ColumbiformeRenderer extends MobRenderer<ColumbiformeEntity, ColumbiformeModel<ColumbiformeEntity>> {

    private static final Map<ColumbiformeEntity.ColumbiformeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ColumbiformeEntity.ColumbiformeVariant.class), map -> {
                for (int i = 0; i < ColumbiformeEntity.ColumbiformeVariant.lenght(); i++) {
                    ColumbiformeEntity.ColumbiformeVariant currentVariant = ColumbiformeEntity.ColumbiformeVariant.byId(i);
                    map.put(currentVariant,
                            ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/columbiforme/" + currentVariant.getFileName() + ".png"));
                }

            });

    public ColumbiformeRenderer(EntityRendererProvider.Context context) {
        super(context, new ColumbiformeModel<>(context.bakeLayer(ColumbiformeModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(ColumbiformeEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(ColumbiformeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(entity.getVariant().getSize(),
                entity.getVariant().getSize(),
                entity.getVariant().getSize());

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}