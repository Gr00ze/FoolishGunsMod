package com.musketeers.foolish_guns.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.musketeers.foolish_guns.items.TeslaGun;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.Optional;


import static com.musketeers.foolish_guns.items.TeslaGun.GLOW_EFFECT;


public class TeslaGunChargeRenderLayer<T extends TeslaGun> extends GeoRenderLayer<T, GeoItemRenderer.RenderData, GeoRenderState> {
    public TeslaGunChargeRenderLayer(GeoRenderer<T, GeoItemRenderer.RenderData, GeoRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void render(GeoRenderState renderState, PoseStack poseStack, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, int renderColor) {
        Optional<GeoBone> optionalCoils = model.getBone("coils");

        if (optionalCoils.isEmpty())
            return;
        //renderState
        GeoBone coils = optionalCoils.get();
        // Riattiva SOLO per questo layer

        //ResourceLocation texture = getTextureResource(renderState);

        RenderType customRenderType = RenderType.lightning();

        Float glow = renderState.getOrDefaultGeckolibData(TeslaGun.GLOW_EFFECT, 0f);
        if (glow == null)return;
        //FoolishGuns.LOGGER.debug("Loading {}", glow);
        int alpha = (int)(glow * 255);
        int color = (alpha << 24) | 0x5555FF;

        for(GeoBone coil: coils.getChildBones()){
            poseStack.pushPose();
            poseStack.scale(1.1f, 1.1f, 1.2f);
            poseStack.translate(0F,-0.03F,0F);
            this.getRenderer().renderRecursively(
                    renderState,
                    poseStack,
                    coil,
                    customRenderType,
                    bufferSource,
                    bufferSource.getBuffer(customRenderType),
                    false,
                    packedLight,
                    packedOverlay,
                    color
            );

            poseStack.popPose();
        }

    }

    @Override
    public void addRenderData(T animatable, GeoItemRenderer.RenderData relatedObject, GeoRenderState renderState) {
        ItemStack stack = relatedObject.itemStack();

        if (stack == null) return;

        long id = GeoItem.getId(stack);
        AnimatableManager<T> manager = animatable.getAnimatableInstanceCache().getManagerForId(id);

        if (manager == null)return;

        Float targetGlow = manager.getAnimatableData(GLOW_EFFECT);

        if (targetGlow == null) return;

        renderState.addGeckolibData(GLOW_EFFECT, targetGlow);
    }

}
