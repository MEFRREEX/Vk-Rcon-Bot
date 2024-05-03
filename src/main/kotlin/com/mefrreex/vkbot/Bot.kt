package com.mefrreex.vkbot

import com.mefrreex.config.Config
import com.mefrreex.vkbot.handler.MessageHandler
import com.mefrreex.vkbot.logger.Logger
import com.mefrreex.vkbot.translation.TranslationService
import com.mefrreex.vkbot.translation.TranslationServiceImpl
import com.mefrreex.vkbot.utils.ConfigHelper
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.messages.Keyboard
import kotlin.random.Random

class Bot(config: Config, groupId: Int, accessToken: String) {

    private val allowList = ConfigHelper.getConfigNotNull(ConfigHelper.ALLOW_LIST)

    private val vkClient: VkApiClient
    private val groupActor: GroupActor

    val logger = Logger()
    val settings = BotSettings(config)
    val translationService: TranslationService

    init {
        instance = this

        val httpClient: HttpTransportClient = HttpTransportClient.getInstance()

        vkClient = VkApiClient(httpClient)
        groupActor = GroupActor(groupId, accessToken)

        vkClient.groupsLongPoll().setLongPollSettings(groupActor, groupId)
            .enabled(true)
            .messageNew(true)
            .execute()

        translationService = TranslationServiceImpl(config)

        MessageHandler(vkClient, groupActor, 1, this).run()
    }

    fun isUserAllowed(userId: Int): Boolean {
        return userId in allowList.node("allowed-users").asList<Int>()
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
        private lateinit var instance: Bot

        fun getInstance(): Bot {
            return instance
        }
    }
}