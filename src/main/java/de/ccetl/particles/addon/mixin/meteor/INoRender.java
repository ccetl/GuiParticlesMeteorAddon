package de.ccetl.particles.addon.mixin.meteor;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.render.NoRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = NoRender.class, remap = false)
public interface INoRender {

    @Accessor
    Setting<Boolean> getNoGuiBackground();

}
