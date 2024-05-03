package com.mefrreex.vkbot.translation

import com.mefrreex.vkbot.Bot

interface TranslationService {

    fun getLanguage(): String

    fun translate(key: String, vararg replacements: Any?): String

    companion object {
        fun getInstance(): TranslationService {
            return Bot.getInstance().translationService
        }
    }
}