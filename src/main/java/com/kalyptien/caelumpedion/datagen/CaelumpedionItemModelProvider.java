package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.item.CaelumpedionItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.resources.ResourceLocation;

public class CaelumpedionItemModelProvider extends ItemModelProvider {
    public CaelumpedionItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CaelumpedionMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        
        featherBuild(CaelumpedionItems.LIGHT_GRAY_FEATHER.get());
        featherBuild(CaelumpedionItems.GRAY_FEATHER.get());
        featherBuild(CaelumpedionItems.BLACK_FEATHER.get());
        featherBuild(CaelumpedionItems.BROWN_FEATHER.get());
        featherBuild(CaelumpedionItems.RED_FEATHER.get());
        featherBuild(CaelumpedionItems.ORANGE_FEATHER.get());
        featherBuild(CaelumpedionItems.YELLOW_FEATHER.get());
        featherBuild(CaelumpedionItems.LIME_FEATHER.get());
        featherBuild(CaelumpedionItems.GREEN_FEATHER.get());
        featherBuild(CaelumpedionItems.LIGHT_BLUE_FEATHER.get());
        featherBuild(CaelumpedionItems.CYAN_FEATHER.get());
        featherBuild(CaelumpedionItems.BLUE_FEATHER.get());
        featherBuild(CaelumpedionItems.PURPLE_FEATHER.get());
        featherBuild(CaelumpedionItems.MAGENTA_FEATHER.get());
        featherBuild(CaelumpedionItems.PINK_FEATHER.get());

        basicItem(CaelumpedionItems.GLITCH_FEATHER.get());

        withExistingParent(CaelumpedionItems.PASSERIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.HIRUNDININAE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.ANSERIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.ACCIPITRIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.PICIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.RAMPHASTIDAE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.GRUIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.COLUMBIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.CORVIDAE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(CaelumpedionItems.TROCHILIDAE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder featherBuild(Item item) {
        return (ItemModelBuilder) ((ItemModelBuilder)((ItemModelBuilder)
                this.getBuilder(item.toString())).parent(
                new ModelFile.UncheckedModelFile("item/generated") {
                }))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath("minecraft", "item/feather"));
    }
}
