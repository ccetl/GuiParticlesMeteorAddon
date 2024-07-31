package de.ccetl.particles.addon.modules.particles;

import de.ccetl.jparticles.core.Renderer;
import de.ccetl.jparticles.core.shape.Shape;
import de.ccetl.jparticles.core.shape.ShapeType;
import de.ccetl.jparticles.event.ResizeEvent;
import de.ccetl.jparticles.systems.ParticleSystem;
import de.ccetl.jparticles.systems.SnowSystem;
import de.ccetl.jparticles.types.particle.LineShape;
import de.ccetl.jparticles.types.particle.Obstacle;
import de.ccetl.particles.addon.event.RenderScreenEvent;
import de.ccetl.particles.addon.render.EffectRenderer;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.game.WindowResizedEvent;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
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
    protected final ParticleSystem particleSystem = new ParticleSystem(config, mc.getWindow().getWidth(), mc.getWindow().getHeight());
    private final Setting<ParticleMode> mode;
    private final SnowSystem.DefaultConfig snowConfig = new SnowSystem.DefaultConfig() {
        @Override
        public Renderer getRenderer() {
            return EffectRenderer.INSTANCE;
        }
    };
    protected final SnowSystem snowSystem = new SnowSystem(snowConfig, mc.getWindow().getWidth(), mc.getWindow().getHeight());

    public ParticlesModule() {
        super(Categories.Render, "Particles", "Adds GUI particle effects.");
        MeteorClient.EVENT_BUS.subscribe(new ConsumerListener<>(WindowResizedEvent.class, 0, this::onResize));
        var defaultGroup = settings.getDefaultGroup();
        mode = defaultGroup.add(new EnumSetting.Builder<ParticleMode>().name("mode").defaultValue(ParticleMode.LINES).onChanged(v -> v.apply(this, config)).build());
        number = defaultGroup.add(new IntSetting.Builder().name("number").description("The amount of particles.").defaultValue(200).min(1).max(5000).visible(() -> mode.get() != ParticleMode.SNOW).onChanged(config::setNumber).build());
        defaultGroup.add(new ColorSetting.Builder().name("color").description("The color of the particles.").defaultValue(Color.MAGENTA).visible(() -> mode.get() != ParticleMode.SNOW).onChanged(settingColor -> config.setColorSupplier(settingColor::getPacked)).build());
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
        snowConfig.setStrict(true);
        snowConfig.setNumber(80);
        snowSystem.init();
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        var reset = theme.button("Reset");
        reset.action = particleSystem::init;
        return reset;
    }

    @Override
    public void onActivate() {
        particleSystem.init();
        snowSystem.init();
    }

    @EventHandler
    public void onRenderBackground(RenderScreenEvent event) {
        if (mc.currentScreen instanceof ChatScreen && !chatScreen.get()) {
            return;
        }

        mode.get().render(this, event);
        EffectRenderer.INSTANCE.renderAndClear(event.getContext());
    }

    @EventHandler
    public void onOpenScreen(OpenScreenEvent event) {
        if (event.screen == null || event.screen instanceof ChatScreen && !chatScreen.get()) {
            mode.get().stop(this);
        } else {
            mode.get().start(this);
        }
    }

    public void onResize(WindowResizedEvent event) {
        var resizeEvent = new ResizeEvent(mc.getWindow().getWidth(), mc.getWindow().getHeight());
        particleSystem.onResize(resizeEvent);
        snowSystem.onResize(resizeEvent);
    }

}
