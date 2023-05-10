package com.mefrreex.vkbot.utils

import com.mefrreex.vkbot.config.defaults.Settings
import com.vk.api.sdk.objects.messages.*
import java.util.*

object Keyboards {

    @Suppress("UNCHECKED_CAST")
    fun commandsKeyboard(): Keyboard {
        val keyboard = Keyboard()

        val list: MutableList<KeyboardButton> = ArrayList()
        for (command in Settings.FAST_COMMANDS.list() as List<String>) {
            list.add(
                KeyboardButton()
                    .setColor(KeyboardButtonColor.POSITIVE)
                    .setAction(
                        KeyboardButtonAction()
                            .setLabel(Settings.COMMAND_PREFIX.string() + command)
                            .setType(TemplateActionTypeNames.TEXT)
                    )
            )
        }
        keyboard.buttons = listOf<List<KeyboardButton>>(list)
        keyboard.inline = true
        return keyboard
    }
}