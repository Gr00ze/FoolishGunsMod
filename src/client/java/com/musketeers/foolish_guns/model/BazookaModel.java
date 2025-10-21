package com.musketeers.foolish_guns.model;

import com.musketeers.foolish_guns.items.BazookaItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import static com.musketeers.foolish_guns.FoolishGuns.MOD_ID;

public class BazookaModel extends GeoModel<BazookaItem> {
    private final ResourceLocation model = ResourceLocation.fromNamespaceAndPath(MOD_ID, "geckolib/models/bazooka_model.geo.json");
    private final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/item/bazooka_texture_futuristic.png");
    private final ResourceLocation animation = ResourceLocation.fromNamespaceAndPath(MOD_ID, "geckolib/animations/bazooka_model.animation.json");
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {return this.model;}

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(BazookaItem bazookaItem) {
        return this.animation;
    }

    /*@Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }

    @Override
    public double getTick(@Nullable Object o) {
        return 0;
    }*/
}
