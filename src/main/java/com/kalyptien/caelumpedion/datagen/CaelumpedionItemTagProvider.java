package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.item.CaelumpedionItems;
import com.kalyptien.caelumpedion.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class CaelumpedionItemTagProvider extends ItemTagsProvider {

    public CaelumpedionItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                       CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CaelumpedionMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.FEATHERS)
                .add(CaelumpedionItems.LIGHT_GRAY_FEATHER.get())
                .add(CaelumpedionItems.GRAY_FEATHER.get())
                .add(CaelumpedionItems.BLACK_FEATHER.get())
                .add(CaelumpedionItems.BROWN_FEATHER.get())
                .add(CaelumpedionItems.RED_FEATHER.get())
                .add(CaelumpedionItems.ORANGE_FEATHER.get())
                .add(CaelumpedionItems.YELLOW_FEATHER.get())
                .add(CaelumpedionItems.LIME_FEATHER.get())
                .add(CaelumpedionItems.GREEN_FEATHER.get())
                .add(CaelumpedionItems.LIGHT_BLUE_FEATHER.get())
                .add(CaelumpedionItems.CYAN_FEATHER.get())
                .add(CaelumpedionItems.BLUE_FEATHER.get())
                .add(CaelumpedionItems.PURPLE_FEATHER.get())
                .add(CaelumpedionItems.MAGENTA_FEATHER.get())
                .add(CaelumpedionItems.PINK_FEATHER.get())
                .add(CaelumpedionItems.GLITCH_FEATHER.get());
    }
}