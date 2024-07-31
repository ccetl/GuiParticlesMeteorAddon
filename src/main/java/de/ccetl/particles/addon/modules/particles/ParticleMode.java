package de.ccetl.particles.addon.modules.particles;

import de.ccetl.jparticles.core.shape.Shape;
import de.ccetl.jparticles.core.shape.ShapeType;
import de.ccetl.jparticles.systems.ParticleSystem;
import de.ccetl.particles.addon.event.RenderScreenEvent;

enum ParticleMode implements ParticleConfig {

    @SuppressWarnings("unused") TRAILS {
        @Override
        public void apply(ParticlesModule module, ParticleSystem.DefaultConfig config) {
            module.snowSystem.pause();
            config.setRange(0);
            config.setParallax(false);
            config.setTrails(true);
            config.setShapeSupplier(() -> new Shape(ShapeType.CIRCLE));
            config.setNumber(20);
            module.number.set(20);
            config.setMinSpeed(1);
            config.setMaxSpeed(0.85);
            module.particleSystem.init();
            module.particleSystem.start();
        }

        @Override
        public void render(ParticlesModule module, RenderScreenEvent event) {
            module.particleSystem.draw(event.getMouseX(), event.getMouseY());
        }

        @Override
        public void start(ParticlesModule module) {
            module.particleSystem.start();
        }

        @Override
        public void stop(ParticlesModule module) {
            module.particleSystem.pause();
        }

        @Override
        public String toString() {
            return "Trails";
        }

    },
    LINES {
        @Override
        public void apply(ParticlesModule module, ParticleSystem.DefaultConfig config) {
            module.snowSystem.pause();
            config.setRange(2000);
            config.setTrails(false);
            config.setParallax(true);
            config.setShapeSupplier(() -> new Shape(ShapeType.TRIANGLE));
            config.setNumber(200);
            module.number.set(200);
            config.setMinSpeed(0.4);
            config.setMaxSpeed(0.6);
            module.particleSystem.init();
            module.particleSystem.start();
        }

        @Override
        public void render(ParticlesModule module, RenderScreenEvent event) {
            module.particleSystem.draw(event.getMouseX(), event.getMouseY());
        }

        @Override
        public void start(ParticlesModule module) {
            module.particleSystem.start();
        }

        @Override
        public void stop(ParticlesModule module) {
            module.particleSystem.pause();
        }

        @Override
        public String toString() {
            return "Lines";
        }

    },
    SNOW {
        @Override
        public void apply(ParticlesModule module, ParticleSystem.DefaultConfig config) {
            module.particleSystem.pause();
            module.snowSystem.init();
            module.snowSystem.start();
        }

        @Override
        public void render(ParticlesModule module, RenderScreenEvent event) {
            module.snowSystem.draw(event.getMouseX(), event.getMouseY());
        }

        @Override
        public void start(ParticlesModule module) {
            module.snowSystem.start();
        }

        @Override
        public void stop(ParticlesModule module) {
            module.snowSystem.pause();
            module.snowSystem.init();
        }

        @Override
        public String toString() {
            return "Snow";
        }

    }

}
