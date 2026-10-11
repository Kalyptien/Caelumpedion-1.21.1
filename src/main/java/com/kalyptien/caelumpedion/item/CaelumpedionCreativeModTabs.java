package com.kalyptien.caelumpedion.item;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.block.CaelumpedionBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CaelumpedionCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CaelumpedionMod.MOD_ID);

    public static final Supplier<CreativeModeTab> CAELUMPEDION_ITEMS_TAB = CREATIVE_MODE_TAB.register("caelumpedion_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.WHEAT_SEEDS))
                    .title(Component.translatable("creativetab.caelumpedion.mod_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(CaelumpedionItems.PASSERIFORME_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.HIRUNDININAE_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.CORVIDAE_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.ANSERIFORME_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.ACCIPITRIFORME_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.PICIFORME_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.RAMPHASTIDAE_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.GRUIFORME_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.TROCHILIDAE_SPAWN_EGG.get());
                        output.accept(CaelumpedionItems.COLUMBIFORME_SPAWN_EGG.get());

                        output.accept(CaelumpedionItems.LIGHT_GRAY_FEATHER.get());
                        output.accept(CaelumpedionItems.GRAY_FEATHER.get());
                        output.accept(CaelumpedionItems.BLACK_FEATHER.get());
                        output.accept(CaelumpedionItems.BROWN_FEATHER.get());
                        output.accept(CaelumpedionItems.RED_FEATHER.get());
                        output.accept(CaelumpedionItems.ORANGE_FEATHER.get());
                        output.accept(CaelumpedionItems.YELLOW_FEATHER.get());
                        output.accept(CaelumpedionItems.LIME_FEATHER.get());
                        output.accept(CaelumpedionItems.GREEN_FEATHER.get());
                        output.accept(CaelumpedionItems.LIGHT_BLUE_FEATHER.get());
                        output.accept(CaelumpedionItems.CYAN_FEATHER.get());
                        output.accept(CaelumpedionItems.BLUE_FEATHER.get());
                        output.accept(CaelumpedionItems.PURPLE_FEATHER.get());
                        output.accept(CaelumpedionItems.MAGENTA_FEATHER.get());
                        output.accept(CaelumpedionItems.PINK_FEATHER.get());

                        output.accept(CaelumpedionBlocks.BIRD_FEEDER.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
