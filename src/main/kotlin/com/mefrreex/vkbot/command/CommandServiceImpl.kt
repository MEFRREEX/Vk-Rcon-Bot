package com.mefrreex.vkbot.command

class CommandServiceImpl : CommandService {
    val commands = HashMap<String, Command>()

    override fun getCommands(): Map<String, Command> {
        return commands
    }

    override fun getCommand(name: String): Command? {
        return commands[name]
    }

    override fun register(vararg commands: Command) {
        commands.forEach { register(it)}
    }

    override fun register(command: Command) {
        commands[command.name.lowercase()] = command
        command.aliases.forEach {
            if (commands[it.lowercase()] == null) {
                commands[it.lowercase()] = command
            }
        }
    }
}