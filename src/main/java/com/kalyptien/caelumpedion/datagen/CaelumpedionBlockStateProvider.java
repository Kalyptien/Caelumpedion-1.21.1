package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.block.CaelumpedionBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class CaelumpedionBlockStateProvider extends BlockStateProvider {
    public CaelumpedionBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CaelumpedionMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockItem(CaelumpedionBlocks.BIRD_FEEDER);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("caelumpedion:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("caelumpedion:block/" + deferredBlock.getId().getPath() + appendix));
    }
}
