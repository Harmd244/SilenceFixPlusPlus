package net.ccbluex.liquidbounce.features.module.modules.hud

import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner


object HUDesigner: Module("HUDesigner", Category.HUD, forcedDescription = "HUD Design", canBeEnabled = false) {
    override fun onEnable() {
        mc.displayGuiScreen(GuiHudDesigner())
    }
}