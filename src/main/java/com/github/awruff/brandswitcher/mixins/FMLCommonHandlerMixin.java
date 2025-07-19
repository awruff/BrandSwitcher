package com.github.awruff.brandswitcher.mixins;

import com.github.awruff.brandswitcher.BrandSwitcherConfig;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FMLCommonHandler.class, remap = false)
public class FMLCommonHandlerMixin {
    @Inject(method = "getModName", at = @At("HEAD"), remap = false, cancellable = true)
    private void getModName(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(BrandSwitcherConfig.INSTANCE.getBrand());
    }
}
