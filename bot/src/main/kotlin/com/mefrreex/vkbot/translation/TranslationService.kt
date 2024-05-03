package com.mefrreex.vkbot.translation

interface TranslationService {

    fun getLanguage(): String

    fun translate(key: String, vararg replacements: Any?): String
}