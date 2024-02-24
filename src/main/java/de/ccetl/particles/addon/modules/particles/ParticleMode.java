package de.ccetl.particles.addon.modules.particles;

import de.ccetl.jparticles.core.shape.Shape;
import de.ccetl.jparticles.core.shape.ShapeType;
import de.ccetl.jparticles.systems.ParticleSystem;

public enum ParticleMode {
    @SuppressWarnings("unused") TRAILS {
        @Override
        protected void apply(ParticlesModule module, ParticleSystem.DefaultConfig config) {
            config.setRange(0);
            config.setParallax(false);
            config.setTrails(true);
            config.setShapeSupplier(() -> new Shape(ShapeType.CIRCLE));
            config.setNumber(20);
            module.number.set(20);
        }

        @Override
        public String toString() {
            return "Trails";
        }
    },
    LINES {
        @Override
        protected void apply(ParticlesModule module, ParticleSystem.DefaultConfig config) {
            config.setRange(2000);
            config.setTrails(false);
            config.setParallax(true);
            config.setShapeSupplier(() -> new Shape(ShapeType.TRIANGLE));
            config.setNumber(200);
            module.number.set(200);
        }

        @Override
        public String toString() {
            return "Lines";
        }
    };

    abstract protected void apply(ParticlesModule module, ParticleSystem.DefaultConfig config);
}
