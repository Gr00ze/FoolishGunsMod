package com.musketeers.foolish_guns.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.musketeers.foolish_guns.items.TeslaGun;
import com.musketeers.foolish_guns.model.GunModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;


public class TeslaGunRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<TeslaGun> {
    public TeslaGunRenderer() {
        super(new GunModel());
    }
    @Override
    public void render(GeoRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource) {
        super.render(renderState, poseStack, bufferSource);
    }


}
