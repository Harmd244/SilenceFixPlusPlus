package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;

import static net.ccbluex.liquidbounce.utils.client.MinecraftInstance.mc;

@Mixin(GuiLanguage.class)
public class MixinGuiLang extends GuiScreen {
    @Shadow
    @Final
    private GameSettings game_settings_3;

    @Shadow
    protected GuiScreen parentScreen;




    @Final
    private Minecraft minecraftIns = FMLClientHandler.instance().getClient();

    @Overwrite
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
        if (p_actionPerformed_1_.enabled) {
            switch (p_actionPerformed_1_.id) {
                case 5:
                    break;
                case 6:
                    mc.displayGuiScreen(this.parentScreen);
                    break;
                case 100:
                    if (p_actionPerformed_1_ instanceof GuiOptionButton) {
                        this.game_settings_3.setOptionValue(((GuiOptionButton)p_actionPerformed_1_).returnEnumOptions(), 1);
                        p_actionPerformed_1_.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
                        ScaledResolution lvt_2_1_ = new ScaledResolution(this.mc);
                        int lvt_3_1_ = lvt_2_1_.getScaledWidth();
                        int lvt_4_1_ = lvt_2_1_.getScaledHeight();
                        this.setWorldAndResolution(this.mc, lvt_3_1_, lvt_4_1_);

                        if (fontRendererObj.getUnicodeFlag() == true) {
                            minecraftIns.fontRendererObj.setUnicodeFlag(false);
                        } else {
                            minecraftIns.fontRendererObj.setUnicodeFlag(true);
                        }

                        if (fontRendererObj.getBidiFlag() == true) {
                            minecraftIns.fontRendererObj.setBidiFlag(false);
                        } else {
                            minecraftIns.fontRendererObj.setBidiFlag(true);
                        }
                    }
                    break;

            }

        }
    }

}
