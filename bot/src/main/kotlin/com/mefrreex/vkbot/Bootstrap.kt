package com.mefrreex.vkbot

import com.mefrreex.vkbot.logger.Logger
import com.mefrreex.vkbot.utils.ConfigHelper
import java.io.File

val logger = Logger()

fun main() {
    logger.info("Starting the bot...")

    val resources = listOf(ConfigHelper.CONFIG, ConfigHelper.ALLOW_LIST)
    resources.forEach {
        ConfigHelper.loadConfig(it)
        if (!File(it).exists()) {
            ConfigHelper.saveResource(it)
            logger.info("Resource $it saved")
        }
    }

    val config = ConfigHelper.getConfig(ConfigHelper.CONFIG)
    if (config == null) {
        logger.error("Failed to start bot. File config.yml not found")
        return
    }

    try {
        val token = config.nodes("vk.accessToken").asString()
        if (token == "token") {
            logger.warn("Specify a valid bot token in the vk.accessToken parameter")
            return
        }

        Bot(config, config.nodes("vk.groupId").asInt(), token)
    } catch (e: Exception) {
        logger.error("Failed to start the bot", e)
        return
    }

    logger.info("Bot is started!")
}
