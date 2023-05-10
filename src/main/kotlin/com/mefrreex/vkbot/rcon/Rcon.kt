package com.mefrreex.vkbot.rcon

import com.mefrreex.vkbot.config.defaults.Settings
import net.kronos.rkon.core.ex.AuthenticationException
import net.kronos.rkon.core.Rcon as RconManager
import kotlin.jvm.Throws
import java.io.IOException
import java.util.function.Consumer

class Rcon {

    companion object {

        fun command(name: String, response: Consumer<String>) {
            response.accept(command(name));
        }

        @Throws(IOException::class, AuthenticationException::class)
        fun command(name: String): String {
            val rcon = RconManager(Settings.RCON_HOST.string(), Settings.RCON_PORT.int(), Settings.RCON_PASSWORD.string().toByteArray())
            return rcon.command(name.toByteArray(charset("UTF-8")));
        }
    }
}