package com.github.awruff.brandswitcher

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main

@Command("@MOD_ID@")
object BrandSwitcherCommand {
    @Main
    fun main() {
        BrandSwitcherConfig.openGui()
    }
}