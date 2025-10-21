package com.musketeers.foolish_guns.items;

import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.function.Consumer;

/**
 * Use this class for separated client and server projects
 * **/
public abstract class ExtendedGeoItem extends Item implements GeoItem {
    private Object provider = null;

    public ExtendedGeoItem(Properties properties) {
        super(properties);
    }
    /**
     * Call this method on client side to inject the render
     * **/
    public void injectRenderProvider(Object provider){
        this.provider = provider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void createGeoRenderer(Consumer consumer) {
        if (provider == null) throw new RuntimeException("Provider is null. Did you inject the rendering with injectRenderProvider?");
        consumer.accept(provider);
    }
}
