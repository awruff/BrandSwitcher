package com.github.awruff.brandswitcher.mixins.accessors;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(C17PacketCustomPayload.class)
public interface IC17PacketCustomPayload {
    @Accessor("data")
    void setData(PacketBuffer buf);
}
