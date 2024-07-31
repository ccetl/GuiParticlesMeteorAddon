package de.ccetl.particles.addon.modules.particles;

import de.ccetl.jparticles.systems.ParticleSystem;
import de.ccetl.particles.addon.event.RenderScreenEvent;

interface ParticleConfig {

    void apply(ParticlesModule module, ParticleSystem.DefaultConfig config);

    void render(ParticlesModule module, RenderScreenEvent event);

    void start(ParticlesModule module);

    void stop(ParticlesModule module);

}
