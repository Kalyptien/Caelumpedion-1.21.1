package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.block.ModBlocks;
import com.kalyptien.caelumpedion.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BIRD_FEEDER.get())
                .pattern("   ")
                .pattern("BSB")
                .pattern("BBB")
                .define('B', Items.BRICK)
                .define('S', Tags.Items.SEEDS)
                .unlockedBy("has_brick", has(Items.BRICK))
                .save(recipeOutput);

        // Feathers

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LIGHT_GRAY_FEATHER.get())
                .requires(Items.LIGHT_GRAY_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GRAY_FEATHER.get())
                .requires(Items.GRAY_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLACK_FEATHER.get())
                .requires(Items.BLACK_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BROWN_FEATHER.get())
                .requires(Items.BROWN_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RED_FEATHER.get())
                .requires(Items.RED_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ORANGE_FEATHER.get())
                .requires(Items.ORANGE_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.YELLOW_FEATHER.get())
                .requires(Items.YELLOW_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LIME_FEATHER.get())
                .requires(Items.LIME_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GREEN_FEATHER.get())
                .requires(Items.GREEN_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LIGHT_BLUE_FEATHER.get())
                .requires(Items.LIGHT_BLUE_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CYAN_FEATHER.get())
                .requires(Items.CYAN_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLUE_FEATHER.get())
                .requires(Items.BLUE_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PURPLE_FEATHER.get())
                .requires(Items.PURPLE_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MAGENTA_FEATHER.get())
                .requires(Items.MAGENTA_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PINK_FEATHER.get())
                .requires(Items.PINK_DYE)
                .requires(Items.FEATHER)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeOutput);
    }
}
