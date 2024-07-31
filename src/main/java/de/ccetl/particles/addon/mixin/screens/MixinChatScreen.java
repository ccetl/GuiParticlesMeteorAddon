package de.ccetl.particles.addon.mixin.screens;

import de.ccetl.particles.addon.event.RenderScreenEvent;
import meteordevelopment.meteorclient.MeteorClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen {

    @Inject(method = "renderBackground", at = @At("RETURN"))
    public void renderBackgroundHook(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        var renderScreenEvent = RenderScreenEvent.set(context, mouseX, mouseY);
        MeteorClient.EVENT_BUS.post(renderScreenEvent);
    }

}
