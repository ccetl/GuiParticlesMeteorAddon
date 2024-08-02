package de.ccetl.particles.addon.mixin;

import de.ccetl.particles.addon.event.WindowResizedEvent;
import meteordevelopment.meteorclient.MeteorClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MixinWindow {

    @Inject(method = "onWindowSizeChanged", at = @At("RETURN"))
    private void windowSizeChangedCallback(long window, int width, int height, CallbackInfo ci) {
        MeteorClient.EVENT_BUS.post(WindowResizedEvent.INSTANCE);
    }

}
