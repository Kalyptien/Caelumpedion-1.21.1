package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.item.ModItems;
import com.kalyptien.caelumpedion.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CaelumpedionMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.FEATHERS)
                .add(ModItems.LIGHT_GRAY_FEATHER.get())
                .add(ModItems.GRAY_FEATHER.get())
                .add(ModItems.BLACK_FEATHER.get())
                .add(ModItems.BROWN_FEATHER.get())
                .add(ModItems.RED_FEATHER.get())
                .add(ModItems.ORANGE_FEATHER.get())
                .add(ModItems.YELLOW_FEATHER.get())
                .add(ModItems.LIME_FEATHER.get())
                .add(ModItems.GREEN_FEATHER.get())
                .add(ModItems.LIGHT_BLUE_FEATHER.get())
                .add(ModItems.CYAN_FEATHER.get())
                .add(ModItems.BLUE_FEATHER.get())
                .add(ModItems.PURPLE_FEATHER.get())
                .add(ModItems.MAGENTA_FEATHER.get())
                .add(ModItems.PINK_FEATHER.get())
                .add(ModItems.GLITCH_FEATHER.get());
    }
}