package com.kalyptien.caelumpedion.item;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.ModEntities;
import com.kalyptien.caelumpedion.item.custom.FeatherItem;
import com.kalyptien.caelumpedion.util.FeatherColor;
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

    // Color Feathers
    public static final DeferredItem<Item> LIGHT_GRAY_FEATHER = ITEMS.register("light_gray_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.LightGray));
    public static final DeferredItem<Item> GRAY_FEATHER = ITEMS.register("gray_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Gray));
    public static final DeferredItem<Item> BLACK_FEATHER = ITEMS.register("black_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Black));
    public static final DeferredItem<Item> BROWN_FEATHER = ITEMS.register("brown_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Brown));
    public static final DeferredItem<Item> RED_FEATHER = ITEMS.register("red_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Red));
    public static final DeferredItem<Item> ORANGE_FEATHER = ITEMS.register("orange_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Orange));
    public static final DeferredItem<Item> YELLOW_FEATHER = ITEMS.register("yellow_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Yellow));
    public static final DeferredItem<Item> LIME_FEATHER = ITEMS.register("lime_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Lime));
    public static final DeferredItem<Item> GREEN_FEATHER = ITEMS.register("green_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Green));
    public static final DeferredItem<Item> LIGHT_BLUE_FEATHER = ITEMS.register("light_blue_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.LightBlue));
    public static final DeferredItem<Item> CYAN_FEATHER = ITEMS.register("cyan_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Cyan));
    public static final DeferredItem<Item> BLUE_FEATHER = ITEMS.register("blue_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Blue));
    public static final DeferredItem<Item> PURPLE_FEATHER = ITEMS.register("purple_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Purple));
    public static final DeferredItem<Item> MAGENTA_FEATHER = ITEMS.register("magenta_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Magenta));
    public static final DeferredItem<Item> PINK_FEATHER = ITEMS.register("pink_feather",
            () -> new FeatherItem(new Item.Properties(), FeatherColor.Pink));

    // Special Feathers

    public static final DeferredItem<Item> GLITCH_FEATHER = ITEMS.register("glitch_feather",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.glitch_feather.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    // Spawn eggs

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

    public static final DeferredItem<Item> CORVIDAE_SPAWN_EGG = ITEMS.register("corvidae_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.CORVIDAE, 0x121212, 0x16111c,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.corvidae_spawn_egg.tooltip"));
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

    public static final DeferredItem<Item> PICIFORME_SPAWN_EGG = ITEMS.register("piciforme_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.PICIFORME, 0x77cf30, 0xd62822,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.piciforme_spawn_egg.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

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

    // =====

    public static final DeferredItem<Item> TROCHILIDAE_SPAWN_EGG = ITEMS.register("trochilidae_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.TROCHILIDAE, 0x34ab32, 0x4770c9,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.trochilidae_spawn_egg.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    // =====

    public static final DeferredItem<Item> COLUMBIFORME_SPAWN_EGG = ITEMS.register("columbiforme_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.COLUMBIFORME, 0x636363, 0xad7fa6,
                    new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.caelumpedion.columbiforme_spawn_egg.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
