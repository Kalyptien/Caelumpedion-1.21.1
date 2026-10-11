package com.kalyptien.caelumpedion.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class CaelumpedionRenderTypes {

    private static final RenderStateShard.ShaderStateShard IRIDESCENT_FEATHER_SHADER =
            new RenderStateShard.ShaderStateShard(CaelumpedionShaders::getIridescentFeatherShader);

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
