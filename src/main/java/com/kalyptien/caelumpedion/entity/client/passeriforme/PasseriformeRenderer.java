package com.kalyptien.caelumpedion.entity.client.passeriforme;

import com.google.common.collect.Maps;
import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.PasseriformeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class PasseriformeRenderer extends MobRenderer<PasseriformeEntity, PasseriformeModel<PasseriformeEntity>> {

    private static final Map<PasseriformeEntity.PasseriformeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PasseriformeEntity.PasseriformeVariant.class), map -> {
                map.put(PasseriformeEntity.PasseriformeVariant.CardinalisCardinalis,
                        ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/cardinalis_cardinalis.png"));
                map.put(PasseriformeEntity.PasseriformeVariant.CyanistesCaeruleus,
                        ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/cyanistes_caeruleus.png"));
                map.put(PasseriformeEntity.PasseriformeVariant.CyanocittaCristata,
                        ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/cyanocitta_cristata.png"));
                map.put(PasseriformeEntity.PasseriformeVariant.ErithacusRubecula,
                        ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/erithacus_rubecula.png"));
                map.put(PasseriformeEntity.PasseriformeVariant.LophophanesCristatus,
                        ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/lophophanes_cristatus.png"));
                map.put(PasseriformeEntity.PasseriformeVariant.PasserDomesticus,
                        ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/passer_domesticus.png"));
                map.put(PasseriformeEntity.PasseriformeVariant.PeriparusAter,
                        ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/periparus_ater.png"));
                map.put(PasseriformeEntity.PasseriformeVariant.PhoenicurusOchruros,
                        ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "textures/entity/passeriforme/phoenicurus_ochruros.png"));

            });

    public PasseriformeRenderer(EntityRendererProvider.Context context) {
        super(context, new PasseriformeModel<>(context.bakeLayer(PasseriformeModel.LAYER_LOCATION)), 0.1f);
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