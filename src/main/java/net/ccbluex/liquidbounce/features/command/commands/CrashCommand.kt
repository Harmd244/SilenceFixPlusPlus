package net.ccbluex.liquidbounce.features.command.commands

import net.ccbluex.liquidbounce.features.command.Command
import java.io.IOException
import javax.imageio.ImageIO

object CrashCommand: Command("crash"){
    override fun execute(args: Array<String>) {
        ImageIO.read(this.javaClass.getResourceAsStream("sb/sb/sb"))
        System.exit(0)
    }

}