package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.resources.ResourceLocation;

public class ModItemModelProvider  extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CaelumpedionMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        
        featherBuild(ModItems.LIGHT_GRAY_FEATHER.get());
        featherBuild(ModItems.GRAY_FEATHER.get());
        featherBuild(ModItems.BLACK_FEATHER.get());
        featherBuild(ModItems.BROWN_FEATHER.get());
        featherBuild(ModItems.RED_FEATHER.get());
        featherBuild(ModItems.ORANGE_FEATHER.get());
        featherBuild(ModItems.YELLOW_FEATHER.get());
        featherBuild(ModItems.LIME_FEATHER.get());
        featherBuild(ModItems.GREEN_FEATHER.get());
        featherBuild(ModItems.LIGHT_BLUE_FEATHER.get());
        featherBuild(ModItems.CYAN_FEATHER.get());
        featherBuild(ModItems.BLUE_FEATHER.get());
        featherBuild(ModItems.PURPLE_FEATHER.get());
        featherBuild(ModItems.MAGENTA_FEATHER.get());
        featherBuild(ModItems.PINK_FEATHER.get());

        basicItem(ModItems.GLITCH_FEATHER.get());

        withExistingParent(ModItems.PASSERIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.HIRUNDININAE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ANSERIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ACCIPITRIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.PICIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.RAMPHASTIDAE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GRUIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.COLUMBIFORME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CORVIDAE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TROCHILIDAE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder featherBuild(Item item) {
        return (ItemModelBuilder) ((ItemModelBuilder)((ItemModelBuilder)
                this.getBuilder(item.toString())).parent(
                new ModelFile.UncheckedModelFile("item/generated") {
                }))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath("minecraft", "item/feather"));
    }
}
