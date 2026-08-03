package com.kalyptien.caelumpedion.block.entity.renderer;

import com.kalyptien.caelumpedion.block.entity.BirdFeederBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class BirdFeederBlockEntityRenderer implements BlockEntityRenderer<BirdFeederBlockEntity> {
    public BirdFeederBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(BirdFeederBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        int limit = pBlockEntity.inventory.getSlots();

        for (int i = 0; i < limit; i++) {
            ItemStack stack = pBlockEntity.inventory.getStackInSlot(i);

            pPoseStack.pushPose();
            pPoseStack.translate(0.5f, 0.0f, 0.5f);
            pPoseStack.mulPose(Axis.YP.rotationDegrees((360/limit) * i));
            pPoseStack.mulPose(Axis.XP.rotation(45));
            pPoseStack.mulPose(Axis.ZP.rotation(25));
            pPoseStack.translate(0.0f, 0.37f, 0.0f);
            pPoseStack.scale(0.5f, 0.5f, 0.5f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                    pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
            pPoseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}