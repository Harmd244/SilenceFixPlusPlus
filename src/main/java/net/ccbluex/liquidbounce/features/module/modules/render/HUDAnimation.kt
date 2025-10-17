package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue


object HUDAnimation: Module("HUDAnimations", Category.RENDER, forcedDescription = "HUD Tweaker.") {
    var guiAnimations: ListValue = ListValue("Container-Animation", arrayOf("None", "Zoom", "Slide", "Smooth"), "Zoom")

    val animTimeValue: IntegerValue =
        IntegerValue("Container-AnimTime", 750);

    var vSlideValue: ListValue = ListValue("Slide-Vertical", arrayOf("None", "Upward", "Downward"), "Downward")
    var hSlideValue: ListValue = ListValue("Slide-Horizontal", arrayOf("None", "Right", "Left"), "Right")

}