package com.github.awruff.brandswitcher.mixins.noforge;

import com.github.awruff.brandswitcher.BrandSwitcherConfig;
import com.github.awruff.brandswitcher.ConfigHelper;
import com.github.awruff.brandswitcher.mixins.accessors.IC17PacketCustomPayload;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author CCBlueX
 */
@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacketVanilla(final Packet<?> packet, final CallbackInfo ci) {
        if (!ConfigHelper.disableForge()) return;

        if (packet instanceof FMLProxyPacket) {
            ci.cancel();
            return;
        }

        if (packet instanceof C17PacketCustomPayload) {
            final C17PacketCustomPayload payload = (C17PacketCustomPayload) packet;
            final String channelName = payload.getChannelName();

            if (!channelName.startsWith("MC|")) {
                ci.cancel();
            } else if (channelName.equalsIgnoreCase("MC|Brand")) {
                final PacketBuffer buf = new PacketBuffer(Unpooled.buffer()).writeString(ConfigHelper.getBrand());
                ((IC17PacketCustomPayload) payload).setData(buf);
            }
        }
    }
}
