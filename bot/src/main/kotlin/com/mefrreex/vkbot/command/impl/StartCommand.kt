package com.mefrreex.vkbot.command.impl

import com.mefrreex.vkbot.Bot
import com.mefrreex.vkbot.command.WordCommand
import com.mefrreex.vkbot.utils.Keyboards
import com.vk.api.sdk.objects.messages.Message

class StartCommand : WordCommand("start", arrayOf("rcon", "начать")) {

    private val bot = Bot.getInstance()
    private val translationService = bot.translationService

    override fun execute(message: Message) {
        if (!bot.isUserAllowed(message.peerId)) {
            bot.sendMessage(message.peerId, translationService.translate("generic-user-not-whitelisted"))
            return
        }
        bot.sendMessage(message.peerId, translationService.translate("command-start-success"), Keyboards.commandsKeyboard())
    }
}