package com.github.awruff.brandswitcher.mixins.noforge;


import com.github.awruff.brandswitcher.BrandSwitcherConfig;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetworkDispatcher.class)
public class NetworkDispatcherMixin {
    @Inject(method = "handleVanilla(Lnet/minecraft/network/Packet;)Z", at = @At("HEAD"), remap = false, cancellable = true)
    private void handleVanilla(Packet<?> msg, CallbackInfoReturnable<Boolean> cir) {
        if (BrandSwitcherConfig.INSTANCE.getNoForge()) {
            cir.setReturnValue(false);
        }
    }
}
