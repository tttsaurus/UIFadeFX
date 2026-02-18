package com.tttsaurus.uifadefx.mixin;

import com.tttsaurus.uifadefx.UIFadeFX;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Vanilla mixin example
 * Refmap will be handled by Unimined automatically
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "createDisplay", at = @At("HEAD"))
    public void inject(CallbackInfo ci){
        UIFadeFX.LOGGER.info("Mixin succeed!");
    }
}
