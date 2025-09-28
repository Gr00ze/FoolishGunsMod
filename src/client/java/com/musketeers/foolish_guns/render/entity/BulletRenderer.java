package com.musketeers.foolish_guns.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.musketeers.foolish_guns.entities.Bullet;
import com.musketeers.foolish_guns.model.BulletModel;
import com.musketeers.foolish_guns.render.EntityRenderList;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;

public class BulletRenderer extends EntityRenderer<Bullet,EntityRenderState> {
    public static ModelLayerLocation BULLET_MODEL_LAYER = new ModelLayerLocation(id("model"),"main");
    public static ResourceLocation BULLET_MODEL_TEXTURE = id("textures/item/bullet_texture.png");

    public EntityModel<EntityRenderState> model;

    public BulletRenderer(EntityRendererProvider.Context context){
        super(context);
        model = new BulletModel(context.bakeLayer(BULLET_MODEL_LAYER));
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(entityRenderState, poseStack, multiBufferSource, i);
        poseStack.pushPose();
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(BULLET_MODEL_TEXTURE)), i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public @NotNull EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

}
