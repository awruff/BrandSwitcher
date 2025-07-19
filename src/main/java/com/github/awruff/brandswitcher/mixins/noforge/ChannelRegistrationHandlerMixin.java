package com.github.awruff.brandswitcher.mixins.noforge;

import com.github.awruff.brandswitcher.BrandSwitcherConfig;
import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.common.network.handshake.ChannelRegistrationHandler;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChannelRegistrationHandler.class)
public class ChannelRegistrationHandlerMixin {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraftforge/fml/common/network/internal/FMLProxyPacket;)V", at = @At("HEAD"), remap = false, cancellable = true)
    public void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket msg, final CallbackInfo callbackInfo) {
        if (BrandSwitcherConfig.INSTANCE.getNoForge()) {
            callbackInfo.cancel();
        }
    }
}
