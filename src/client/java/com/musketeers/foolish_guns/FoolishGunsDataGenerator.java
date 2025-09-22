package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.items.ItemList;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;

public class FoolishGunsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

        fabricDataGenerator.createPack().addProvider(MyModelProvider::new);
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
            itemModelGenerators.declareCustomModelItem(ItemList.PROTOTYPE_GUN_ITEM);
        }
    }
}
