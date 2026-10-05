package com.kalyptien.caelumpedion.event;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;
import java.util.Objects;

@EventBusSubscriber(value = Dist.CLIENT, modid = CaelumpedionMod.MOD_ID)
public class ModShaders {

    private static ShaderInstance iridescentFeatherShader;

    public static ShaderInstance getIridescentFeatherShader() {
        return Objects.requireNonNull(iridescentFeatherShader, "Attempted to call getIridescentFeatherShader before shaders have finished loading.");
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, "iridescent_feather"), DefaultVertexFormat.NEW_ENTITY), (shader) -> iridescentFeatherShader = shader);
    }
}
