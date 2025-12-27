package com.musketeers.foolish_guns.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RenderEffects {

    public static void drawLightning(MultiBufferSource bufferSource, PoseStack poseStack) {
        VertexConsumer lightningConsumer = bufferSource.getBuffer(RenderType.lightning());
        PoseStack.Pose pose = poseStack.last();

        Vector3f point1 = new Vector3f(1,1,0);
        Vector3f point2 = new Vector3f(0,0,0);

        int lightningColor = 0xFF0000FF;

        List<Vector3f[]> segments = generateStraightLightningSegments(point1, point2, 20, 0.15F);
        for (Vector3f[] pair : segments) {
            RenderUtils.drawConcentricSegment(lightningConsumer, pose, pair[0], pair[1], 0.5F, lightningColor);
        }
    }
    public static List<Vector3f[]> generateStraightLightningSegments(Vector3f start, Vector3f end, int segments, float deviation) {

        List<Vector3f[]> result = new ArrayList<>();
        Vector3f prev = new Vector3f(start);

        for (int i = 1; i <= segments; i++) {
            float t = (float)i / segments;
            Vector3f next = new Vector3f(
                    start.x + (end.x - start.x) * t,
                    start.y + (end.y - start.y) * t,
                    start.z + (end.z - start.z) * t
            );

            // offset casuale perpendicolare alla direzione
            Vector3f dir = new Vector3f(next).sub(prev).normalize();
            Vector3f offset = randomPerpendicular(dir).mul(deviation * (1 - t)); // deviazione decrescente verso fine
            next.add(offset);

            result.add(new Vector3f[]{new Vector3f(prev), new Vector3f(next)});
            prev.set(next);
        }

        return result;
    }
    public static List<Vector3f[]> generateArcedLightningSegments(
            Vector3f start, Vector3f end,
            int segments, float deviation, float radius) {


        List<Vector3f[]> result = new ArrayList<>();

        Vector3f dir = new Vector3f(end).sub(start);
        float length = dir.length();
        if (length <= 1e-6f) return result; // start == end guard

        dir.normalize();

        // scegli un vettore "arbitrary" non parallelo a dir
        Vector3f arbitrary = Math.abs(dir.x) < 0.9f ? new Vector3f(1, 0, 0) : new Vector3f(0, 1, 0);

        // normal dovrebbe essere non-zero: normal = arbitrary x dir
        Vector3f normal = new Vector3f(arbitrary).cross(dir);
        if (normal.length() < 1e-6f) {
            // fallback alternativo (rare)
            arbitrary = new Vector3f(0, 0, 1);
            normal.set(arbitrary).cross(dir);
            if (normal.length() < 1e-6f) {
                // estremo fallback: usa un asse qualunque
                normal.set(1, 0, 0).cross(dir);
            }
        }
        normal.normalize();

        // binormal nel piano (perpendicolare a dir e normal)
        Vector3f binormal = new Vector3f(dir).cross(normal).normalize();

        Vector3f prev = new Vector3f(start);

        for (int i = 1; i <= segments; i++) {
            float t = (float) i / segments;

            // punto lineare
            Vector3f next = new Vector3f(start).add(new Vector3f(dir).mul(length * t));

            // curva: sin(pi*t) * radius, direzione lungo 'binormal' (sta nel piano della corda)
            float curveAmount = (float) (Math.sin(Math.PI * t) * radius);
            next.add(new Vector3f(binormal).mul(curveAmount));

            // tangent: proteggi da zero-length
            Vector3f tangent = new Vector3f(next).sub(prev);
            if (tangent.length() < 1e-6f) {
                tangent.set(dir); // fallback se i punti coincidono
            } else {
                tangent.normalize();
            }

            Vector3f offset = randomPerpendicularSafe(tangent).mul(deviation * (1 - t));
            next.add(offset);

            result.add(new Vector3f[]{new Vector3f(prev), new Vector3f(next)});
            prev.set(next);
        }

        return result;
    }

    private static Vector3f randomPerpendicularSafe(Vector3f dir) {
        // se dir quasi zero fallback su Y
        if (dir.length() < 1e-6f) dir.set(0, 1, 0);

        Vector3f up = Math.abs(dir.y) < 0.99f ? new Vector3f(0, 1, 0) : new Vector3f(1, 0, 0);
        Vector3f perp = new Vector3f(dir).cross(up);
        if (perp.length() < 1e-6f) {
            // pick another up if parallel
            up.set(1, 0, 0);
            perp.set(dir).cross(up);
            if (perp.length() < 1e-6f) {
                up.set(0, 0, 1);
                perp.set(dir).cross(up);
            }
        }
        perp.normalize();
        Vector3f perp2 = new Vector3f(dir).cross(perp).normalize();
        Random RANDOM = new Random();
        float a = RANDOM.nextFloat() * 2 - 1;
        float b = RANDOM.nextFloat() * 2 - 1;

        return new Vector3f(perp).mul(a).add(new Vector3f(perp2).mul(b));
    }
    public static Vector3f randomPerpendicular(Vector3f dir) {
        Vector3f up = Math.abs(dir.y) < 0.99f ? new Vector3f(0,1,0) : new Vector3f(1,0,0);
        Vector3f perp = new Vector3f(dir).cross(up).normalize();
        Vector3f perp2 = new Vector3f(dir).cross(perp).normalize();

        Random RANDOM = new Random();
        float a = RANDOM.nextFloat() * 2 - 1;
        float b = RANDOM.nextFloat() * 2 - 1;

        return new Vector3f(perp).mul(a).add(new Vector3f(perp2).mul(b));
    }
}
