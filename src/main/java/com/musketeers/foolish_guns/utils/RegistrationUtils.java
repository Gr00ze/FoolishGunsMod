package com.musketeers.foolish_guns.utils;

import com.musketeers.foolish_guns.entities.Bullet;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
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
    public static <I extends Item> I registerItem(String itemName, Function<Item.Properties, I> itemFactory, Item.Properties itemProperties){
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID,itemName);
        ResourceKey<Item> resourceKey = ResourceKey.create(Registries.ITEM, resourceLocation);

        itemProperties.setId(resourceKey);
        return Registry.register(BuiltInRegistries.ITEM, resourceKey, itemFactory.apply(itemProperties));
    }
    public static <E extends EntityType<? extends Entity>> E registerEntity(String entityName, EntityType.EntityFactory test){
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID,entityName);
        ResourceKey<EntityType<?>> resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, resourceLocation);
        EntityType<?> entityType = EntityType.Builder.createNothing(MobCategory.MISC)
                .sized(0.5f,0.5f)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(resourceKey);
        return (E)Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, entityType);
    }
}
