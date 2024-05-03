package com.mefrreex.vkbot

import com.mefrreex.config.Config
import com.mefrreex.vkbot.handler.EventHandler
import com.mefrreex.vkbot.utils.ConfigHelper
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.httpclient.HttpTransportClient

class Bot(groupId: Int, accessToken: String) {

    private val allowList = ConfigHelper.getConfigNotNull(ConfigHelper.ALLOW_LIST)
    private val messages = ConfigHelper.getConfigNotNull(ConfigHelper.MESSAGES)

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

        EventHandler(vkClient, groupActor, 1, this).run()
    }

    fun isUserAllowed(userId: Int): Boolean {
        return userId in allowList.node("allowed-users").asList<Int>()
    }

    fun getMessage(key: String): String {
        return messages.node(key).asString()
    }

    companion object {
        lateinit var instance: Bot
    }
}