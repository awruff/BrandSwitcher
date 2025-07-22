package com.github.awruff.brandswitcher

import cc.polyfrost.oneconfig.utils.dsl.mc

object ConfigHelper {
    @JvmStatic
    fun getBrand(): String {
        return if (BrandSwitcherConfig.enabled) {
            BrandSwitcherConfig.brand
        } else {
            "fml,forge"
        }
    }

    @JvmStatic
    fun disableForge(): Boolean {
        return BrandSwitcherConfig.enabled && BrandSwitcherConfig.noForge && !mc.isIntegratedServerRunning && !mc.isSingleplayer
    }
}