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
        val keys = key.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var current = config
        for (i in 0 until keys.size - 1) {
            val next = current!![keys[i]] as MutableMap<String, Any>?
            if (next == null) {
                val newMap = mutableMapOf<String, Any>()
                current[keys[i]] = newMap
                current = newMap
            } else {
                current = next
            }
        }
        current!![keys[keys.size - 1]] = value
    }

    fun save() {
        val content = yaml!!.dump(config)
        val lines = mutableListOf<String>()

        // Считываем старое содержимое файла
        if (file.exists()) {
            file.forEachLine { line ->
                lines.add(line)
            }
        }

        // Обновляем содержимое файла
        val writer = file.bufferedWriter()
        for (line in lines) {
            if (line.trim().startsWith("#")) {
                writer.write(line)
                writer.newLine()
            }
        }
        writer.write(content)
        writer.flush()
        writer.close()
    }

}
