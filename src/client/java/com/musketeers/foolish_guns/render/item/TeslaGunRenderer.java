package com.musketeers.foolish_guns.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.musketeers.foolish_guns.items.TeslaGun;
import com.musketeers.foolish_guns.model.GunModel;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import org.apache.commons.lang3.mutable.MutableObject;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.PerBoneRender;
import software.bernie.geckolib.util.RenderUtil;

import static com.musketeers.foolish_guns.FoolishGuns.MOD_ID;

public class TeslaGunRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<TeslaGun> {
    public TeslaGunRenderer() {
        super(new GunModel());
    }
    @Override
    public void render(GeoRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource) {
        super.render(renderState,poseStack, bufferSource);
    }


}
