package com.musketeers.foolish_guns.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.*;

public class ItemList {
    public static final PrototypeGunItem PROTOTYPE_GUN_ITEM = registerItem("prototype_gun_item", PrototypeGunItem::new, new Item.Properties().stacksTo(1).useCooldown(0.5F).durability(10));
    public static final BulletItem BULLET_ITEM = registerItem("bullet_item", BulletItem::new, new Item.Properties());

    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id("guns_item_group"));
    public static final CreativeModeTab CUSTOM_ITEM_GROUP =
            registerCreativeModeTab(CUSTOM_ITEM_GROUP_KEY, FabricItemGroup.builder()
            .icon(() -> new ItemStack(PROTOTYPE_GUN_ITEM))
            .title(Component.translatable("itemGroup.guns"))
            .build());


    public static void initialize(){
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.accept(PROTOTYPE_GUN_ITEM.asItem());
        });
    }
}
