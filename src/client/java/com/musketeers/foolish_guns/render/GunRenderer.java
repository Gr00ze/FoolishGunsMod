package com.musketeers.foolish_guns.render;

import com.musketeers.foolish_guns.items.PrototypeGunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GunRenderer extends GeoItemRenderer<PrototypeGunItem> {
    public GunRenderer() {
        super(new GunModel());
    }


}
