package de.ccetl.particles.addon.render;

import meteordevelopment.meteorclient.renderer.DrawMode;
import meteordevelopment.meteorclient.renderer.Shader;
import meteordevelopment.meteorclient.renderer.ShaderMesh;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class CircleMesh extends ShaderMesh {

    public CircleMesh(Shader shader, DrawMode drawMode, Attrib... attributes) {
        super(shader, drawMode, attributes);
    }

    // this would belong in the Renderer2d class, but it's faster if I just put it here
    public void circle(double centerX, double centerY, double radius, Color color, int segments) {
        if (segments < 3) {
            throw new IllegalArgumentException("A circle must have at least 3 segments.");
        }

        int centerIndex = next();
        vec2(centerX, centerY).color(color);

        int[] indices = new int[segments + 2];
        indices[0] = centerIndex;

        for (int i = 0; i <= segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            indices[i + 1] = next();
            vec2(x, y).color(color);
        }

        for (int i = 1; i < indices.length - 1; i++) {
            triangle(indices[0], indices[i], indices[i + 1]);
        }
    }

}
