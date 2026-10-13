package com.kalyptien.caelumpedion.util;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public class CaelumpedionTags {

    public static class Biomes {
        public static final TagKey<Biome> IS_COLD = createTag("is_cold");
        public static final TagKey<Biome> IS_HOT = createTag("is_hot");
        public static final TagKey<Biome> IS_CAVE = createTag("is_cave");

        public static final TagKey<Biome> IS_ASIAN = createTag("is_asian");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, name));
        }
    }

    public static class Blocks {
    }

    public static class Items {
        public static final TagKey<Item> FEATHERS = createTag("feathers");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, name));
        }
    }
}
