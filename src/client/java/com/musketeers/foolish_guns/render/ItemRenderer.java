package com.musketeers.foolish_guns.render;

import com.musketeers.foolish_guns.items.ItemList;
import com.musketeers.foolish_guns.items.PrototypeGunItem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import static com.musketeers.foolish_guns.FoolishGuns.MOD_ID;

public class ItemRenderer {
     public static void init() {
         ItemList.PROTOTYPE_GUN_ITEM.createGeoRenderer(consumer -> new GunRenderer());
     }



}
