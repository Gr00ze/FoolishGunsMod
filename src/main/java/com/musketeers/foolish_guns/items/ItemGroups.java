package com.musketeers.foolish_guns.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;
import static com.musketeers.foolish_guns.utils.RegistrationUtils.registerCreativeModeTab;

public class ItemGroups {
    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id("guns_item_group"));
    public static final CreativeModeTab CUSTOM_ITEM_GROUP =
            registerCreativeModeTab(CUSTOM_ITEM_GROUP_KEY, FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ItemList.TESLA_GUN_ITEM))
                    .title(Component.translatable("itemGroup.guns"))
                    .build());


    public static void initialize(){
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.accept(ItemList.TESLA_GUN_ITEM.asItem());
            itemGroup.accept(ItemList.BAZOOKA_ITEM.asItem());
        });
    }
}
