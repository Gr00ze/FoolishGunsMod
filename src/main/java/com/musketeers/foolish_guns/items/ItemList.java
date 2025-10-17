package com.musketeers.foolish_guns.items;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.registerItem;

public class ItemList {
    public static final PrototypeGunItem PROTOTYPE_GUN_ITEM = registerItem("prototype_gun_item", PrototypeGunItem::new, new Item.Properties().stacksTo(1).useCooldown(10).durability(10));
    public static final BulletItem BULLET_ITEM = registerItem("bullet_item", BulletItem::new, new Item.Properties());
    public static void initialize(){

    }
}
