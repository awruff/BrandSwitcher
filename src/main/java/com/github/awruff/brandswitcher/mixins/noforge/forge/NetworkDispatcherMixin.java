package com.github.awruff.brandswitcher.mixins.noforge.forge;

import com.github.awruff.brandswitcher.ConfigHelper;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NetworkDispatcher.class, remap = false)
public class NetworkDispatcherMixin {
    @Inject(method = "handleVanilla(Lnet/minecraft/network/Packet;)Z", at = @At("HEAD"), cancellable = true)
    private void handleVanilla(Packet<?> msg, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigHelper.disableForge()) cir.setReturnValue(false);
    }
}
