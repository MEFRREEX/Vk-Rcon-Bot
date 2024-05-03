package com.mefrreex.vkbot.command.parser

import com.mefrreex.vkbot.command.data.CommandData


object CommandParser {

    fun canParse(command: String): Boolean {
        return command.startsWith("/")
    }

    fun parse(command: String?): CommandData? {
        var command = command
        if (command == null || command.isEmpty()) {
            return null
        }

        if (command.startsWith("/")) {
            command = command.substring(1)
        }

        val arguments = parseArguments(command)
        if (arguments.isEmpty()) {
            return null
        }

        val name = arguments[0]
        val args = arguments.subList(1, arguments.size)

        return CommandData(name, args)
    }

    fun parseArguments(command: String?): List<String> {
        if (command == null || command.isEmpty()) {
            return emptyList()
        }

        val arguments: MutableList<String> = ArrayList()

        var quotes = false

        var currentArgument = StringBuilder()
        for (i in 0 until command.length) {
            val c = command[i]
            if (c == ' ') {
                if (quotes) {
                    currentArgument.append(c)
                } else {
                    if (!currentArgument.isEmpty()) {
                        arguments.add(currentArgument.toString())
                        currentArgument = StringBuilder()
                    }
                }
            } else if (c == '"') {
                quotes = !quotes
            } else {
                currentArgument.append(c)
            }
        }

        if (quotes) {
            throw RuntimeException()
        }

        if (!currentArgument.isEmpty()) {
            arguments.add(currentArgument.toString())
        }

        return arguments
    }
}