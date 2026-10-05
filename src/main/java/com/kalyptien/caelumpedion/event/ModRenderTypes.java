package com.kalyptien.caelumpedion.event;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ModRenderTypes {

    private static final RenderStateShard.ShaderStateShard IRIDESCENT_FEATHER_SHADER =
            new RenderStateShard.ShaderStateShard(ModShaders::getIridescentFeatherShader);

    public static final Function<ResourceLocation, RenderType> IRIDESCENT_FEATHER = Util.memoize((resourceLocation) -> {
        return RenderType.create("iridescent_feather", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(IRIDESCENT_FEATHER_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                        .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setLightmapState(RenderStateShard.LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(false));
    });

    public static RenderType iridescenteFeather(ResourceLocation location) {
        return (RenderType)IRIDESCENT_FEATHER.apply(location);
    }
}
