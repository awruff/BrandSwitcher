package com.github.awruff.brandswitcher.mixins.noforge;

import com.github.awruff.brandswitcher.ConfigHelper;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C00Handshake.class)
public class C00HandshakeMixin {
    @Shadow private int protocolVersion;

    @Shadow private String ip;

    @Shadow private int port;

    @Shadow private EnumConnectionState requestedState;

    @Inject(method = "writePacketData", at = @At("HEAD"), cancellable = true)
    private void writePacketDataVanilla(PacketBuffer buf, CallbackInfo ci) {
        if (ConfigHelper.disableForge()) {
            buf.writeVarIntToBuffer(this.protocolVersion);
            buf.writeString(this.ip);
            buf.writeShort(this.port);
            buf.writeVarIntToBuffer(this.requestedState.getId());
            ci.cancel();
        }
    }
}