package com.github.awruff.brandswitcher.mixins.noforge.patches;

import com.github.awruff.brandswitcher.ConfigHelper;
import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.common.network.handshake.ChannelRegistrationHandler;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChannelRegistrationHandler.class, remap = false)
public class ChannelRegistrationHandlerMixin {
    @Inject(
            method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraftforge/fml/common/network/internal/FMLProxyPacket;)V",
            at = @At("HEAD"), cancellable = true
    )
    private void channelRead0Vanilla(ChannelHandlerContext ctx, FMLProxyPacket msg, final CallbackInfo ci) {
        if (ConfigHelper.disableForge()) ci.cancel();
    }
}
