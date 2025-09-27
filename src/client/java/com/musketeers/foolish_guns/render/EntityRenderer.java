package com.musketeers.foolish_guns.render;

import com.musketeers.foolish_guns.entities.EntityList;
import com.musketeers.foolish_guns.render.ItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;


public class EntityRenderer {
    public static void initialize(){
        EntityRendererRegistry.register(EntityList.BULLET_ENTITY_TYPE, new EntityRendererProvider<Entity>() {
            @Override
            public net.minecraft.client.renderer.entity.@NotNull EntityRenderer<Entity, ?> create(Context context) {
                return new net.minecraft.client.renderer.entity.EntityRenderer<Entity, EntityRenderState>(context) {
                    @Override
                    public @NotNull EntityRenderState createRenderState() {
                        return new EntityRenderState();
                    }
                };
            }
        });
        //EntityModelLayerRegistry.registerModelLayer();
    }
}
