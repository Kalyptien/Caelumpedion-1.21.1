package com.kalyptien.caelumpedion.item;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CaelumpedionMod.MOD_ID);

    // MISC

    public static final DeferredItem<Item> PASSERIFORME_SPAWN_EGG = ITEMS.register("passeriforme_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.PASSERIFORME, 0x402018, 0xebebeb,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.passeriforme_spawn_egg.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> HIRUNDININAE_SPAWN_EGG = ITEMS.register("hirundininae_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.HIRUNDININAE, 0x402018, 0x141414,
                    new Item.Properties()){
                        @Override
                        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                            tooltipComponents.add(Component.translatable("tooltip.caelumpedion.hirundininae_spawn_egg.tooltip"));
                            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                        }
                    });

    // =====

    public static final DeferredItem<Item> ANSERIFORME_SPAWN_EGG = ITEMS.register("anseriforme_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.ANSERIFORME, 0x525252, 0xebebeb,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.anseriforme_spawn_egg.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    // =====

    public static final DeferredItem<Item> ACCIPITRIFORME_SPAWN_EGG = ITEMS.register("accipitriforme_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.ACCIPITRIFORME, 0x402018, 0xe3af98,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.accipitriforme_spawn_egg.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    // =====

    public static final DeferredItem<Item> RAMPHASTIDAE_SPAWN_EGG = ITEMS.register("ramphastidae_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.RAMPHASTIDAE, 0x141414, 0xe67b25,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.ramphastidae_spawn_egg.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    // =====

    public static final DeferredItem<Item> GRUIFORME_SPAWN_EGG = ITEMS.register("gruiforme_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.GRUIFORME, 0xebebeb, 0xe3381e,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.gruiforme_spawn_egg.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
