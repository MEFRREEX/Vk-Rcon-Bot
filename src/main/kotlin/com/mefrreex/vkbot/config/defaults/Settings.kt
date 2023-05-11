package com.mefrreex.vkbot.config.defaults

import com.mefrreex.vkbot.config.Config

enum class Settings(private val key: String) {

    GROUP_ID("vk.groupId"),
    ACCESS_TOKEN("vk.accessToken"),

    RCON_HOST("rcon.host"),
    RCON_PORT("rcon.port"),
    RCON_PASSWORD("rcon.password"),

    COMMAND_PREFIX("commands.prefix"),
    FAST_COMMANDS("commands.fast-commands"),
    BLOCKED_COMMANDS("commands.blocked-commands");

    companion object {

        val config = Config("config.yml")
    }

    private fun getValue(key: String): Any? {
        return config.getValue(key)
    }

    fun string(): String {
        return this.getValue(key) as String
    }

    fun int(): Int {
        return this.getValue(key) as Int
    }

    fun list(): List<*> {
        return this.getValue(key) as List<*>
    }

    fun get(): Any? {
        return this.getValue(key)
    }
}