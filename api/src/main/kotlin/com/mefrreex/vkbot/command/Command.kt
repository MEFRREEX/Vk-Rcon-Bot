package com.mefrreex.vkbot.command

abstract class Command(
    val name: String,
    val description: String = "",
    val aliases: Array<String> = emptyArray()
)