package com.musketeers.foolish_guns.render;

import com.musketeers.foolish_guns.entities.EntityList;
import com.musketeers.foolish_guns.model.BulletModel;
import com.musketeers.foolish_guns.render.entity.BulletRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;


public class EntityRenderList {
    public static void initialize(){
        EntityRendererRegistry.register(EntityList.BULLET_ENTITY_TYPE, BulletRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BulletRenderer.BULLET_MODEL_LAYER, BulletModel::getTexturedModelData);
    }
}
