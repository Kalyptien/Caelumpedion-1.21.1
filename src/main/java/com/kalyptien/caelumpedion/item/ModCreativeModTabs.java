package com.kalyptien.caelumpedion.item;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CaelumpedionMod.MOD_ID);

    public static final Supplier<CreativeModeTab> CAELUMPEDION_ITEMS_TAB = CREATIVE_MODE_TAB.register("caelumpedion_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.WHEAT_SEEDS))
                    .title(Component.translatable("creativetab.caelumpedion.mod_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.PASSERIFORME_SPAWN_EGG.get());
                        output.accept(ModItems.HIRUNDININAE_SPAWN_EGG.get());
                        output.accept(ModItems.CORVIDAE_SPAWN_EGG.get());
                        output.accept(ModItems.ANSERIFORME_SPAWN_EGG.get());
                        output.accept(ModItems.ACCIPITRIFORME_SPAWN_EGG.get());
                        output.accept(ModItems.PICIFORME_SPAWN_EGG.get());
                        output.accept(ModItems.RAMPHASTIDAE_SPAWN_EGG.get());
                        output.accept(ModItems.GRUIFORME_SPAWN_EGG.get());
                        output.accept(ModItems.TROCHILIDAE_SPAWN_EGG.get());
                        output.accept(ModItems.COLUMBIFORME_SPAWN_EGG.get());

                        output.accept(ModItems.LIGHT_GRAY_FEATHER.get());
                        output.accept(ModItems.GRAY_FEATHER.get());
                        output.accept(ModItems.BLACK_FEATHER.get());
                        output.accept(ModItems.BROWN_FEATHER.get());
                        output.accept(ModItems.RED_FEATHER.get());
                        output.accept(ModItems.ORANGE_FEATHER.get());
                        output.accept(ModItems.YELLOW_FEATHER.get());
                        output.accept(ModItems.LIME_FEATHER.get());
                        output.accept(ModItems.GREEN_FEATHER.get());
                        output.accept(ModItems.LIGHT_BLUE_FEATHER.get());
                        output.accept(ModItems.CYAN_FEATHER.get());
                        output.accept(ModItems.BLUE_FEATHER.get());
                        output.accept(ModItems.PURPLE_FEATHER.get());
                        output.accept(ModItems.MAGENTA_FEATHER.get());
                        output.accept(ModItems.PINK_FEATHER.get());

                        output.accept(ModBlocks.BIRD_FEEDER.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
