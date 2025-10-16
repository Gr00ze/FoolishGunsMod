package com.musketeers.foolish_guns.render.item;

import com.musketeers.foolish_guns.items.BulletItem;
import com.musketeers.foolish_guns.model.BazookaModel;
import com.musketeers.foolish_guns.model.BulletModel;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BazookaRenderer extends GeoItemRenderer<BulletItem> {
    public BazookaRenderer() {
        super(new BazookaModel());
    }
}
