package me.watermark;


import me.naven.Colors;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.render.LBPRenderUtils;
import net.minecraft.client.Minecraft;

public class SilenceFixMark{
    int width;

    public void render() {
        if (Minecraft.getMinecraft().gameSettings.hideGUI || Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            return;
        }

        LiquidBounce liquidBounce = LiquidBounce.INSTANCE;

        int color1 = LBPRenderUtils.getRainbowOpaque(0, 0.5f, 1f, 3000);
        int color2 = LBPRenderUtils.getRainbowOpaque(2000, 0.5f, 1f, 3000);

        LBPRenderUtils.drawGradientRect2(5, 5, width, 6, true, color1, color2);

        LBPRenderUtils.drawRect(5, 6, width, 23, Colors.getColor(0, 0, 0, 60));

        char[] title = "SilenceFix".toCharArray();
        float titleWidth = 0;

        GameFontRenderer font = Fonts.font18;
        GameFontRenderer smallFont = Fonts.font16;

        for (int i = 0; i < title.length; i++) {
            char c = title[i];

            font.drawString(String.valueOf(c), 7.5f + titleWidth, 9.5f, 0x000000);
            font.drawString(String.valueOf(c), 7 + titleWidth, 9, LBPRenderUtils.getRainbowOpaque(i * -100, 0.5f, 1f, 5000));
            titleWidth += font.getCharWidth(c);
        }

        String serverIP = Minecraft.getMinecraft().getCurrentServerData() == null ? "Local" : Minecraft.getMinecraft().getCurrentServerData().serverIP;



        String text = "| " + "b0.3.2" +  " | User: HeShuYou" + " | " + Minecraft.getDebugFPS() + "FPS | " + serverIP;
        smallFont.drawStringWithShadow(text, 9f + titleWidth, 9.5f, Colors.WHITE.c);

        width = (int) (titleWidth + smallFont.getStringWidth(text) + 14);

        if (Minecraft.getMinecraft().gameSettings.hideGUI || Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            return;
        }

        LBPRenderUtils.drawRect(5, 6, width, 23, 0xFFFFFFFF);
    }

}
