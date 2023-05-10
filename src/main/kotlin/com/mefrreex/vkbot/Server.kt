package com.mefrreex.vkbot

import com.mefrreex.vkbot.config.Config
import com.mefrreex.vkbot.config.defaults.Settings
import com.mefrreex.vkbot.handler.EventHandler
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.httpclient.HttpTransportClient

class Server(val bootstrap: Bootstrap) {

    private val config = Settings.getConfig()
    private val groupId = Settings.GROUP_ID.int()
    private val accessToken = Settings.ACCESS_TOKEN.string()

    fun start() {

        val httpClient: HttpTransportClient = HttpTransportClient.getInstance()
        val vk = VkApiClient(httpClient)

        val groupActor = GroupActor(groupId, accessToken)

        vk.groupsLongPoll().setLongPollSettings(groupActor, groupId)
            .enabled(true)
            .messageNew(true)
            .execute()

        val handler = EventHandler(vk, groupActor, 1, this)
        handler.run();

    }

    fun getConfig(): Config {
        return config
    }

    fun getGroupId(): Int {
        return groupId
    }

    fun getAccessToken(): String {
        return accessToken
    }

}