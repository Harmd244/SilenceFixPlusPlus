package net.ccbluex.liquidbounce.features.module.modules.hud

import me.naven.Colors
import me.watermark.SilenceFixMark
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render2DEvent
import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.LBPRenderUtils
import net.minecraft.client.Minecraft

object NavenMark: Module("NavenWaterMark", category = Category.HUD, forcedDescription = "The Naven WaterMark.") {

    @EventTarget
    fun onRender2D(e: Render2DEvent) {
        SilenceFixMark().render()
    }
}