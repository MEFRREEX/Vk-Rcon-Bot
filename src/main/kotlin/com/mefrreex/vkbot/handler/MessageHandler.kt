package com.mefrreex.vkbot.handler

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.events.Events
import com.vk.api.sdk.events.longpoll.GroupLongPollApi
import com.vk.api.sdk.objects.callback.messages.CallbackMessage
import com.vk.api.sdk.objects.messages.Message

abstract class MessageHandler(
    client: VkApiClient,
    actor: GroupActor,
    waitTime: Int
) : GroupLongPollApi(client, actor, waitTime) {

    abstract fun onMessageNew(message: Message);

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