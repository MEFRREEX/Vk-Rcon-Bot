package com.mefrreex.vkbot.translation

import com.mefrreex.config.Config
import com.mefrreex.config.YamlConfig
import com.mefrreex.vkbot.utils.ConfigHelper
import java.io.File

class TranslationServiceImpl(config: Config) : TranslationService {

    private val language: String = config.node("language").asString()
    private val languages: Map<String, String>

    init {
        ConfigHelper.saveResource("lang/$language.yml")
        val languageConfig = YamlConfig(File("lang", "$language.yml"))
        this.languages = languageConfig.root().asMap()
    }

    /**
     * Get selected language
     */
    override fun getLanguage(): String {
        return language
    }

    /**
     * Get translation by key
     * @param key Message key
     * @param replacements Message parameters
     */
    override fun translate(key: String, vararg replacements: Any?): String {
        var translated = languages[key] ?: key

        for ((i, replacement) in replacements.withIndex()) {
            translated = translated.replace("[$i]", replacement.toString())
        }

        return translated
    }
}