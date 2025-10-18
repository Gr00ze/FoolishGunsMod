package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.items.ItemList;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;

public class FoolishGunsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(MyModelProvider::new);
        pack.addProvider(MyRecipeProvider::new);
        pack.addProvider(MyTranslationProvider::new);
	}

    class MyModelProvider extends FabricModelProvider {
        public MyModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerators) {
            //itemModelGenerators.declareCustomModelItem(ItemList.PROTOTYPE_GUN_ITEM);
        }
    }

    class MyRecipeProvider extends FabricRecipeProvider {
        public MyRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new RecipeProvider(provider, recipeOutput) {
                @Override
                public void buildRecipes() {
                    HolderGetter<Item> itemGetter = provider.lookupOrThrow(Registries.ITEM);

                    ShapedRecipeBuilder.shaped(itemGetter,RecipeCategory.COMBAT, ItemList.PROTOTYPE_GUN_ITEM.asItem())
                            .pattern("IOR")
                            .pattern("I  ")
                            .pattern("I  ")
                            .define('I', Items.IRON_INGOT)
                            .define('O', Items.END_ROD)
                            .define('R', Items.REDSTONE)
                            .unlockedBy("has_end_rod", has(Items.END_ROD))
                            .save(recipeOutput, "prototype_gun_recipe");

                    ShapelessRecipeBuilder.shapeless(itemGetter, RecipeCategory.COMBAT, ItemList.PROTOTYPE_GUN_ITEM.asItem())
                            .requires(ItemList.PROTOTYPE_GUN_ITEM.asItem(), 1) // item rotto
                            .requires(Items.END_ROD, 1)            // materiale per ricarica
                            .unlockedBy("has_end_rod", has(Items.END_ROD))
                            .save(recipeOutput,  "prototype_gun_repair");
                }
            };
        }

        @Override
        public String getName() {
            return "";
        }
    }
        class MyTranslationProvider extends FabricLanguageProvider {
            protected MyTranslationProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
                super(dataOutput, registryLookup);
            }

            @Override
            public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
                translationBuilder.add(ItemList.PROTOTYPE_GUN_ITEM.asItem(), "Ring Gun");
            }
        }
    }
