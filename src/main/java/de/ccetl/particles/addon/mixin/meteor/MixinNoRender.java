package de.ccetl.particles.addon.mixin.meteor;

import meteordevelopment.meteorclient.systems.modules.render.NoRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NoRender.class, remap = false)
public abstract class MixinNoRender {

    @Inject(method = "noGuiBackground", at = @At(value = "RETURN"), cancellable = true)
    public void noGuiBackGroundCancel(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

}
