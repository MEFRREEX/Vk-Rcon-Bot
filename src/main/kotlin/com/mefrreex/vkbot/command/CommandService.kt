package com.mefrreex.vkbot.command

import com.vk.api.sdk.objects.messages.Message

interface CommandService {

    fun getCommands(): Map<String, Command>

    fun getCommand(name: String): Command?

    fun register(vararg commands: Command) {
        commands.forEach { register(it)}
    }

    fun register(command: Command)
}