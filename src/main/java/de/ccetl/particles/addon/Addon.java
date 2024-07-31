package de.ccetl.particles.addon;

import com.mojang.logging.LogUtils;
import de.ccetl.particles.addon.modules.particles.ParticlesModule;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Addon extends MeteorAddon {

    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing GUI Particles for Meteor");

        Modules.get().add(new ParticlesModule());
    }

    @Override
    public String getPackage() {
        return "de.ccetl.particles.addon";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("ccetl", "GuiParticlesMeteorAddon");
    }

}
