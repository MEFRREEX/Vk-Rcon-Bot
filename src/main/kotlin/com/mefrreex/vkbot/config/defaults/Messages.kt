package com.mefrreex.vkbot.config.defaults

import com.mefrreex.vkbot.config.Config

enum class Messages(private val key: String) {

    USER_ID("user_id"),
    USER_NOT_WHITELISTED("user_not_whitelisted"),

    START("start"),

    RCON("rcon"),
    RCON_WITH_COMMANDS("rcon_with_commands"),

    COMMAND_BLOCKED("command_blocked"),
    FAILED_TO_CONNECT("failed_to_connect"),
    FAILED_TO_AUTHENTICATE("failed_to_authenticate"),

    COMMAND_SENDED("command_sended"),
    RESPONSE_NULL("response_null");

    companion object {

        private val CONFIG = Config("messages.yml")

        fun getConfig(): Config {
            return CONFIG
        }
    }

    fun get(): String {
        return CONFIG.getString(key)!!
    }
}