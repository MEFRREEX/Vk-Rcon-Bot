package com.mefrreex.vkbot

import com.mefrreex.vkbot.config.ConfigManager
import com.mefrreex.vkbot.logger.Logger
import java.io.File

val logger = Logger.getInstance()
val configManager = ConfigManager()

var server: Server? = null

class Bootstrap {

}

fun main() {
    logger.info("Starting the bot...")
    val bootstrap = Bootstrap();

    val resources = listOf(File("config.yml"), File("allow_list.yml"), File("messages.yml"))
    if (!resources.all { it.exists() }) {
        logger.info("Saving resources.")
        configManager.saveResource("allow_list.yml")
        configManager.saveResource("messages.yml")
        configManager.saveResource("config.yml")
    }

    server = Server(bootstrap)
    server!!.start()

    logger.info("Bot is started!")
}
