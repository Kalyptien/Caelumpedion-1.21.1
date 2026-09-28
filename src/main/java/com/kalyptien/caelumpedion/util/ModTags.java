package com.kalyptien.caelumpedion.util;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Blocks {
    }

    public static class Items {
        public static final TagKey<Item> FEATHERS = createTag("feathers");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, name));
        }
    }
}
