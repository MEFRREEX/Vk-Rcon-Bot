package com.mefrreex.vkbot

import com.mefrreex.vkbot.logger.Logger
import com.mefrreex.vkbot.utils.ConfigHelper
import java.io.File


val logger = Logger.instance

fun main() {
    logger.info("Starting the bot...")

    val resources = listOf("config.yml", "allow_list.yml", "messages.yml")
    resources.forEach {
        ConfigHelper.loadConfig(it)
        if (!File(it).exists()) {
            ConfigHelper.saveResource(it)
            logger.info("Resource $it saved.")
        }
    }

    val config = ConfigHelper.getConfig(ConfigHelper.CONFIG)
    if (config == null) {
        logger.error("Failed to start bot. File config.yml not found.")
        return
    }

    try {
        val token = config.nodes("vk.accessToken").asString()
        if (token == "token") {
            logger.warn("Specify a valid bot token in the vk.accessToken parameter")
            return
        }

        Bot(config.node("vk.groupId").asInt(), token)
    } catch (e: Exception) {
        logger.error("Failed to start the bot", e)
        return
    }

    logger.info("Bot is started!")
}
