package com.mefrreex.vkbot.handler

import com.google.gson.annotations.SerializedName
import com.vk.api.sdk.objects.messages.Message

class MessageFix {

    @SerializedName("message")
    val message: Message? = null

}