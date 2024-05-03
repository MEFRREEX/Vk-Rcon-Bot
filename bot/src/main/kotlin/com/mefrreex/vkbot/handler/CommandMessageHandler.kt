package com.mefrreex.vkbot.handler

import com.mefrreex.vkbot.Bot
import com.mefrreex.vkbot.command.parser.CommandParser
import com.mefrreex.vkbot.command.SlashCommand
import com.mefrreex.vkbot.command.WordCommand
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.objects.messages.Message

class CommandMessageHandler(
    client: VkApiClient,
    actor: GroupActor,
    waitTime: Int,
    private val bot: Bot
) : MessageHandler(client, actor, waitTime) {

    private val commandService = bot.commandService

    override fun onMessageNew(message: Message) {
        val wordCommand = commandService.getCommand(message.text)
        if (wordCommand is WordCommand) {
            wordCommand.execute(message)
            return
        }

        if (!CommandParser.canParse(message.text)) {
            return
        }

        try {
            val data = CommandParser.parse(message.text)
            val command = commandService.getCommand(data!!.name)

            if (command is SlashCommand) {
                command.execute(message, data.name, data.args)
            }
        } catch (e: Exception) {
            bot.sendMessage(message.peerId, e.message!!)
        }
    }
}