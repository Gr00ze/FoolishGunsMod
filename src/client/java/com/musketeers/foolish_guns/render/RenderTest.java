package com.musketeers.foolish_guns.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.nio.ByteBuffer;

public class RenderTest {
    private static volatile Vec3 lineStart = null;
    private static volatile Vec3 lineEnd = null;
    private static volatile boolean shouldDraw = false;

    private static int i = 0;
    public static void test(){
        WorldRenderEvents.END.register(worldRenderContext -> {
            PoseStack poseStack = worldRenderContext.matrixStack();
            MultiBufferSource buffer = worldRenderContext.consumers();
            Camera camera = worldRenderContext.camera();
            BlockPos camPos = camera.getBlockPosition();
            //exampleTessellatorRGBTriangle(new Vector3f(camPos.getX()+3,camPos.getY(),camPos.getZ()),poseStack);
            //test0(null,poseStack, buffer);
            poseStack.pushPose();
            //drawCube(buffer.getBuffer(RenderType.endGateway()),poseStack.last(),11,1);
            poseStack.scale(2f,2f,2f);
            poseStack.popPose();
        });
    }

    public static void test0(GeoRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource){
        RenderType renderType = RenderType.endGateway();
        //VertexConsumer vertexConsumer = ItemRenderer.getFoilBuffer(bufferSource, renderType, false, renderState.getGeckolibData(DataTickets.HAS_GLINT));
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
        poseStack.pushPose();
        poseStack.translate(0,1,-1);
        //poseStack.scale(9,9,9);
        vertexConsumer.addVertex(poseStack.last().pose(), 0,0,0).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);
        vertexConsumer.addVertex(poseStack.last().pose(),1,1,1).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);
        vertexConsumer.addVertex(poseStack.last().pose(),0,1,0).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);
        vertexConsumer.addVertex(poseStack.last().pose(),1,0,1).setColor(-1).setOverlay(655360).setLight(15728640).setNormal(0.5f,0.5f,0.5f);

        poseStack.popPose();
    }

    public static void test1(){     WorldRenderEvents.END.register(context -> {


        var matrices = context.matrixStack();
        matrices.pushPose();
        //drawSquareInFrontOfCamera(matrices,1,context.camera());
        matrices.popPose();
    });

        WorldRenderEvents.END.register(context -> {
            VertexConsumer buffer = context.consumers().getBuffer(RenderType.lines());
            var matrices = context.matrixStack();
            matrices.pushPose();

            buffer.addVertex( 1, 1, 1).setColor(1f, 0f, 0f, 1f).setNormal(0,0,0);
            buffer.addVertex( 0, 0, 0).setColor(1f, 0f, 0f, 1f).setNormal(0,0,0);


            //matrices.translate(0,0,-2);
            //drawRedSquare(matrices, 10);
            //drawRedSquare2(matrices, 10);
            matrices.popPose();
        });
        WorldRenderEvents.END.register(context -> {
            if (!shouldDraw || lineStart == null || lineEnd == null) return;

            Vec3 camPos = context.camera().getPosition();


            double sx = lineStart.x - camPos.x;
            double sy = lineStart.y - camPos.y;
            double sz = lineStart.z - camPos.z;

            double ex = lineEnd.x - camPos.x;
            double ey = lineEnd.y - camPos.y;
            double ez = lineEnd.z - camPos.z;

            var matrices = context.matrixStack();
            matrices.pushPose();


            //drawLine(matrices, sx, sy, sz, ex, ey, ez);

            //
            matrices.popPose();
        });
    }


    private static void drawLine(PoseStack matrices,
                                 double sx, double sy, double sz,
                                 double ex, double ey, double ez) {

        var modelView = matrices.last().pose();

        // disabilitare texture e depth test se vuoi sovrapporre
        //RenderSystem.disableScissorForRenderTypeDraws();

        //RenderSystem.disableTexture();
        //RenderSystem.depthMask(false);
        //RenderSystem.disableDepthTest();
        RenderSystem.lineWidth(2.0F);


        var tesselator = Tesselator.getInstance();
        var buffer = tesselator.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);


        float r = 1.0f, g = 0.0f, b = 0.0f, a = 1.0f;
        buffer.addVertex(modelView, (float)sx, (float)sy, (float)sz).setColor(r, g, b, a);
        buffer.addVertex(modelView, (float)ex, (float)ey, (float)ez).setColor(r, g, b, a);


        buffer.build();

        //RenderSystem.enableDepthTest();
        //RenderSystem.depthMask(true);
        //RenderSystem.enableTexture();

    }

    public static void exampleTessellatorRGBTriangle(Vector3f center, PoseStack matrices) {
        matrices.pushPose();
        matrices.translate(center.x,center.y, center.z);

        Matrix4f matrix = matrices.last().pose();
        Tesselator tessellator = Tesselator.getInstance();

        //For colors
        BufferBuilder buffer = tessellator.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        buffer.addVertex(matrix, 0.5f, 1.0f, 0.5f).setColor(255, 0, 0, 255);  // Punto superiore
        buffer.addVertex(matrix, 1.0f, 0.0f, 0f).setColor(0, 255, 0, 255);  // Punto sinistro
        buffer.addVertex(matrix, 0f, 0.0f, 0f).setColor(0, 0, 255, 255);  // Punto destro

        //RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.setShaderLights(RenderSystem.getShaderLights());

        //RenderSystem.disableCull();

        //RenderSystem.enableDepthTest();

        //BufferRenderer.drawWithGlobalProgram(buffer.end());

        matrices.popPose();
    }

    private static void drawRedSquare(PoseStack matrices, float size) {
        var modelView = matrices.last().pose();
        float half = size / 2.0f;
        //System.out.println("Disegno");
        //RenderSystem.disableDepthTest();
        RenderSystem.lineWidth(20.0F);


        var tesselator = Tesselator.getInstance();
        var buffer = tesselator.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);

        float r = 1f, g = 0f, b = 0f, a = 1f;

        // 4 linee che formano un quadrato
        buffer.addVertex(modelView, -half, -half, 0f).setColor(r, g, b, a);
        buffer.addVertex(modelView, half, -half, 0f).setColor(r, g, b, a);

        buffer.addVertex(modelView, half, -half, 0f).setColor(r, g, b, a);
        buffer.addVertex(modelView, half, half, 0f).setColor(r, g, b, a);

        buffer.addVertex(modelView, half, half, 0f).setColor(r, g, b, a);
        buffer.addVertex(modelView, -half, half, 0f).setColor(r, g, b, a);

        buffer.addVertex(modelView, -half, half, 0f).setColor(r, g, b, a);
        buffer.addVertex(modelView, -half, -half, 0f).setColor(r, g, b, a);

        buffer.build().close();






        //RenderSystem.enableDepthTest();
    }
    private static void drawRedSquare2(PoseStack matrices, float size) {
        var modelView = matrices.last().pose();
        float half = size / 2.0f;
        //System.out.println("Disegno");
        //RenderSystem.disableDepthTest();
        //RenderSystem.lineWidth(20.0F);


        var tesselator = Tesselator.getInstance();
        var buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        float r = 1f, g = 0f, b = 0f, a = 1f;

        buffer.addVertex(modelView, -half, -half, 0f).setColor(r, g, b, a);
        buffer.addVertex(modelView, half, -half, 0f).setColor(r, g, b, a);
        buffer.addVertex(modelView, half, half, 0f).setColor(r, g, b, a);
        buffer.addVertex(modelView, -half, half, 0f).setColor(r, g, b, a);

        MeshData meshData = buffer.build();
        ByteBuffer bb = meshData.indexBuffer();






        tesselator.clear();




        //RenderSystem.enableDepthTest();
    }

    private static void drawSquareInFrontOfCamera(PoseStack matrices, float size, Camera camera) {
        var modelView = matrices.last().pose();
        float half = size / 2.0f;

        // Calcola la posizione davanti alla camera (ad esempio 2 blocchi davanti)
        Vec3 camPos = camera.getPosition();
        Vector3f vector = camera.getLookVector();
        Vec3 lookVec = new Vec3(vector);
        Vec3 center = camPos.add(lookVec.scale(2.0)); // 2 blocchi davanti

        float x = (float) center.x;
        float y = (float) center.y;
        float z = (float) center.z;

        var tesselator = Tesselator.getInstance();
        var buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        float r = 1f, g = 0f, b = 0f, a = 1f;

        buffer.addVertex(modelView, x - half, y - half, z).setColor(r, g, b, a);
        buffer.addVertex(modelView, x + half, y - half, z).setColor(r, g, b, a);
        buffer.addVertex(modelView, x + half, y + half, z).setColor(r, g, b, a);
        buffer.addVertex(modelView, x - half, y + half, z).setColor(r, g, b, a);

        buffer.build();
    }

    private static void drawCube(VertexConsumer vertexConsumer,PoseStack.Pose entry, int light, int overlay) {
        float[][] vertices = {
                {0, 0, 0}, {1, 0, 0}, {1, 1, 0}, {0, 1, 0}, // Back (Z-)
                {0, 0, 1}, {1, 0, 1}, {1, 1, 1}, {0, 1, 1}, // Front (Z+)
        };

        int[][] faces = {
                {0, 1, 2, 3,  0, 0, -1}, // Back
                {5, 4, 7, 6,  0, 0, 1},  // Front
                {4, 0, 3, 7, -1, 0, 0}, // Left
                {1, 5, 6, 2,  1, 0, 0},  // Right
                {3, 2, 6, 7,  0, 1, 0},  // Top
                {4, 5, 1, 0,  0, -1, 0}  // Bottom
        };

        for (int[] face : faces) {
            addVertex(entry, vertexConsumer, vertices[face[0]], light, overlay, face[4], face[5], face[6], 0, 0);
            addVertex(entry, vertexConsumer, vertices[face[1]], light, overlay, face[4], face[5], face[6], 1, 0);
            addVertex(entry, vertexConsumer, vertices[face[2]], light, overlay, face[4], face[5], face[6], 1, 1);
            addVertex(entry, vertexConsumer, vertices[face[3]], light, overlay, face[4], face[5], face[6], 0, 1);
        }
    }

    private static void addVertex(PoseStack.Pose entry, VertexConsumer vertexConsumer, float[] pos, int light, int overlay, float nx, float ny, float nz, float u, float v) {
        vertexConsumer.addVertex(entry, pos[0], pos[1], pos[2])
                .setColor(255, 255, 255, 255)
                .setUv(u, v)  // Corretto UV mapping
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(entry, nx, ny, nz);
    }


}
