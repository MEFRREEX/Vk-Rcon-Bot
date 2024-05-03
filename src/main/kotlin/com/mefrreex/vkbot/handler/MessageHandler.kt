package com.mefrreex.vkbot.handler

import com.mefrreex.vkbot.Bot
import com.mefrreex.vkbot.rcon.Rcon
import com.mefrreex.vkbot.utils.Keyboards
import com.mefrreex.vkbot.utils.TextFormat
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.events.Events
import com.vk.api.sdk.events.longpoll.GroupLongPollApi
import com.vk.api.sdk.objects.callback.messages.CallbackMessage
import com.vk.api.sdk.objects.messages.Message
import net.kronos.rkon.core.ex.AuthenticationException
import java.io.IOException

class MessageHandler(
    client: VkApiClient,
    actor: GroupActor,
    waitTime: Int,
    private val bot: Bot
) : GroupLongPollApi(client, actor, waitTime) {

    private fun onMessageNew(message: Message) {
        val text = message.text.lowercase()

        when {

            text == "начать" || text == "start" || text == "rcon" -> {
                if (!bot.isUserAllowed(message.peerId)) {
                    bot.sendMessage(message.peerId, bot.getMessage("user_not_whitelisted"))
                    return
                }
                bot.sendMessage(message.peerId, bot.getMessage("start"), Keyboards.commandsKeyboard())
            }

            text.startsWith(bot.settings.commandPrefix) -> {

                val command = text.substring(bot.settings.commandPrefix.length, text.length)
                val commandName = command.split("\\s+".toRegex())[0].removePrefix("/")

                when(command) {

                    "getid" -> {
                        bot.sendMessage(message.peerId, bot.getMessage("user_id").format(message.fromId))
                    }

                    else -> {

                        if (!bot.isUserAllowed(message.peerId)) {
                            return
                        }

                        if (bot.settings.blockedCommands.contains(commandName)) {
                            bot.sendMessage(message.peerId, bot.getMessage("command_blocked").format(command))
                            bot.logger.warn("User ${message.peerId} tried to use the blocked command ${TextFormat.RED}`$command`${TextFormat.RESET}.")
                            return
                        }

                        try {
                            Rcon.command(command, response = {
                                if (it.length < 4000) {
                                    bot.sendMessage(message.peerId, if (it.isNotBlank()) {
                                        bot.getMessage("command_sent").format(it)
                                    } else {
                                        bot.getMessage("response_null")
                                    })
                                } else {
                                    bot.sendMessage(message.peerId, bot.getMessage("command_response_too_long"))
                                }
                                bot.logger.info("Used ${TextFormat.YELLOW}`$command`${TextFormat.RESET} command by user ${message.fromId}")
                            })

                        } catch (e: IOException) {
                            bot.sendMessage(message.peerId, bot.getMessage("failed_to_connect"))
                            bot.logger.error("Unhandled exception when trying to connect to RCON:", e)

                        } catch (e: AuthenticationException) {
                            bot.sendMessage(message.peerId, bot.getMessage("failed_to_authenticate"))
                            bot.logger.error("Unhandled exception on RCON authentication attempt:", e)
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