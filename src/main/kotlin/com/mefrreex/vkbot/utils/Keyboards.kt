package com.mefrreex.vkbot.utils

import com.mefrreex.vkbot.Bot
import com.vk.api.sdk.objects.messages.*
import java.util.*

object Keyboards {

    @Suppress("UNCHECKED_CAST")
    fun commandsKeyboard(): Keyboard {
        val bot = Bot.instance
        val keyboard = Keyboard()

        val list: MutableList<KeyboardButton> = ArrayList()
        for (command in bot.settings.fastCommands) {
            list.add(
                KeyboardButton()
                    .setColor(KeyboardButtonColor.POSITIVE)
                    .setAction(
                        KeyboardButtonAction()
                            .setLabel(bot.settings.commandPrefix + command)
                            .setType(TemplateActionTypeNames.TEXT)
                    )
            )
        }
        keyboard.buttons = listOf<List<KeyboardButton>>(list)
        keyboard.inline = true
        return keyboard
    }
}