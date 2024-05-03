package com.mefrreex.vkbot

import com.mefrreex.vkbot.handler.MessageHandler
import com.mefrreex.vkbot.logger.Logger
import com.mefrreex.vkbot.utils.ConfigHelper
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.messages.Keyboard
import kotlin.random.Random

class Bot(groupId: Int, accessToken: String) {

    private val allowList = ConfigHelper.getConfigNotNull(ConfigHelper.ALLOW_LIST)
    private val messages = ConfigHelper.getConfigNotNull(ConfigHelper.MESSAGES)

    val logger = Logger.instance
    val settings = BotSettings()

    val vkClient: VkApiClient
    val groupActor: GroupActor

    init {
        instance = this

        val httpClient: HttpTransportClient = HttpTransportClient.getInstance()

        vkClient = VkApiClient(httpClient)
        groupActor = GroupActor(groupId, accessToken)

        vkClient.groupsLongPoll().setLongPollSettings(groupActor, groupId)
            .enabled(true)
            .messageNew(true)
            .execute()

        MessageHandler(vkClient, groupActor, 1, this).run()
    }

    fun isUserAllowed(userId: Int): Boolean {
        return userId in allowList.node("allowed-users").asList<Int>()
    }

    fun getMessage(key: String): String {
        return messages.node(key).asString()
    }

    fun sendMessage(userId: Int, message: String, keyboard: Keyboard? = null) {
        vkClient.messages().send(groupActor)
            .message(message)
            .apply {
                keyboard?.let { keyboard(it) }
            }
            .peerId(userId)
            .randomId(Random.nextInt(10000))
            .execute()
    }

    companion object {
        lateinit var instance: Bot
    }
}