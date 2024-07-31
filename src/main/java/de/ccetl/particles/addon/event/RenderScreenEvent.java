package de.ccetl.particles.addon.event;

import net.minecraft.client.gui.DrawContext;

public class RenderScreenEvent {

    public static final RenderScreenEvent INSTANCE = new RenderScreenEvent();

    private DrawContext context;
    private int mouseX;
    private int mouseY;

    private RenderScreenEvent() {
    }

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
