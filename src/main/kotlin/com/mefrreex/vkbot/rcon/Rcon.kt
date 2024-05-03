package com.mefrreex.vkbot.rcon

import com.mefrreex.vkbot.Bot
import net.kronos.rkon.core.ex.AuthenticationException
import net.kronos.rkon.core.Rcon as RconManager
import kotlin.jvm.Throws
import java.io.IOException
import java.util.function.Consumer

object Rcon {

    fun command(name: String, response: Consumer<String>) {
        response.accept(command(name));
    }

    @Throws(IOException::class, AuthenticationException::class)
    fun command(name: String): String {
        val settings = Bot.instance.settings

        val rcon = RconManager(settings.rconHost, settings.rconPort, settings.rconPassword.toByteArray())
        return rcon.command(name.toByteArray(charset("UTF-8")));
    }
}