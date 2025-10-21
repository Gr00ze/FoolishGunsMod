package com.musketeers.foolish_guns.render;

import com.musketeers.foolish_guns.entities.EntityList;
import com.musketeers.foolish_guns.render.entity.IonBallRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;


public class EntityRenderList {
    public static void initialize(){
        EntityRendererRegistry.register(EntityList.BULLET_ENTITY_TYPE, IonBallRenderer::new);
        //EntityModelLayerRegistry.registerModelLayer(BulletRenderer.BULLET_MODEL_LAYER, IonBallModel::);
    }
}
