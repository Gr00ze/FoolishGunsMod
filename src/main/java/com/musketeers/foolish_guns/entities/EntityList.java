package com.musketeers.foolish_guns.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.registerEntity;

public class EntityList {
    //BUILDERS
    public static final EntityType.Builder<IonBallEntity> BULLET_ENTITY_TYPE_BUILDER = EntityType.Builder.of(IonBallEntity::new, MobCategory.MISC);
    //TYPES
    public static final EntityType<IonBallEntity> BULLET_ENTITY_TYPE = registerEntity("bullet_entity",BULLET_ENTITY_TYPE_BUILDER.sized(2,2));
    public static void initialize(){}
}
