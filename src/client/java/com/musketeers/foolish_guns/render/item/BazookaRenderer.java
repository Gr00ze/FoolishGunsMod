package com.musketeers.foolish_guns.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.musketeers.foolish_guns.items.BazookaItem;
import com.musketeers.foolish_guns.model.BazookaModel;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class BazookaRenderer extends GeoItemRenderer<BazookaItem> {
    public BazookaRenderer() {
        super(new BazookaModel());
    }

    @Override
    public void render(GeoRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource) {
        super.render(renderState, poseStack, bufferSource);
    }
}
