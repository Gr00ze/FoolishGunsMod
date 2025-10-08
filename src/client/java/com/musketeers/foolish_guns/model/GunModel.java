package com.musketeers.foolish_guns.model;

import com.musketeers.foolish_guns.items.PrototypeGunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import static com.musketeers.foolish_guns.FoolishGuns.MOD_ID;

public class GunModel extends GeoModel<PrototypeGunItem> {
    private final ResourceLocation model = ResourceLocation.fromNamespaceAndPath(MOD_ID, "geckolib/models/gun_model.geo.json");
    private final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/item/gun.png");
    private final ResourceLocation animation = ResourceLocation.fromNamespaceAndPath(MOD_ID, "geckolib/animations/gun_model.animation.json");
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {return this.model;}

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(PrototypeGunItem animatable) {
        return this.animation;
    }
}

