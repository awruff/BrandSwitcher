package com.github.awruff.brandswitcher

object ConfigHelper {
    @JvmStatic
    fun getBrand(): String {
        if (BrandSwitcherConfig.enabled) {
            return BrandSwitcherConfig.brand
        } else {
            return "fml,forge"
        }
    }

    @JvmStatic
    fun disableForge(): Boolean {
        return BrandSwitcherConfig.enabled && BrandSwitcherConfig.noForge
    }
}