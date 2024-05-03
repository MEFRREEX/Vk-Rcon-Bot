package com.mefrreex.vkbot.command

import com.vk.api.sdk.objects.messages.Message

abstract class SlashCommand(
    name: String,
    description: String = "",
    aliases: Array<String> = emptyArray()
) :
    Command(name, description, aliases) {

    abstract fun execute(message: Message, label: String, args: List<String>)
}