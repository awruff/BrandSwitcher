package com.github.awruff.brandswitcher.mixins;

import com.github.awruff.brandswitcher.ConfigHelper;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClientBrandRetriever.class, remap = false)
public class ClientBrandRetrieverMixin {
    @Inject(method = "getClientModName", at = @At("HEAD"), cancellable = true)
    private static void getBrand(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(ConfigHelper.getBrand());
    }
}
