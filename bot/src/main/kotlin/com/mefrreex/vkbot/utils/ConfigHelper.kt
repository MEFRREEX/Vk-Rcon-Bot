package com.mefrreex.vkbot.utils

import com.mefrreex.config.Config
import com.mefrreex.config.ConfigType
import java.io.File

object ConfigHelper {

    const val CONFIG = "config.yml"
    const val ALLOW_LIST = "allow_list.yml"

    private val configs: MutableMap<String, Config> = mutableMapOf()

    fun getConfig(name: String): Config? {
        return configs[name]
    }

    fun getConfigNotNull(name: String): Config {
        return configs[name]!!
    }

    fun loadConfig(name: String) {
        configs[name] = ConfigType.DETECT.createOf(name);
    }

    fun saveResource(target: String, output: String = target, replace: Boolean = false) {
        val resourceStream = javaClass.classLoader.getResourceAsStream(target)
        val outputFile = File(output)
        if (resourceStream != null && (replace || !outputFile.exists())) {
            outputFile.parentFile.mkdirs()
            outputFile.createNewFile()
            outputFile.outputStream().use { resourceStream.copyTo(it) }
        }
    }
}