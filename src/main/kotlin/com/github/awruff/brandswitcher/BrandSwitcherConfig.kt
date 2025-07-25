package com.github.awruff.brandswitcher

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Dropdown
import cc.polyfrost.oneconfig.config.annotations.Info
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.annotations.Text
import cc.polyfrost.oneconfig.config.data.InfoType
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.utils.commands.CommandManager

object BrandSwitcherConfig : Config(
    Mod("@MOD_NAME@", ModType.UTIL_QOL, "/assets/brandswitcher/icon.svg"),
    "@MOD_ID@.json"
) {
    @Info(
        text = "You must rejoin for changes to take affect!",
        type = InfoType.WARNING,
        size = 1
    )
    var _warning: Boolean = false

    @Dropdown(
        name = "Presets",
        options = ["Vanilla", "Forge", "Lunar Client", "Badlion Client", "Custom"]
    )
    var preset: Int = 1

    @Text(
        name = "Brand"
    )
    var brand: String = "fml,forge"

    @Switch(
        name = "Disable Forge",
        description = "Disable's Forge's packets",
        category = "Extra"
    )
    var noForge = false

    init {
        initialize()

        CommandManager.register(BrandSwitcherCommand)

        addListener("preset") {
            when (preset) {
                0 -> brand = "vanilla"
                1 -> brand = "fml,forge"
                2 -> brand = "lunarclient:v2.20.4-2529"
                3 -> brand = "badlion"
            }
        }

        addListener("brand") {
            if (preset != 4) preset = 4
        }
    }
}