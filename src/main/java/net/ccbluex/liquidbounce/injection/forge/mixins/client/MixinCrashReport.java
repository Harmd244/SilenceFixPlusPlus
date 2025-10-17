package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import me.utils.CrashStringUtils;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.fml.common.asm.transformers.BlamingTransformer;
import net.minecraftforge.fml.relauncher.CoreModManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mixin(CrashReport.class)
public abstract class MixinCrashReport {


    @Shadow
    @Final
    private String description;

    @Shadow
    public abstract String getCauseStackTraceOrString();

    @Shadow
    public abstract void getSectionsInStringBuilder(StringBuilder p_getSectionsInStringBuilder_1_);


    @Overwrite
    private static String getWittyComment() {


        try {
            return CrashStringUtils.getCrashString();
        } catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }

    @Overwrite
    public String getCompleteReport() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- SilenceFix Crash Report ----\n");
        BlamingTransformer.onCrash(stringbuilder);
        CoreModManager.onCrash(stringbuilder);
        stringbuilder.append("// ");
        stringbuilder.append(getWittyComment());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time: ");
        stringbuilder.append((new SimpleDateFormat()).format(new Date()));
        stringbuilder.append("\n");
        stringbuilder.append("Description: ");
        stringbuilder.append(this.description);
        stringbuilder.append("\n\n");
        stringbuilder.append(this.getCauseStackTraceOrString());
        stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

        for(int i = 0; i < 87; ++i) {
            stringbuilder.append("-");
        }

        stringbuilder.append("\n\n");
        this.getSectionsInStringBuilder(stringbuilder);
        return stringbuilder.toString();
    }
}
