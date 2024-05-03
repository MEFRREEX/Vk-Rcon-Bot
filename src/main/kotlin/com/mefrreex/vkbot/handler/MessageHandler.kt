package com.mefrreex.vkbot.handler

import com.mefrreex.vkbot.Bot
import com.mefrreex.vkbot.rcon.RconClient
import com.mefrreex.vkbot.rcon.exception.ConnectionException
import com.mefrreex.vkbot.translation.TranslationService
import com.mefrreex.vkbot.utils.Keyboards
import com.mefrreex.vkbot.utils.TextFormat
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.events.Events
import com.vk.api.sdk.events.longpoll.GroupLongPollApi
import com.vk.api.sdk.objects.callback.messages.CallbackMessage
import com.vk.api.sdk.objects.messages.Message

class MessageHandler(
    client: VkApiClient,
    actor: GroupActor,
    waitTime: Int,
    private val bot: Bot
) : GroupLongPollApi(client, actor, waitTime) {

    private val translationService = TranslationService.getInstance()

    private fun onMessageNew(message: Message) {
        val text = message.text.lowercase()

        when {

            text == "начать" || text == "start" || text == "rcon" -> {
                if (!bot.isUserAllowed(message.peerId)) {
                    bot.sendMessage(message.peerId, translationService.translate("generic-user-not-whitelisted"))
                    return
                }
                bot.sendMessage(message.peerId, translationService.translate("command-start-success"), Keyboards.commandsKeyboard())
            }

            text.startsWith(bot.settings.commandPrefix) -> {

                val command = text.substring(bot.settings.commandPrefix.length, text.length)
                val commandName = command.split("\\s+".toRegex())[0].removePrefix("/")

                when(command) {

                    "getid" -> {
                        bot.sendMessage(message.peerId, translationService.translate("command-userid-success", message.fromId))
                    }

                    else -> {

                        if (!bot.isUserAllowed(message.peerId)) {
                            return
                        }

                        if (bot.settings.blockedCommands.contains(commandName)) {
                            bot.sendMessage(message.peerId, translationService.translate("generic-command-blocked", command))
                            bot.logger.warn("User ${message.peerId} tried to use the blocked command ${TextFormat.RED}/$command${TextFormat.RESET}.")
                            return
                        }

                        val rcon = RconClient(bot.settings.rconHost, bot.settings.rconPort)

                        try {
                            rcon.connect(bot.settings.rconPassword)
                        } catch (e: Exception) {
                            if (e is ConnectionException) {
                                bot.sendMessage(message.peerId, translationService.translate("rcon-failed-to-connect"))
                                bot.logger.error("Unhandled exception when trying to connect to RCON:", e)
                            } else {
                                e.printStackTrace()
                                bot.sendMessage(message.peerId, translationService.translate("rcon-failed-to-authenticate"))
                                bot.logger.error("Unhandled exception on RCON authentication attempt:", e)
                            }
                        }

                        rcon.sendCommand(command).let {
                            if (it.length <= 4000) {
                                bot.sendMessage(message.peerId, if (it.isNotBlank()) {
                                    translationService.translate("rcon-command-sent", it)
                                } else {
                                    translationService.translate("rcon-response-empty")
                                })
                            } else {
                                bot.sendMessage(message.peerId, translationService.translate("rcon-command-response-too-long"))
                            }
                            bot.logger.info("User ${message.fromId} used the command ${TextFormat.YELLOW}/$command${TextFormat.RESET}")
                        }
                    }
                }
            }
        }
    }

    override fun parse(message: CallbackMessage): String? {
        if (message.type == Events.MESSAGE_NEW) {
            gson.fromJson(message.getObject(), MessageFix::class.java).message?.let {
                this.onMessageNew(it)
            }
            return "OK"
        }
        return super.parse(message)
    }

}