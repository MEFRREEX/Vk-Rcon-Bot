package com.mefrreex.vkbot.config.defaults

import com.mefrreex.vkbot.config.Config

enum class AllowList(private val key: String) {

    ALLOWED_USERS("allowed-users");

    companion object {

        private val CONFIG = Config("allow_list.yml")

        fun getConfig(): Config {
            return CONFIG
        }
    }

    private fun getValue(key: String): Any? {
        return CONFIG.getList(key)
    }

    @Suppress("UNCHECKED_CAST")
    fun get(): List<Int> {
        return this.getValue(key) as List<Int>
    }
}