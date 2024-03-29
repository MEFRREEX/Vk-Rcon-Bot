package com.mefrreex.vkbot.handler

import com.mefrreex.vkbot.Server
import com.mefrreex.vkbot.config.defaults.AllowList
import com.mefrreex.vkbot.config.defaults.Messages
import com.mefrreex.vkbot.config.defaults.Settings
import com.mefrreex.vkbot.logger.Logger
import com.mefrreex.vkbot.objects.MessageFix
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
import kotlin.random.Random

class EventHandler(

    private val client: VkApiClient,
    private val actor: GroupActor,
    private val waitTime: Int,
    private val server: Server

) : GroupLongPollApi(client, actor, waitTime) {

    val logger = Logger.instance

    fun messageNewFix(message: Message) {

        val text = message.text.lowercase()

        when {

            text == "начать" || text == "start" || text == "rcon" -> {
                if (!AllowList.ALLOWED_USERS.get().contains(message.fromId)) {
                    client.messages().send(actor)
                        .message(Messages.USER_NOT_WHITELISTED.get())
                        .keyboard(Keyboards.commandsKeyboard())
                        .peerId(message.peerId)
                        .randomId(Random.nextInt(10000))
                        .execute()
                    return
                }
                client.messages().send(actor)
                    .message(Messages.START.get())
                    .keyboard(Keyboards.commandsKeyboard())
                    .peerId(message.peerId)
                    .randomId(Random.nextInt(10000))
                    .execute()
            }

            text.startsWith(Settings.COMMAND_PREFIX.string()) -> {

                val command = text.substring(Settings.COMMAND_PREFIX.string().length, text.length)
                val commandName = command.split("\\s+".toRegex())[0].removePrefix("/")

                when(command) {

                    "getid" -> {
                        client.messages().send(actor)
                            .message(Messages.USER_ID.get().format(message.fromId))
                            .peerId(message.peerId)
                            .randomId(Random.nextInt(10000))
                            .execute()
                    }

                    else -> {

                        if (!AllowList.ALLOWED_USERS.get().contains(message.fromId)) {
                            return
                        }

                        if (Settings.BLOCKED_COMMANDS.list().contains(commandName)) {
                            client.messages().send(actor)
                                .message(Messages.COMMAND_BLOCKED.get().format(command))
                                .peerId(message.peerId)
                                .randomId(Random.nextInt(10000))
                                .execute()
                            logger.warn("User ${message.fromId} tried to use the blocked command ${TextFormat.RED}`$command`${TextFormat.RESET}.")
                            return
                        }

                        try {
                            Rcon.command(command, response = {
                                if (it.length < 4000) {
                                    client.messages().send(actor)
                                        .message(
                                            if (it != "") {
                                                Messages.COMMAND_SENDED.get().format(it)
                                            } else {
                                                Messages.RESPONSE_NULL.get()
                                            }
                                        )
                                        .peerId(message.peerId)
                                        .randomId(Random.nextInt(10000))
                                        .execute()
                                } else {
                                    client.messages().send(actor)
                                        .message("The command's response is too long.")
                                        .peerId(message.peerId)
                                        .randomId(Random.nextInt(10000))
                                        .execute()
                                }
                                logger.info("Used ${TextFormat.YELLOW}`$command`${TextFormat.RESET} command by user ${message.fromId}")
                            })

                        } catch (e: IOException) {
                            client.messages().send(actor)
                                .message(Messages.FAILED_TO_CONNECT.get())
                                .peerId(message.peerId)
                                .randomId(Random.nextInt(10000))
                                .execute()
                            logger.error("Unhandled exception when trying to connect to RCON:", e)

                        } catch (e: AuthenticationException) {
                            client.messages().send(actor)
                                .message(Messages.FAILED_TO_AUTHENTICATE.get())
                                .peerId(message.peerId)
                                .randomId(Random.nextInt(10000))
                                .execute()
                            logger.error("Unhandled exception on RCON authentication attempt:", e)
                        }
                    }
                }
            }
        }
    }

    override fun parse(message: CallbackMessage): String? {
        if (message.type == Events.MESSAGE_NEW) {
            gson.fromJson<MessageFix>(message.getObject(), MessageFix::class.java).message?.let {
                this.messageNewFix(it)
            }
            return "OK"
        }
        return super.parse(message)
    }

}