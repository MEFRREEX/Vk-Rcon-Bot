package com.mefrreex.vkbot

import com.mefrreex.vkbot.config.ConfigManager
import com.mefrreex.vkbot.logger.Logger
import java.io.File

val logger = Logger.instance
val configManager = ConfigManager()

var server: Server? = null

class Bootstrap {

}

fun main() {
    logger.info("Starting the bot...")
    val bootstrap = Bootstrap();

    val resources = listOf(File("config.yml"), File("allow_list.yml"), File("messages.yml"))
    resources.forEach {
        if (!it.exists()) {
            configManager.saveResource(it.name)
            logger.info("Resource `${it.name}` saved.")
        }
    }

    server = Server(bootstrap)
    server!!.start()

    logger.info("Bot is started!")
}
