package de.ccetl.particles.addon.event;

import net.minecraft.client.gui.DrawContext;

public class RenderScreenEvent {
    public final static RenderScreenEvent INSTANCE = new RenderScreenEvent();
    private DrawContext context;
    private int mouseX;
    private int mouseY;

    public static RenderScreenEvent set(DrawContext context, int mouseX, int mouseY) {
        INSTANCE.context = context;
        INSTANCE.mouseX = mouseX;
        INSTANCE.mouseY = mouseY;
        return INSTANCE;
    }

    public DrawContext getContext() {
        return context;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
