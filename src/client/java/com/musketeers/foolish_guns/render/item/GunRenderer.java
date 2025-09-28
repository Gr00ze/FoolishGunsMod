package com.musketeers.foolish_guns.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.musketeers.foolish_guns.items.PrototypeGunItem;
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

public class GunRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<PrototypeGunItem> {
    public GunRenderer() {
        super(new GunModel());
    }
    @Override
    public void render(GeoRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource) {
        //super.render(renderState,poseStack, bufferSource);
        renderGecko(renderState,poseStack,bufferSource);
        /*
        RenderType renderType = RenderType.entityCutoutNoCull(texture);
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBuffer(bufferSource, renderType, false, renderState.getGeckolibData(DataTickets.HAS_GLINT));
        poseStack.pushPose();
        poseStack.scale(9,9,9);
        vertexConsumer.addVertex(0.5f,0.2f,0.5f).setColor(-1).setUv(0,0).setOverlay(13131231).setLight(313131).setNormal(0.5f,0.5f,0.5f);
        vertexConsumer.addVertex(0.2f,0.5f,0.5f).setColor(-1).setUv(0,0).setOverlay(13131231).setLight(313131).setNormal(0.5f,0.5f,0.5f);
        vertexConsumer.addVertex(0.2f,0.5f,0.5f).setColor(-1).setUv(0,0).setOverlay(13131231).setLight(313131).setNormal(0.5f,0.5f,0.5f);
        vertexConsumer.addVertex(0.5f,0.2f,0.5f).setColor(-1).setUv(0,0).setOverlay(13131231).setLight(313131).setNormal(0.5f,0.5f,0.5f);
        poseStack.popPose();
        */

    }

    public void renderGecko(GeoRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource){
        //super.render()
        if (renderState.getGeckolibData(DataTickets.ITEM_RENDER_PERSPECTIVE) == ItemDisplayContext.GUI) {
            renderInGui(renderState, poseStack, bufferSource);
        }
        else {
            RenderType renderType = getRenderType(renderState, getTextureLocation(renderState));
            //VertexConsumer buffer = renderType == null ? null : ItemRenderer.getFoilBuffer(bufferSource, renderType, false, renderState.getGeckolibData(DataTickets.HAS_GLINT));
            VertexConsumer buffer = renderType == null ? null : ItemRenderer.getFoilBuffer(bufferSource, RenderType.endGateway(), false, renderState.getGeckolibData(DataTickets.HAS_GLINT));
            poseStack.pushPose();
            //poseStack.scale(9,9,9);

            buffer.addVertex(poseStack.last().pose(), 0,0,0).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);
            buffer.addVertex(poseStack.last().pose(),2,0,2).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);
            buffer.addVertex(poseStack.last().pose(),0,0,2).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);
            buffer.addVertex(poseStack.last().pose(),2,0,0).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);

            //buffer.addVertex(poseStack.last().pose(),0,1,2).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);
            //buffer.addVertex(poseStack.last().pose(),2,1,2).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);

            poseStack.popPose();


            //defaultRender(renderState, poseStack, bufferSource, renderType, buffer);
            poseStack.pushPose();

            //to remove
            // poseStack.translate(new Vec3(1,1,1));
            // poseStack.scale(9,9,9);

            if (renderType == null)
                renderType = getRenderType(renderState, getTextureLocation(renderState));

            if (buffer == null && renderType != null)
                buffer = bufferSource.getBuffer(renderType);

            final GeoModel geoModel = getGeoModel();
            final BakedGeoModel model = geoModel.getBakedModel(geoModel.getModelResource(renderState));
            final int packedOverlay = renderState.getGeckolibData(DataTickets.PACKED_OVERLAY);
            final int packedLight = renderState.getGeckolibData(DataTickets.PACKED_LIGHT);
            final int renderColor = renderState.getGeckolibData(DataTickets.RENDER_COLOR);

            preRender(renderState, poseStack, model, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);
            adjustPositionForRender(renderState, poseStack, model, false);
            scaleModelForRender(renderState, 1, 1, poseStack, model, false);

            if (firePreRenderEvent(renderState, poseStack, model, bufferSource)) {
                preApplyRenderLayers(renderState, poseStack, model, renderType, bufferSource, buffer, packedLight, packedOverlay, renderColor);
                //
                //actuallyRender(renderState, poseStack, model, renderType, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);
                if (!false) {
                    ((T)renderState.getGeckolibData(DataTickets.ITEM)).getAnimatableInstanceCache().getManagerForId(renderState.getGeckolibData(DataTickets.ANIMATABLE_INSTANCE_ID)).setAnimatableData(DataTickets.ITEM_RENDER_PERSPECTIVE, renderState.getGeckolibData(DataTickets.ITEM_RENDER_PERSPECTIVE));
                    getGeoModel().handleAnimations(createAnimationState(renderState));
                }

                this.modelRenderTranslations = new Matrix4f(poseStack.last().pose());

                if (buffer != null){
                    if (renderType == null || buffer == null)
                        return;

                    for (GeoBone group : model.topLevelBones()) {
                        //renderRecursively(renderState, poseStack, group, renderType, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);
                        GeoBone bone = group;
                        if (bone.isTrackingMatrices()) {
                            Matrix4f poseState = new Matrix4f(poseStack.last().pose());

                            bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.modelRenderTranslations));
                            bone.setLocalSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.itemRenderTranslations));
                        }

                        //renderRecursively(renderState, poseStack, bone, renderType, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);
                        poseStack.pushPose();
                        RenderUtil.prepMatrixForBone(poseStack, bone);

                        if (!false) {
                            Pair<MutableObject<PoseStack.Pose>, PerBoneRender<GeoRenderState>> boneRenderTask = getPerBoneTasks(renderState).get(bone);

                            if (boneRenderTask != null)
                                boneRenderTask.left().setValue(poseStack.last().copy());
                        }

                        //renderCubesOfBone(renderState, bone, poseStack, buffer, packedLight, packedOverlay, renderColor);
                        if (bone.isHidden())
                            return;

                        for (GeoCube cube : bone.getCubes()) {
                            poseStack.pushPose();
                            //renderCube(renderState, cube, poseStack, buffer, packedLight, packedOverlay, renderColor);
                            RenderUtil.translateToPivotPoint(poseStack, cube);
                            RenderUtil.rotateMatrixAroundCube(poseStack, cube);
                            RenderUtil.translateAwayFromPivotPoint(poseStack, cube);

                            Matrix3f normalisedPoseState = poseStack.last().normal();
                            Matrix4f poseState = new Matrix4f(poseStack.last().pose());

                            for (GeoQuad quad : cube.quads()) {
                                if (quad == null)
                                    continue;

                                Vector3f normal = normalisedPoseState.transform(new Vector3f(quad.normal()));

                                RenderUtil.fixInvertedFlatCube(cube, normal);
                                //createVerticesOfQuad(renderState, quad, poseState, normal, buffer, packedOverlay, packedLight, renderColor);
                                for (GeoVertex vertex : quad.vertices()) {
                                    Vector3f position = vertex.position();
                                    Vector4f vector4f = poseState.transform(new Vector4f(position.x(), position.y(), position.z(), 1.0f));

                                    buffer.addVertex(vector4f.x(), vector4f.y(), vector4f.z(), renderColor, vertex.texU(),
                                            vertex.texV(), packedOverlay, packedLight, normal.x(), normal.y(), normal.z());
                                    //buffer.addVertex(0,0,0,0,0,0,0,0,0,0,0);
                                }
                                //createVerticesOfQuad(renderState, quad, poseState, normal, buffer, packedOverlay, packedLight, renderColor);
                            }
                            //renderCube(renderState, cube, poseStack, buffer, packedLight, packedOverlay, renderColor);
                            poseStack.popPose();
                        }
                        //renderCubesOfBone(renderState, bone, poseStack, buffer, packedLight, packedOverlay, renderColor);
                        renderChildBones(renderState, bone, poseStack, renderType, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);
                        poseStack.popPose();
                        //renderRecursively(renderState, poseStack, bone, renderType, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);



                        //renderRecursively(renderState, poseStack, group, renderType, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);
                    }

                }





                //actuallyRender(renderState, poseStack, model, renderType, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);




                //
                //applyRenderLayers(renderState, poseStack, model, renderType, bufferSource, buffer, packedLight, packedOverlay, renderColor);
                //postRender(renderState, poseStack, model, bufferSource, buffer, false, packedLight, packedOverlay, renderColor);
                //firePostRenderEvent(renderState, poseStack, model, bufferSource);
            }

            poseStack.popPose();

            //renderFinal(renderState, poseStack, model, bufferSource, buffer, packedLight, packedOverlay, renderColor);
            //doPostRenderCleanup();
        }




    }
}
