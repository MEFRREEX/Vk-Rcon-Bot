package com.mefrreex.vkbot.command.impl

import com.mefrreex.vkbot.Bot
import com.mefrreex.vkbot.command.SlashCommand
import com.vk.api.sdk.objects.messages.Message

class GetIDCommand : SlashCommand("getid") {

    private val bot = Bot.getInstance()
    private val translationService = bot.translationService

    override fun execute(message: Message, label: String, args: List<String>) {
        bot.sendMessage(message.peerId, translationService.translate("command-userid-success", message.fromId))
    }
}