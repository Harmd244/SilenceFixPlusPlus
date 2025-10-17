package me.utils

import net.ccbluex.liquidbounce.utils.ClassUtils

class NeteaseUtils {
    fun hasNetease(): Boolean {
        return ClassUtils.hasClass("com.netease.mc.mod.departmod.DepartMod")
    }
}