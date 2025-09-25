package com.musketeers.foolish_guns.render;

import com.musketeers.foolish_guns.items.ItemList;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ItemRenderer {
     public static void init() {
         ItemList.PROTOTYPE_GUN_ITEM.injectRenderProvider(new GeoRenderProvider() {
             private GunRenderer renderer;

             @Override
             public GeoItemRenderer<?> getGeoItemRenderer() {
                 if (renderer == null) renderer = new GunRenderer();
                 return renderer;
             }
         });
        /*
         ClientTickEvents.END_CLIENT_TICK.register(client -> {
             if (client.player == null) return;

             if (client.options.keyAttack.isDown()) {
                 if (client.player.getMainHandItem().getItem() instanceof PrototypeGunItem gun) {
                     gun.shoot(client.level, client.player, client.player.getMainHandItem());
                 }
             }

         });
        */

     }



}
