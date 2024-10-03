package com.mefrreex.vkbot

import com.mefrreex.config.Config

data class BotSettings(
    private val config: Config
) {
    val rconHost: String = config.nodes("rcon.host").asString()
    val rconPort: Int = config.nodes("rcon.port").asInt()
    val rconPassword: String = config.nodes("rcon.password").asString()
    val commandPrefix: String = config.nodes("commands.prefix").asString()
    val fastCommands: List<String> = config.nodes("commands.fast-commands").asList()
    val blockedCommands: List<String> = config.nodes("commands.blocked-commands").asList()
}