package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.block.CaelumpedionBlocks;
import com.kalyptien.caelumpedion.item.CaelumpedionItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class CaelumpedionRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public CaelumpedionRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CaelumpedionBlocks.BIRD_FEEDER.get())
                .pattern("   ")
                .pattern("BSB")
                .pattern("BBB")
                .define('B', Items.BRICK)
                .define('S', Tags.Items.SEEDS)
                .unlockedBy("has_brick", has(Items.BRICK))
                .save(recipeOutput);

        // Feathers

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.LIGHT_GRAY_FEATHER.get())
                .requires(Items.LIGHT_GRAY_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.GRAY_FEATHER.get())
                .requires(Items.GRAY_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.BLACK_FEATHER.get())
                .requires(Items.BLACK_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.BROWN_FEATHER.get())
                .requires(Items.BROWN_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.RED_FEATHER.get())
                .requires(Items.RED_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.ORANGE_FEATHER.get())
                .requires(Items.ORANGE_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.YELLOW_FEATHER.get())
                .requires(Items.YELLOW_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.LIME_FEATHER.get())
                .requires(Items.LIME_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.GREEN_FEATHER.get())
                .requires(Items.GREEN_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.LIGHT_BLUE_FEATHER.get())
                .requires(Items.LIGHT_BLUE_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.CYAN_FEATHER.get())
                .requires(Items.CYAN_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.BLUE_FEATHER.get())
                .requires(Items.BLUE_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.PURPLE_FEATHER.get())
                .requires(Items.PURPLE_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.MAGENTA_FEATHER.get())
                .requires(Items.MAGENTA_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CaelumpedionItems.PINK_FEATHER.get())
                .requires(Items.PINK_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);
    }
}
