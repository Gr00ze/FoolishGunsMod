package com.musketeers.foolish_guns.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.nio.ByteBuffer;

public class RenderTest {
    private static volatile Vec3 lineStart = null;
    private static volatile Vec3 lineEnd = null;
    private static volatile boolean shouldDraw = false;

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

}
