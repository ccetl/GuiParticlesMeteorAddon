package de.ccetl.particles.addon.mixin.screens;

import de.ccetl.particles.addon.event.RenderScreenEvent;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.gui.WidgetScreen;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WidgetScreen.class)
public abstract class MixinWidgetScreen {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getScaleFactor()D", shift = At.Shift.BEFORE))
    public void renderWidgetScreenHook(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MeteorClient.EVENT_BUS.post(RenderScreenEvent.set(context, mouseX, mouseY));
    }

}
