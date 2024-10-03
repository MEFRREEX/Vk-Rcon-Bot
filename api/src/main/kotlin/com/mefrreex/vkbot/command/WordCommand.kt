package com.mefrreex.vkbot.command

import com.vk.api.sdk.objects.messages.Message

abstract class WordCommand(
    name: String,
    aliases: Array<String> = emptyArray()
) :
    Command(name, "", aliases)
{
    abstract fun execute(message: Message)
}