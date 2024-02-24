package de.ccetl.particles.addon.render;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ccetl.jparticles.core.Renderer;
import de.ccetl.jparticles.util.Vec2d;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.utils.render.color.Color;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class EffectRenderer implements Renderer {
    public static final EffectRenderer INSTANCE = new EffectRenderer();
    public static final Renderer2D renderer = new Renderer2D(false);
    private static final double ANGLE = Math.PI / 180D;
    public static DrawContext context;

    @Override
    public void drawLine(double x, double y, double x1, double y1, double width, int color) {
        renderer.begin();
        renderer.line(x, y, x1, y1, new Color(color));
        renderer.end();
        renderer.render(new MatrixStack());
    }

    @Override
    public void drawLine(Vec2d[] vertices, double width, int color) {
    }

    @Override
    public void drawLineRotated(double x, double y, double x1, double y1, double translationX, double translationY, double width, double radians, int color) {
    }

    @Override
    public void drawCircle(double x, double y, double radius, int color) {
        RenderSystem.setShaderColor(((color >> 16) & 0xFF) / 255F, ((color >> 8) & 0xFF) / 255F, (color & 0xFF) / 255F, ((color >> 24) & 0xFF) / 255F);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION);
        bufferbuilder.vertex(matrix4f, (float) x, (float) y, 0f).next();
        for (double i = 0; i <= 360; i++) {
            bufferbuilder.vertex(matrix4f, (float) (x + Math.sin(i * ANGLE) * radius), (float) (y + Math.cos(i * ANGLE) * radius), 0F).next();
        }
        BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }

    @Override
    public void drawTriangle(double x, double y, double radius, int color) {
        renderer.begin();
        renderer.triangle(x, y - radius, x + radius, y + radius, x - radius, y + radius, new Color(color));
        renderer.end();
        renderer.render(new MatrixStack());
    }

    @Override
    public void drawStar(double x, double y, double radius, int sides, double dent, int color) {
    }

    @Override
    public void drawImage(double x, double y, double radius, int id) {
    }

    @Override
    public void drawPolygon(Vec2d[] vertices, int color) {
    }
}
