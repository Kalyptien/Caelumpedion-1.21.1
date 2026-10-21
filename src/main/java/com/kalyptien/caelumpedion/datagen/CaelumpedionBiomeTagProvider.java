package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.util.CaelumpedionTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CaelumpedionBiomeTagProvider extends BiomeTagsProvider {

    public CaelumpedionBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, CaelumpedionMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CaelumpedionTags.Biomes.IS_HOT)
                .add(Biomes.DESERT);

        tag(CaelumpedionTags.Biomes.IS_COLD)
                .add(Biomes.TAIGA)
                .add(Biomes.OLD_GROWTH_PINE_TAIGA)
                .add(Biomes.SNOWY_TAIGA)
                .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
                .add(Biomes.SNOWY_BEACH)
                .add(Biomes.SNOWY_PLAINS)
                .add(Biomes.SNOWY_SLOPES)
                .add(Biomes.FROZEN_RIVER)
                .add(Biomes.GROVE)
                .add(Biomes.ICE_SPIKES);

        tag(CaelumpedionTags.Biomes.IS_CAVE)
                .add(Biomes.LUSH_CAVES)
                .add(Biomes.DRIPSTONE_CAVES);

        tag(CaelumpedionTags.Biomes.IS_ASIAN)
                .add(Biomes.CHERRY_GROVE)
                .add(Biomes.BAMBOO_JUNGLE);
    }
}