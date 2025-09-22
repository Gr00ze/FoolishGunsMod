package com.musketeers.foolish_guns.utils;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Function;

import static com.musketeers.foolish_guns.FoolishGuns.MOD_ID;

public class RegistrationUtils {

    /**
     * @param itemName the name of the item example IRON_SWORD
     * @param itemFactory the method reference of the constructor of the class Item::new, CustomItem::new
     * @param itemProperties The properties of the item.
     * @return The item itself
     * @implNote registerItem('sword', SwordItem::new, new Item.Properties() )
     * **/
    public static Item registerItem(String itemName, Function<Item.Properties, Item> itemFactory, Item.Properties itemProperties){
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID,itemName);
        ResourceKey<Item> resourceKey = ResourceKey.create(Registries.ITEM, resourceLocation);

        itemProperties.setId(resourceKey);
        return Registry.register(BuiltInRegistries.ITEM, resourceKey, itemFactory.apply(itemProperties));
    }

}
