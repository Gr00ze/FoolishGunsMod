package com.musketeers.foolish_guns.entities;

import com.musketeers.foolish_guns.items.PrototypeGunItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.registerEntity;
import static com.musketeers.foolish_guns.utils.RegistrationUtils.registerItem;

public class EntityList {
    //public static final PrototypeGunItem PROTOTYPE_GUN_ITEM = registerItem("prototype_gun_item", PrototypeGunItem::new, new Item.Properties());
    public static final EntityType<Bullet> BULLET_ENTITY_TYPE = registerEntity("bullet_entity", Bullet::new);
    public static void initialize(){}
}
