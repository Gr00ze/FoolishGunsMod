package com.musketeers.foolish_guns.entities;

import com.musketeers.foolish_guns.items.PrototypeGunItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.registerEntity;
import static com.musketeers.foolish_guns.utils.RegistrationUtils.registerItem;

public class EntityList {
    public static final EntityType<Bullet> BULLET_ENTITY_TYPE =
            registerEntity(
                    "bullet_entity",
                    EntityType.Builder.of(
                            Bullet::new,
                            MobCategory.MISC
                    )
            );
    public static void initialize(){}
}
