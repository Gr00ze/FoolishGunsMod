package com.musketeers.foolish_guns.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class RenderUtils {


    public static void drawConcentricSegment(VertexConsumer lightningConsumer, PoseStack.Pose pose, Vector3f point1, Vector3f point2, float radius_Multiplier, int lineColor) {
        int baseRGB = lineColor & 0x00FFFFFF;

        int argb1 = (0x33 << 24) | lerpToWhite(baseRGB, 0.0f);
        int argb2 = (0x55 << 24) | lerpToWhite(baseRGB, 0.5f);
        int argb3 = (0xFF << 24) | lerpToWhite(baseRGB, 1.0f);

        drawSegment(lightningConsumer, pose, point1, point2, argb1, 0.1F * radius_Multiplier);
        drawSegment(lightningConsumer, pose, point1, point2, argb2, 0.05F * radius_Multiplier);
        drawSegment(lightningConsumer, pose, point1, point2, argb3, 0.01F * radius_Multiplier);
    }

    public static int lerpToWhite(int rgb, float t) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        r = (int) (r + (255 - r) * t);
        g = (int) (g + (255 - g) * t);
        b = (int) (b + (255 - b) * t);

        return (r << 16) | (g << 8) | b;
    }
    public static void drawSegment(VertexConsumer lightningConsumer, PoseStack.Pose pose, Vector3f point1, Vector3f point2, int argb, float radius){
        Vector3f[] verts = generateFacesVerticesFromPoints(point1, point2, radius);

        int[][] sideFaces = {
                {0, 1, 5, 4},
                {1, 2, 6, 5},
                {2, 3, 7, 6},
                {3, 0, 4, 7}
        };
        RenderUtils.drawFacesVertexColor(lightningConsumer, pose, verts, sideFaces, argb);
    }

    public static Vector3f @NotNull [] generateFacesVerticesFromPoints(Vector3f point1, Vector3f point2, float radius) {
        Vector3f dir = new Vector3f(point1).sub(point2).normalize();

        Vector3f up = Math.abs(dir.y) < 0.99f ? new Vector3f(0,1,0) : new Vector3f(1,0,0);


        // Calcola due vettori perpendicolari a dir
        Vector3f perp1 = new Vector3f(dir).cross(up).normalize().mul(radius);
        Vector3f perp2 = new Vector3f(dir).cross(perp1).normalize().mul(radius);

        // 4 vertici alla base (intorno ad A)
        Vector3f v0 = new Vector3f(point1).add(perp1).add(perp2);
        Vector3f v1 = new Vector3f(point1).add(perp1).sub(perp2);
        Vector3f v2 = new Vector3f(point1).sub(perp1).sub(perp2);
        Vector3f v3 = new Vector3f(point1).sub(perp1).add(perp2);

        // 4 vertici in cima (intorno a B)
        Vector3f v4 = new Vector3f(point2).add(perp1).add(perp2);
        Vector3f v5 = new Vector3f(point2).add(perp1).sub(perp2);
        Vector3f v6 = new Vector3f(point2).sub(perp1).sub(perp2);
        Vector3f v7 = new Vector3f(point2).sub(perp1).add(perp2);

        return new Vector3f[]{v0,v1,v2,v3,v4,v5,v6,v7};
    }
    
    
    public static void drawFacesVertexColor(VertexConsumer consumer, PoseStack.Pose pose, Vector3f[] verts, int[][] faces, int argb) {
        for (int[] face : faces) {
            Vector3f[] quadVerts = new Vector3f[4];
            for (int i = 0; i < 4; i++) {
                quadVerts[i] = verts[face[i]];
            }
            drawQuadVertexColor(consumer, pose, quadVerts, argb);
        }
    }

    public static void drawQuadVertexColor(VertexConsumer consumer, PoseStack.Pose pose, Vector3f[] verts, int argb) {
        for (Vector3f v : verts) {
            consumer.addVertex(pose, v).setColor(argb);
        }
    }
}
