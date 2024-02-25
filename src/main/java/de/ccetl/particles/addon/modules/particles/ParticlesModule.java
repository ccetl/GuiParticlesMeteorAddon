package de.ccetl.particles.addon.modules.particles;

import de.ccetl.jparticles.core.Renderer;
import de.ccetl.jparticles.core.shape.Shape;
import de.ccetl.jparticles.core.shape.ShapeType;
import de.ccetl.jparticles.event.ResizeEvent;
import de.ccetl.jparticles.systems.ParticleSystem;
import de.ccetl.jparticles.types.particle.LineShape;
import de.ccetl.jparticles.types.particle.Obstacle;
import de.ccetl.particles.addon.event.RenderScreenEvent;
import de.ccetl.particles.addon.render.EffectRenderer;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.game.WindowResizedEvent;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.listeners.ConsumerListener;
import net.minecraft.client.gui.screen.ChatScreen;

public class ParticlesModule extends Module {
    protected final Setting<Boolean> chatScreen = new BoolSetting.Builder().name("chat-screen").description("Whether particles get rendered in the chat GUI.").defaultValue(true).build();
    protected final Setting<Integer> number;
    private final ParticleSystem.DefaultConfig config = new ParticleSystem.DefaultConfig() {
        @Override
        public Renderer getRenderer() {
            return EffectRenderer.INSTANCE;
        }
    };
    private final ParticleSystem particleSystem = new ParticleSystem(config, mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight());

    public ParticlesModule() {
        super(Categories.Render, "Particles", "Adds GUI particle effects.");
        MeteorClient.EVENT_BUS.subscribe(new ConsumerListener<>(WindowResizedEvent.class, 0, this::onResize));
        SettingGroup defaultGroup = settings.getDefaultGroup();
        defaultGroup.add(new ColorSetting.Builder().name("color").description("The color of the particles.").defaultValue(Color.MAGENTA).onChanged(settingColor -> config.setColorSupplier(settingColor::getPacked)).build());
        number = defaultGroup.add(new IntSetting.Builder().name("number").description("The amount of particles.").defaultValue(200).min(1).max(5000).onChanged(config::setNumber).build());
        defaultGroup.add(new EnumSetting.Builder<ParticleMode>().name("mode").defaultValue(ParticleMode.LINES).onChanged(v -> {
            v.apply(this, config);
            particleSystem.init();
        }).build());
        defaultGroup.add(chatScreen);
        config.setMinRadius(1);
        config.setMaxRadius(3);
        config.setRange(5000);
        config.setHoverRepulse(true);
        config.setParallax(true);
        config.setProximity(100);
        config.setLineShape(LineShape.CUBE);
        config.setLineWidth(2);
        config.setTrailAlive(333);
        config.setTrailUpdate(12);
        config.setCollisionIntern(Obstacle.BOUNCE);
        config.setCollisionEdge(Obstacle.IGNORE);
        config.setShapeSupplier(() -> new Shape(ShapeType.TRIANGLE));
        config.setCenterLines(true);
        config.setNumber(200);
        config.setMinSpeed(0.4);
        config.setMaxSpeed(0.6);
        particleSystem.init();
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        WButton reset = theme.button("Reset");
        reset.action = particleSystem::init;
        return reset;
    }

    @Override
    public void onActivate() {
        particleSystem.init();
    }

    @EventHandler
    public void onRenderBackground(RenderScreenEvent event) {
        if (mc.currentScreen instanceof ChatScreen && !chatScreen.get()) {
            return;
        }

        EffectRenderer.context = event.getContext();
        particleSystem.draw(event.getMouseX(), event.getMouseY());
    }

    @EventHandler
    public void onOpenScreen(OpenScreenEvent event) {
        if (event.screen == null || event.screen instanceof ChatScreen && !chatScreen.get()) {
            particleSystem.pause();
        } else {
            particleSystem.start();
        }
    }

    public void onResize(WindowResizedEvent event) {
        ResizeEvent resizeEvent = new ResizeEvent(mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight());
        particleSystem.onResize(resizeEvent);
    }
}
