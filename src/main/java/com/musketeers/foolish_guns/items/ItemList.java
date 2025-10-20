package com.musketeers.foolish_guns.items;

import net.minecraft.world.item.Item;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.*;

public class ItemList {
    public static final TeslaGun TESLA_GUN_ITEM = registerItem("tesla_gun_item", TeslaGun::new, TeslaGun.getItemProperties());
    public static final BulletItem BULLET_ITEM = registerItem("bullet_item", BulletItem::new, new Item.Properties());

    public static void initialize(){}
}
