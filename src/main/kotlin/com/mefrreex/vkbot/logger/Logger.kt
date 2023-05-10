package com.mefrreex.vkbot.logger

import com.mefrreex.vkbot.utils.TextFormat
import java.text.SimpleDateFormat
import java.util.*


class Logger {

    private val DATE_FORMAT = SimpleDateFormat("HH:mm:ss.SSS")

    fun info(message: String) {
        log(TextFormat.BLUE + "INFO" + TextFormat.RESET, message)
    }

    fun warn(message: String) {
        log(TextFormat.RED + "WARN" + TextFormat.RESET, message)
    }

    fun error(message: String) {
        log(TextFormat.RED + "ERROR" + TextFormat.RESET, message)
    }

    fun error(message: String, throwable: Throwable) {
        log(TextFormat.RED + "ERROR" + TextFormat.RESET, message, throwable)
    }

    fun debug(message: String) {
        log(TextFormat.BLUE + "DEBUG" + TextFormat.RESET, message)
    }

    private fun log(level: String, message: String) {
        log(level, message, null)
    }

    private fun log(level: String, message: String, throwable: Throwable?) {
        val date = DATE_FORMAT.format(Date())
        println("${TextFormat.CYAN}$date ${TextFormat.WHITE}[$level] $message")
        throwable?.printStackTrace()
    }

    companion object {

        private val instance = Logger()

        fun getInstance(): Logger {
            return instance
        }
    }
}