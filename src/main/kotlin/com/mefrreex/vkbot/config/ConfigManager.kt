package com.mefrreex.vkbot.config

import java.nio.file.Paths.*
import java.io.*


class ConfigManager {

    fun saveResource(target: String) {

        val currentPath = get("").toAbsolutePath()
        val configPath = "/$target"
        val file = File(currentPath.toString(), target)

        if (!file.exists()) {
            try {
                val inputStream: InputStream? = javaClass.getResourceAsStream(configPath)
                val outputStream: OutputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                inputStream.close()
                outputStream.close()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}