package com.github.awruff.brandswitcher.mixins.noforge.patches;

import com.github.awruff.brandswitcher.ConfigHelper;
import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.common.network.handshake.HandshakeMessageHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HandshakeMessageHandler.class, remap = false)
public class HandshakeMessageHandlerMixin {
    @Inject(
            method = "userEventTriggered(Lio/netty/channel/ChannelHandlerContext;Ljava/lang/Object;)V",
            at = @At("HEAD"), cancellable = true
    )
    private void userEventTriggeredVanilla(ChannelHandlerContext ctx, Object obj, final CallbackInfo ci) {
        if (ConfigHelper.disableForge()) ci.cancel();
    }
}
