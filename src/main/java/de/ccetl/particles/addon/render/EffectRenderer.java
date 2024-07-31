package de.ccetl.particles.addon.render;

import de.ccetl.jparticles.core.Renderer;
import de.ccetl.jparticles.util.Vec2d;
import meteordevelopment.meteorclient.renderer.DrawMode;
import meteordevelopment.meteorclient.renderer.Mesh;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.renderer.Shaders;
import meteordevelopment.meteorclient.utils.render.color.Color;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class EffectRenderer implements Renderer {

    public static final EffectRenderer INSTANCE = new EffectRenderer();

    private static final Renderer2D renderer = new Renderer2D(false);
    private static final CircleMesh circleMesh;

    static {
        circleMesh = new CircleMesh(Shaders.POS_COLOR, DrawMode.Triangles, Mesh.Attrib.Vec2, Mesh.Attrib.Color);
    }

    private final List<Line> lines = new ArrayList<>();
    private final List<Circle> circles = new ArrayList<>();
    private final List<Triangle> triangles = new ArrayList<>();

    private EffectRenderer() {
    }

    @Override
    public void drawLine(double x, double y, double x1, double y1, double width, int color) {
        lines.add(new Line(x, y, x1, y1, color));
    }

    @Override
    public void drawLine(Vec2d[] vertices, double width, int color) {
        /* not needed */
    }

    @Override
    public void drawLineRotated(double x, double y, double x1, double y1, double translationX, double translationY, double width, double radians, int color) {
        /* not needed */
    }

    @Override
    public void drawCircle(double x, double y, double radius, double degrees, int color) {
        circles.add(new Circle((float) x, (float) y, radius, color));
    }

    @Override
    public void drawTriangle(double x, double y, double radius, double degrees, int color) {
        triangles.add(new Triangle(x, y, radius, color));
    }

    @Override
    public void drawStar(double x, double y, double radius, int sides, double dent, double degrees, int color) {
        /* not needed */
    }

    @Override
    public void drawImage(double x, double y, double radius, double degrees, int id) {
        /* not needed */
    }

    @Override
    public void drawPolygon(Vec2d[] vertices, int color) {
        /* not needed */
    }

    public void renderAndClear(DrawContext context) {
        var matrixStack = context.getMatrices();

        renderer.begin();
        circleMesh.begin();

        lines.forEach(line -> renderer.line(line.x(), line.y(), line.x1(), line.y1(), new Color(line.color())));
        triangles.forEach(triangle -> {
            var x = triangle.x();
            var y = triangle.y();
            var radius = triangle.radius();
            renderer.triangle(x, y - radius, x + radius, y + radius, x - radius, y + radius, new Color(triangle.color()));
        });
        circles.forEach(circle -> circleMesh.circle(circle.x(), circle.y(), circle.radius(), new Color(circle.color()), 180));

        renderer.end();

        // the renderer#render method would render them in the wrong order, so each has to be
        // called here individually
        renderer.lines.render(matrixStack);
        renderer.triangles.render(matrixStack);
        circleMesh.render(matrixStack);

        lines.clear();
        triangles.clear();
        circles.clear();
    }

}
