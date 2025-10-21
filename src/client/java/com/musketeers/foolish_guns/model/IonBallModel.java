package com.musketeers.foolish_guns.model;


import com.musketeers.foolish_guns.entities.IonBallEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;


// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class IonBallModel extends GeoModel<IonBallEntity> {
    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return id("geckolib/models/ion_ball_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return id("textures/item/ionball_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IonBallEntity ionBallEntity) {
        return id("geckolib/animations/ion_ball_model.animation.json");
    }
}