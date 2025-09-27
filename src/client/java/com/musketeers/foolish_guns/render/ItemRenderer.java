package com.musketeers.foolish_guns.render;

import com.musketeers.foolish_guns.items.ItemList;
import com.musketeers.foolish_guns.render.item.GunRenderer;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Supplier;

public class ItemRenderer {
     public static GeoRenderProvider getItemRenderProvider(Supplier<GeoItemRenderer<?>> factory){
         return new GeoRenderProvider() {
             private GeoItemRenderer<?> renderer;

             @Override
             public GeoItemRenderer<?> getGeoItemRenderer() {
                 if (renderer == null) renderer = factory.get();
                 return renderer;
             }
         };
     }
     public static void init() {
         ItemList.PROTOTYPE_GUN_ITEM.injectRenderProvider(getItemRenderProvider(GunRenderer::new));

     }



}
