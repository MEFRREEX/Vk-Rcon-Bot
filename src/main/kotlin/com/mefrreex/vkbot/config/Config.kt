package com.mefrreex.vkbot.config

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.*
import java.nio.charset.StandardCharsets


class Config(private val file: File) {

    constructor(fileName: String) : this(File(fileName))

    private var yaml: Yaml? = null
    private var config: MutableMap<String, Any>? = null

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
        val dumperOptions = DumperOptions();
        dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK;
        this.yaml = Yaml(dumperOptions)
        this.config = yaml!!.load(FileInputStream(file)) as MutableMap<String, Any>?
    }

    fun getString(key: String): String? {
        return getValue(key) as String?
    }

    fun getInt(key: String): Int {
        return getValue(key) as Int
    }

    fun getBoolean(key: String): Boolean {
        return getValue(key) as Boolean
    }

    fun getList(key: String): List<*>? {
        return getValue(key) as List<*>?
    }

    fun getAll(): Map<String, Any>? {
        return config
    }

    fun getValue(key: String): Any? {
        val keys = key.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var value = config as Map<String, Any>?
        for (i in 0 until keys.size - 1) {
            value = value!![keys[i]] as Map<String, Any>?
        }
        return value!![keys[keys.size - 1]]
    }

    fun set(key: String, value: Any) {
        // TODO
    }

    fun save() {
        // TODO
    }

}
