package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render2DEvent
import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot.isBot
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.EntityUtils.getHealth
import net.ccbluex.liquidbounce.utils.EntityUtils.isLookingOnEntities
import net.ccbluex.liquidbounce.utils.EntityUtils.isSelected
import net.ccbluex.liquidbounce.utils.GlowUtils
import net.ccbluex.liquidbounce.utils.RotationUtils.isEntityHeightVisible
import net.ccbluex.liquidbounce.utils.client.EntityLookup
import net.ccbluex.liquidbounce.utils.extensions.component1
import net.ccbluex.liquidbounce.utils.extensions.component2
import net.ccbluex.liquidbounce.utils.extensions.component3
import net.ccbluex.liquidbounce.utils.extensions.interpolatedPosition
import net.ccbluex.liquidbounce.utils.extensions.lastTickPos
import net.ccbluex.liquidbounce.utils.extensions.minus
import net.ccbluex.liquidbounce.utils.extensions.renderPos
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils.disableGlCap
import net.ccbluex.liquidbounce.utils.render.RenderUtils.enableGlCap
import net.ccbluex.liquidbounce.utils.render.RenderUtils.renderNameTag
import net.ccbluex.liquidbounce.utils.render.RenderUtils.resetCaps
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.boolean
import net.ccbluex.liquidbounce.value.int
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11.GL_BLEND
import org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import org.lwjgl.opengl.GL11.GL_ENABLE_BIT
import org.lwjgl.opengl.GL11.GL_LIGHTING
import org.lwjgl.opengl.GL11.GL_LINE_SMOOTH
import org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA
import org.lwjgl.opengl.GL11.GL_SRC_ALPHA
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL11.glBlendFunc
import org.lwjgl.opengl.GL11.glColor4f
import org.lwjgl.opengl.GL11.glDisable
import org.lwjgl.opengl.GL11.glEnable
import org.lwjgl.opengl.GL11.glPopAttrib
import org.lwjgl.opengl.GL11.glPopMatrix
import org.lwjgl.opengl.GL11.glPushAttrib
import org.lwjgl.opengl.GL11.glPushMatrix
import org.lwjgl.opengl.GL11.glRotatef
import org.lwjgl.opengl.GL11.glScalef
import org.lwjgl.opengl.GL11.glTranslated
import java.awt.Color
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.pow

object RiseNameTag: Module("RiseNameTags", Category.RENDER, forcedDescription = "Show Entities Name Tags.") {
    private val renderSelf by boolean("RenderSelf", false)
    private val bot by boolean("Bots", true)

    val maxRenderDistance = object : IntegerValue("MaxRenderDistance", 50, 1..200) {
        override fun onChanged(oldValue: Int, newValue: Int) {
            maxRenderDistanceSq = value.toDouble().pow(2)
        }
    }

    private val onLook by boolean("OnLook", false)
    private val maxAngleDifference by FloatValue("MaxAngleDifference", 90f, 5.0f..90f) { onLook }

    private val thruBlocks by BoolValue("ThruBlocks", true)

    private val shadowcheck by BoolValue("ShadowCheck", true)
    private val shadowStrength by IntegerValue("ShadowStrength", 1, 1..2)

    private var maxRenderDistanceSq = 0.0
        set(value) {
            field = if (value <= 0.0) maxRenderDistance.get().toDouble().pow(2.0) else value
        }

    private val inventoryBackground = ResourceLocation("textures/gui/container/inventory.png")
    private val decimalFormat = DecimalFormat("##0.00", DecimalFormatSymbols(Locale.ENGLISH))
    private val entities by EntityLookup<EntityLivingBase>()
        .filter { bot || !isBot(it) }
        .filter { !onLook || isLookingOnEntities(it, maxAngleDifference.toDouble()) }
        .filter { thruBlocks || isEntityHeightVisible(it) }

    @EventTarget
    fun onRender2D(event: Render2DEvent) {
        if (mc.theWorld == null || mc.thePlayer == null) return

        glPushAttrib(GL_ENABLE_BIT)
        glPushMatrix()

        // Disable lightning and depth test
        glDisable(GL_LIGHTING)
        glDisable(GL_DEPTH_TEST)

        glEnable(GL_LINE_SMOOTH)

        // Enable blend
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        for (entity in entities) {
            val isRenderingSelf =
                entity is EntityPlayerSP && (mc.gameSettings.thirdPersonView != 0 || FreeCam.handleEvents())

            if (!isRenderingSelf || !renderSelf) {
                if (!isSelected(entity, false)) continue
            }

            val name = entity.displayName.unformattedText ?: continue

            val distanceSquared = mc.thePlayer.getDistanceSqToEntity(entity)

            // In case user has FreeCam enabled, we restore the position back to normal,
            // so it renders the name-tag at the player's body position instead of the FreeCam position.
            if (isRenderingSelf) {
                FreeCam.restoreOriginalPosition()
            }

            if (distanceSquared <= maxRenderDistanceSq) {
                renderNameTag(entity, isRenderingSelf)
            }

            if (isRenderingSelf) {
                FreeCam.useModifiedPosition()
            }
        }

        glDisable(GL_BLEND)
        glDisable(GL_LINE_SMOOTH)

        glPopMatrix()
        glPopAttrib()

        // Reset color
        glColor4f(1F, 1F, 1F, 1F)
    }

    private fun renderNameTag(entity: EntityLivingBase, isRenderingSelf: Boolean) {
        val thePlayer = mc.thePlayer ?: return

        // 使用NameTags的渲染方式：直接在3D空间中绘制并使用OpenGL变换
        glPushMatrix()

        // 禁用光照和深度测试
        disableGlCap(GL_LIGHTING, GL_DEPTH_TEST)

        // 启用混合
        enableGlCap(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        // 获取实体信息
        val name = entity.displayName.unformattedText
        val health = getHealth(entity)
        val healthText = health.toInt().toString()//decimalFormat.format(health)

        // 计算位置并应用变换
        val renderManager = mc.renderManager
        val rotateX = if (mc.gameSettings.thirdPersonView == 2) -1.0f else 1.0f

        // 使用插值位置
        val (x, y, z) = entity.interpolatedPosition(entity.lastTickPos) - renderManager.renderPos

        // 转换到实体位置
        glTranslated(x, y + entity.eyeHeight.toDouble() + 0.55, z)

        // 旋转以面对玩家视角
        glRotatef(-renderManager.playerViewY, 0F, 1F, 0F)
        glRotatef(renderManager.playerViewX * rotateX, 1F, 0F, 0F)

        // 计算缩放比例
        val distance = thePlayer.getDistanceToEntity(entity)
        val scale = ((distance / 4F).coerceAtLeast(1F) / 150F) * 2F // 调整缩放因子使标签更清晰
        glScalef(-scale, -scale, scale)

        // 计算文本宽度
        val nameWidth = Fonts.fontHonor35.getStringWidth(name)
        val healthWidth = Fonts.fontHonor35.getStringWidth(healthText)
        val maxWidth = maxOf(nameWidth, healthWidth) + 10 // 10像素的边距
        val height = (Fonts.fontHonor35.FONT_HEIGHT * 2) + 6 // 两行文本的高度，加6像素的边距

        // 绘制圆角矩形背景（使用RenderUtils的工具函数）
        glDisable(GL_TEXTURE_2D)

        // 设置黑色半透明背景颜色
        glColor4f(0f, 0f, 0f, 0.7f)

        if (shadowcheck) {
            GlowUtils.drawGlow(-maxWidth / 2f, -height / 2f,
                maxWidth / 2f, height / 2f, (shadowStrength*13f).toInt(),
                Color(0, 0, 0, 140)
            )
        }

        // 使用RenderUtils的drawRoundedRect函数
        RenderUtils.drawRoundedRect(
            -maxWidth / 2f, -height / 2f,
            maxWidth / 2f, height / 2f,
            Color(0, 0, 0, 178).rgb, // 70%的不透明度
            5f // 圆角半径
        )

        // 恢复纹理并绘制文本
        glEnable(GL_TEXTURE_2D)

        // 绘制名字（浅蓝色）
        Fonts.fontHonor35.drawString(
            name,
            -nameWidth / 2f,
            -height / 2f + 2f,
            Color(103, 216, 230).rgb,
            false // 无阴影
        )

        // 绘制血量（白色）
        Fonts.fontHonor35.drawString(
            healthText,
            -healthWidth / 2f,
            -height / 2f + Fonts.fontHonor35.FONT_HEIGHT + 4f,
            Color.WHITE.rgb,
            false // 无阴影
        )

        // 重置OpenGL状态
        resetCaps()
        glColor4f(1f, 1f, 1f, 1f)

        // 恢复矩阵
        glPopMatrix()
    }
}