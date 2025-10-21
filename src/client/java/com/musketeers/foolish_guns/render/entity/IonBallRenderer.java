package com.musketeers.foolish_guns.render.entity;

import com.musketeers.foolish_guns.entities.IonBallEntity;
import com.musketeers.foolish_guns.model.IonBallModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class IonBallRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<IonBallEntity, R> {
    //public static ModelLayerLocation BULLET_MODEL_LAYER = new ModelLayerLocation(id("model"),"main");
    //public static ResourceLocation BULLET_MODEL_TEXTURE = id("textures/item/bullet_texture.png");
    //public EntityModel<EntityRenderState> model;
    public IonBallRenderer(EntityRendererProvider.Context context){
        super(context, new IonBallModel());
    }
}
