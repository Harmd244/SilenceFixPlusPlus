/*
 * GoldBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/bzym2/GoldBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.misc

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.GameTickEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.utils.misc.RandomUtils.nextFloat
import net.ccbluex.liquidbounce.utils.misc.RandomUtils.nextInt
import net.ccbluex.liquidbounce.utils.misc.RandomUtils.randomString
import net.ccbluex.liquidbounce.utils.timing.MSTimer
import net.ccbluex.liquidbounce.utils.timing.TimeUtils.randomDelay
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.TextValue
import net.ccbluex.liquidbounce.value.boolean


object Spammer : Module("Spammer", Category.MISC, subjective = true, hideModule = false) {
    private val maxDelayValue: IntegerValue = object : IntegerValue("MaxDelay", 1000, 0..5000) {
        override fun onChange(oldValue: Int, newValue: Int) = newValue.coerceAtLeast(minDelay)

        override fun onChanged(oldValue: Int, newValue: Int) {
            delay = randomDelay(minDelay, get())
        }
    }
    private val maxDelay by maxDelayValue

    private val minDelay: Int by object : IntegerValue("MinDelay", 500, 0..5000) {
        override fun onChange(oldValue: Int, newValue: Int) = newValue.coerceAtMost(maxDelay)

        override fun onChanged(oldValue: Int, newValue: Int) {
            delay = randomDelay(get(), maxDelay)
        }

        override fun isSupported() = !maxDelayValue.isMinimal()
    }



    private val msTimer = MSTimer()
    private var delay = randomDelay(minDelay, maxDelay)

    var index:Int = 0
    var xinXin:Int = 0



    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        val ads: MutableList<String?> = ArrayList<String?>()
        val sb = 6000L

        xinXin = 0
        ads.add("@[SilenceFix46.60无需脱盒] 免费最强的客户端 我们也有布吉岛客户端呢 点击右边的代码heshuyou.xyz 来获取！")
        ads.add("@[欣欣公益46.60无需脱盒] 学生党请你打开快手搜索SilenceFix 我们也有布吉岛客户端呢 观看我们的免费最强公益的视频");
        ads.add("@[欣欣公益46.60无需脱盒] 我们是最强自动金苹果且边打边吃 我们也有布吉岛客户端呢 所以来免费获取点击右边的代码heshuyou.xyz");
        ads.add("@[欣欣公益46.60无需脱盒] 全网首发最强的起床跑吃跑喝 我们也有布吉岛客户端呢 免费获取点击右边的代码heshuyou.xyz");
        ads.add("@[欣欣公益46.60无需脱盒] 全网首发无需脱盒 无需托盒工具箱 我们也有布吉岛客户端呢 免费获取点击右边的代码heshuyou.xyz");
        ads.add("@[欣欣公益46.60无需脱盒] 起床空岛同距离同装备的最强对刀客户端 我们也有布吉岛客户端呢 免费获取点击右边的代码heshuyou.xyz");
        ads.add("@[欣欣公益46.60无需脱盒] 职业战争免费的最强自动回血客户端 我们也有布吉岛客户端呢 免费获取点击右边的代码heshuyou.xyz");
        ads.add("@[欣欣提醒无需脱盒] 别用gm的客户端 诈骗且垃记 而且不稳定 被我们碾压的打 我们也有布吉岛客户端呢 免费获取点击右边的代码heshuyou.xyz");
        ads.add("@[欣欣提醒无需脱盒] gm的客户端 诈骗且垃记 而且不稳定 被我们碾压的打的 我们也有布吉岛客户端呢 免费获取点击右边的代码heshuyou.xyz");
        ads.add("@SilenceFix Best The Config Free 1020535222")
        ads.add("@[欣欣公益45.60无需脱盒] ZKM+JNIC是谁的一辈子?")
        if (msTimer.hasTimePassed(sb)) {
            xinXin++

            if (this.xinXin > 100) {
                xinXin = 0
            }

            mc.thePlayer.sendChatMessage(ads.get(index++) + " >" + randomString(nextInt(5, 11)) + "<")


            if (index == ads.size) {
                index = 0;
            }
            msTimer.reset()
            delay = randomDelay(minDelay, maxDelay)
        }
    }

    private fun replace(text: String): String {
        var replacedStr = text

        replaceMap.forEach { (key, valueFunc) ->
            while (key in replacedStr) {
                // You have to replace them one by one, otherwise all parameters like %s would be set to the same random string.
                replacedStr = replacedStr.replaceFirst(key, valueFunc())
            }
        }

        return replacedStr
    }

    private fun randomPlayer() =
        mc.netHandler.playerInfoMap
            .map { playerInfo -> playerInfo.gameProfile.name }
            .filter { name -> name != mc.thePlayer.name }
            .randomOrNull() ?: "none"

    private val replaceMap = mapOf(
        "%f" to { nextFloat().toString() },
        "%i" to { nextInt(0, 10000).toString() },
        "%ss" to { randomString(nextInt(1, 6)) },
        "%s" to { randomString(nextInt(1, 10)) },
        "%ls" to { randomString(nextInt(1, 17)) },
        "%p" to { randomPlayer() }
    )
}