package com.mefrreex.vkbot.config.defaults

import com.mefrreex.vkbot.config.Config

enum class AllowList(private val key: String) {

    ALLOWED_USERS("allowed-users");

    companion object {
        val config = Config("allow_list.yml")
    }

    private fun getValue(key: String): Any? {
        return config.getList(key)
    }

    @Suppress("UNCHECKED_CAST")
    fun get(): List<Int> {
        return this.getValue(key) as List<Int>
    }
}