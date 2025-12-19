package com.musketeers.foolish_guns.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.musketeers.foolish_guns.items.TeslaGun;
import com.musketeers.foolish_guns.model.GunModel;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;


public class TeslaGunRenderer<T extends TeslaGun, R extends GeoRenderState > extends GeoItemRenderer<T> {
    public TeslaGunRenderer() {
        super(new GunModel<>());
        this.addRenderLayer(new TeslaGunChargeRenderLayer<>(this));
    }
    @Override
    public void render(GeoRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource) {
        super.render(renderState, poseStack, bufferSource);
    }


}
