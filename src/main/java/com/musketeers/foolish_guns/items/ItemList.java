package com.musketeers.foolish_guns.items;

import static  com.musketeers.foolish_guns.utils.RegistrationUtils.registerItem;
import net.minecraft.world.item.Item;

public class ItemList {
    public static final Item PROTOTYPE_GUN_ITEM = registerItem("prototype_gun_item", Item::new, new Item.Properties());
    public static void onInit(){

    }
}
