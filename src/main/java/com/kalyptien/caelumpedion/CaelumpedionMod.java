package com.kalyptien.caelumpedion;

import com.kalyptien.caelumpedion.block.CaelumpedionBlocks;
import com.kalyptien.caelumpedion.block.entity.CaelumpedionBlockEntities;
import com.kalyptien.caelumpedion.block.entity.renderer.BirdFeederBlockEntityRenderer;
import com.kalyptien.caelumpedion.entity.CaelumpedionEntities;
import com.kalyptien.caelumpedion.entity.client.accipitridae.AccipitridaeRenderer;
import com.kalyptien.caelumpedion.entity.client.anseriforme.AnseriformeRenderer;
import com.kalyptien.caelumpedion.entity.client.columbiforme.ColumbiformeRenderer;
import com.kalyptien.caelumpedion.entity.client.corvidae.CorvidaeRenderer;
import com.kalyptien.caelumpedion.entity.client.gruiforme.GruiformeRenderer;
import com.kalyptien.caelumpedion.entity.client.hirundininae.HirundininaeRenderer;
import com.kalyptien.caelumpedion.entity.client.passeriforme.PasseriformeRenderer;
import com.kalyptien.caelumpedion.entity.client.ramphastidae.RamphastidaeRenderer;
import com.kalyptien.caelumpedion.entity.client.trochilidae.TrochilidaeRenderer;
import com.kalyptien.caelumpedion.item.CaelumpedionCreativeModTabs;
import com.kalyptien.caelumpedion.item.CaelumpedionItems;
import com.kalyptien.caelumpedion.screen.CaelumpedionMenuTypes;
import com.kalyptien.caelumpedion.screen.custom.BirdFeederScreen;
import com.kalyptien.caelumpedion.util.InitializationHandler;
import com.kalyptien.caelumpedion.worldgen.CaelumpedionDensityFunctionTypes;
import com.kalyptien.caelumpedion.worldgen.CaelumpedionDensityFunctions;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CaelumpedionMod.MOD_ID)
public class CaelumpedionMod {
    public static final String MOD_ID = "caelumpedion";

    public CaelumpedionMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        CaelumpedionDensityFunctionTypes.register(modEventBus);

        CaelumpedionCreativeModTabs.register(modEventBus);

        CaelumpedionItems.register(modEventBus);
        CaelumpedionBlocks.register(modEventBus);
        CaelumpedionBlockEntities.register(modEventBus);

        CaelumpedionMenuTypes.register(modEventBus);

        CaelumpedionEntities.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, InitializationHandler::onServerAboutToStart);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(Items.WHEAT_SEEDS);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(CaelumpedionEntities.PASSERIFORME.get(), PasseriformeRenderer::new);
            EntityRenderers.register(CaelumpedionEntities.HIRUNDININAE.get(), HirundininaeRenderer::new);
            EntityRenderers.register(CaelumpedionEntities.CORVIDAE.get(), CorvidaeRenderer::new);
            EntityRenderers.register(CaelumpedionEntities.ANSERIFORME.get(), AnseriformeRenderer::new);
            EntityRenderers.register(CaelumpedionEntities.ACCIPITRIFORME.get(), AccipitridaeRenderer::new);
            EntityRenderers.register(CaelumpedionEntities.RAMPHASTIDAE.get(), RamphastidaeRenderer::new);
            EntityRenderers.register(CaelumpedionEntities.GRUIFORME.get(), GruiformeRenderer::new);
            EntityRenderers.register(CaelumpedionEntities.TROCHILIDAE.get(), TrochilidaeRenderer::new);
            EntityRenderers.register(CaelumpedionEntities.COLUMBIFORME.get(), ColumbiformeRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(CaelumpedionMenuTypes.BIRD_FEEDER_MENU.get(), BirdFeederScreen::new);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(CaelumpedionBlockEntities.BIRD_FEEDER_BE.get(), BirdFeederBlockEntityRenderer::new);
        }
    }
}
