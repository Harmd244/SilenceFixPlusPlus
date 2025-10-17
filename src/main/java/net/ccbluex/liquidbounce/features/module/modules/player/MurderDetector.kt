package net.ccbluex.liquidbounce.features.module.modules.player

import net.ccbluex.liquidbounce.event.EventState
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.MotionEvent
import net.ccbluex.liquidbounce.event.Render2DEvent
import net.ccbluex.liquidbounce.event.WorldEvent
import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.modules.hud.WaterMark
import net.ccbluex.liquidbounce.script.api.global.Chat
import net.ccbluex.liquidbounce.ui.client.hud.HUD.addNotification
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notifications
import net.ccbluex.liquidbounce.ui.font.Fonts.minecraftFont
import net.ccbluex.liquidbounce.value.BoolValue
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import java.awt.Color

object MurderDetector : Module("MurderDetector", Category.PLAYER, gameDetecting = false) {
    private val showText by BoolValue("ShowText", true)
    private val chatValue by BoolValue("Chat", true)
    private val notifyValue by BoolValue("Notification", true)

    private var murder1: EntityPlayer? = null
    private var murder2: EntityPlayer? = null

    private val murderItems = mutableListOf(
        267,  // Items.iron_sword,
        272,  // Items.stone_sword,
        256,  // Items.iron_shovel,
        280,  // Items.stick,
        271,  // Items.wooden_axe,
        268,  // Items.wooden_sword,
        273,  // Items.stone_shovel,
        369,  // Items.blaze_rod,
        277,  // Items.diamond_shovel,
        359,  // Items.shears,
        400,  // Items.pumpkin_pie,
        285,  // Items.golden_pickaxe,
        398,  // Items.carrot_on_a_stick,
        357,  // Items.cookie,
        279,  // Items.diamond_axe,
        283,  // Items.golden_sword,
        276,  // Items.diamond_sword,
        293,  // Items.diamond_hoe,
        421,  // Items.name_tag,
        333,  // Items.boat,
        409,  // Items.prismarine_shard,
        349,  // Items.fish,
        364,  // Items.cooked_beef,
        382,  // Items.speckled_melon,
        351,  // Items.dye,
        340,  // Items.book,
        406,  // Items.quartz,
        396,  // Items.golden_carrot,
        260,  // Items.apple,
        2258, // Items.record_blocks
        76,   // Blocks.redstone_torch,
        32,   // Blocks.deadbush,
        19,   // Blocks.sponge,
        122,  // Blocks.dragon_egg,
        175,  // Blocks.double_plant,
        405,  // Blocks.nether_brick,
        130   // Blocks.ender_chest
    )

    override fun onDisable() {
        murder1 = null
        murder2 = null
    }

    @EventTarget
    fun onWorld(event: WorldEvent) {
        murder1 = null
        murder2 = null
    }

    @EventTarget
    fun onMotion(event: MotionEvent) {
        if (event.eventState == EventState.PRE) {
            for (player in mc.theWorld.playerEntities) {
                if (mc.thePlayer.ticksExisted % 2 == 0) return
                if (player.heldItem != null && (player.heldItem.displayName.contains(
                        "Knife",
                        ignoreCase = true
                    ) || murderItems.contains(Item.getIdFromItem(player.heldItem.item)))
                ) {
                    if (murder1 == null) {
                        if (chatValue)
                            Chat.print("§e" + player.name + "§r is Murderer!")
                        if (notifyValue)
                            WaterMark.showIconNotification("Murderer 1 Detected!", "${player.name} is Murderer!",
                                ResourceLocation("liquidbounce/notification/enabled.png"), 1000)
                            addNotification(
                                Notification(
                                    "${player.name} is Murderer!",
                                    2000F,
                                    "Murderer Detected!",
                                    Notifications.SeverityType.INFO
                                )
                            )
                        murder1 = player
                        return
                    }
                    if (murder2 == null && player != murder1) {
                        if (chatValue)
                            Chat.print("§e" + player.name + "§r is Murderer!")
                        if (notifyValue)
                            WaterMark.showIconNotification("Murderer 2 Detected!", "${player.name} is Murderer!",
                                ResourceLocation("liquidbounce/notifcation/enabled.png"), 1000)
                            addNotification(
                                Notification(
                                    "${player.name} is Murderer!",
                                    2000F,
                                    "Murderer Detected!",
                                    Notifications.SeverityType.INFO
                                )
                            )
                        murder2 = player
                    }
                }
            }
        }
    }

    @EventTarget
    fun onRender2D(event: Render2DEvent) {
        val sc = ScaledResolution(mc)
        if (showText) {
            minecraftFont.drawString(
                if (murder1 != null) "Murderer1: §e" + murder1?.name else "Murderer1: §cNone",
                sc.scaledWidth / 2F - minecraftFont.getStringWidth(if (murder1 != null) "Murderer1: §e" + murder1?.name else "Murderer1: §cNone") / 2F,
                66.5F,
                Color(255, 255, 255).rgb,
                true
            )
            minecraftFont.drawString(
                if (murder2 != null) "Murderer2: §e" + murder2?.name else "Murderer2: §cNone",
                sc.scaledWidth / 2F - minecraftFont.getStringWidth(if (murder2 != null) "Murderer2: §e" + murder2?.name else "Murderer2: §cNone") / 2F,
                77.5F,
                Color(255, 255, 255).rgb,
                true
            )
        }
    }
}