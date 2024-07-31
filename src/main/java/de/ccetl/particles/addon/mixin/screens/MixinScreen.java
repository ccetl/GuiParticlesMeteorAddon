package de.ccetl.particles.addon.mixin.screens;

import de.ccetl.particles.addon.event.RenderScreenEvent;
import de.ccetl.particles.addon.mixin.meteor.INoRender;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.NoRender;
import meteordevelopment.meteorclient.utils.Utils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Screen.class)
public abstract class MixinScreen {

    @Inject(method = "renderBackground", at = @At(value = "HEAD"), cancellable = true)
    public void renderBackgroundNoRenderHook(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (Utils.canUpdate()) {
            var noRender = Modules.get().get(NoRender.class);
            if (noRender.isActive() && ((INoRender) noRender).getNoGuiBackground().get()) {
                MeteorClient.EVENT_BUS.post(RenderScreenEvent.set(context, mouseX, mouseY));
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderBackground", at = @At("RETURN"))
    public void renderBackgroundHook(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MeteorClient.EVENT_BUS.post(RenderScreenEvent.set(context, mouseX, mouseY));
    }

}
