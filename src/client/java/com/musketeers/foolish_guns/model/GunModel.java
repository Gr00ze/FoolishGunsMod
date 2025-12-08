package com.musketeers.foolish_guns.model;

import com.musketeers.foolish_guns.items.TeslaGun;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import static com.musketeers.foolish_guns.FoolishGuns.MOD_ID;
import static com.musketeers.foolish_guns.utils.Season.getSeasonalMode;

public class GunModel extends GeoModel<TeslaGun> {
    private final ResourceLocation model = ResourceLocation.fromNamespaceAndPath(MOD_ID, "geckolib/models/tesla_gun_model.geo.json");
    private final ResourceLocation defaultTexture = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/item/tesla_gun.png");
    private final ResourceLocation christmasTexture = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/item/tesla_gun_christmas.png");
    private final ResourceLocation animation = ResourceLocation.fromNamespaceAndPath(MOD_ID, "geckolib/animations/tesla_gun_model.animation.json");
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {return this.model;}

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        switch (getSeasonalMode()) {
            case HALLOWEEN -> {
                return this.defaultTexture;
            }
            case CHRISTMAS -> {
                return this.christmasTexture;
            }
            default -> {
                //volume 0 - 1 - >1 distance
                //pitch 0.5 - 1 - > 1 faster sound
                return this.defaultTexture;
            }

        }

    }

    @Override
    public ResourceLocation getAnimationResource(TeslaGun animatable) {
        return this.animation;
    }
}

