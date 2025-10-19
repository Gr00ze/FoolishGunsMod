package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.items.ItemGroups;
import com.musketeers.foolish_guns.items.ItemList;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class FoolishGunsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider((FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)-> new FabricRecipeProvider(output,registriesFuture) {
            @Override
            public @NotNull String getName() {
                return "GunsRecipeProvider";
            }

            @Override
            protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
                return new RecipeProvider(provider, recipeOutput) {
                    @Override
                    public void buildRecipes() {
                        HolderGetter<Item> itemGetter = provider.lookupOrThrow(Registries.ITEM);

                        ShapedRecipeBuilder.shaped(itemGetter,RecipeCategory.COMBAT, ItemList.TESLA_GUN_ITEM.asItem())
                                .pattern("IOR")
                                .pattern("I  ")
                                .pattern("I  ")
                                .define('I', Items.IRON_INGOT)
                                .define('O', Items.END_ROD)
                                .define('R', Items.REDSTONE)
                                .unlockedBy("has_end_rod", has(Items.END_ROD))
                                .save(recipeOutput, "prototype_gun_recipe");

                        ShapelessRecipeBuilder.shapeless(itemGetter, RecipeCategory.COMBAT, ItemList.TESLA_GUN_ITEM.asItem())
                                .requires(ItemList.TESLA_GUN_ITEM.asItem(), 1) // item rotto
                                .requires(Items.END_ROD, 1)            // materiale per ricarica
                                .unlockedBy("has_end_rod", has(Items.END_ROD))
                                .save(recipeOutput,  "prototype_gun_repair");
                    }
                };
            }
        });

        pack.addProvider((FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)-> new FabricLanguageProvider(output, registriesFuture) {
            @Override
            public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
                translationBuilder.add(ItemList.TESLA_GUN_ITEM.asItem(), "Ring Gun");
                translationBuilder.add(ItemGroups.CUSTOM_ITEM_GROUP_KEY, "Guns");
            }
        });
	}


    }
