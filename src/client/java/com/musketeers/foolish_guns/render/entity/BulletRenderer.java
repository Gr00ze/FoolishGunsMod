package com.musketeers.foolish_guns.render.entity;

import com.musketeers.foolish_guns.entities.Bullet;
import com.musketeers.foolish_guns.render.EntityRenderList;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.jetbrains.annotations.NotNull;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;

public class BulletRenderer extends EntityRenderer<Bullet,EntityRenderState> {
    public static ModelLayerLocation BULLET_MODEL_LAYER = new ModelLayerLocation(id("model"),"layer_0");
    public static EntityModel<EntityRenderState> model;

    public BulletRenderer(EntityRendererProvider.Context context){
        super(context);

    }

    @Override
    public @NotNull EntityRenderState createRenderState() {
        return new EntityRenderState();
    }




}
