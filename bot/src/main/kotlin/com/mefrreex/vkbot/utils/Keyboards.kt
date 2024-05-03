package com.mefrreex.vkbot.utils

import com.mefrreex.vkbot.Bot
import com.vk.api.sdk.objects.messages.*

object Keyboards {

    private val settings = Bot.getInstance().settings

    fun commandsKeyboard(): Keyboard {
        return Keyboard()
            .setInline(true)
            .setButtons(listOf(
                settings.fastCommands.map { command ->
                    KeyboardButton()
                        .setColor(KeyboardButtonColor.POSITIVE)
                        .setAction(
                            KeyboardButtonAction()
                                .setLabel(settings.commandPrefix + command)
                                .setType(TemplateActionTypeNames.TEXT)
                        )
                }
            ))
    }
}
