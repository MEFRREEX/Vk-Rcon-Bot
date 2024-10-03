# Vk-Rcon-Bot

A simple bot for sending commands to a Minecraft server via RCON through the VK platform.

## 🚀 Features
- Sends RCON commands to a server directly from VK messages.
- Easy setup and configuration.
- Lightweight and simple to use.
- **Multilingual support**: The bot supports multiple languages, with easy customization of all messages.
- **Command prefix**: Ability to specify a custom prefix for commands.
- **Quick commands**: Add predefined quick commands for frequent actions.
- **Command blocking**: Option to block specific unwanted commands from being executed.

## 🎮 Running the Bot
1. Download the latest release of the bot.
2. Run the following command to start the bot:
```bash
   java -jar VkRconBot-<Version>.jar
```

## ⚙️ Configuration
After the first run of the bot, a configuration file config.yml will be generated. You will need to edit this file before the bot can function properly.
```yaml
# Available languages: eng (English), rus (Русский)
language: "rus"

# VK settings
vk:
  groupId: 123 # VK group Id 
  accessToken: "token" # VK group token

# RCON settings
rcon:
  host: "localhost" # RCON address
  port: 19132 # RCON port
  password: "password" # RCON password

# Command settings
commands:
  # A character before the command to send the command. For example '/'.
  # Leave blank if not required.
  prefix: '/'

  # Fast commands
  # (Displayed when “Start”, “Rcon” is entered)
  fast-commands: ["ver", "status", "stop"]

  # Blocked commands
  blocked-commands: ["stop"]
```

## 🛠 Building the JAR File
To build the project from source:
1. Clone the repository:
```bash
git clone https://github.com/MEFRREEX/Vk-Rcon-Bot.git 
```
2. Navigate to the project directory:
```bash
cd Vk-Rcon-Bot
```
3. Build the JAR file using Gradle:
```bash
gradle build
```

## 📄 Dependencies
The project uses the following libraries:   
- [Vk-Java-Sdk](https://github.com/VKCOM/vk-java-sdk) - for VK API integration.   
- [Configuration](https://github.com/MEFRREEX/Configuration) - for managing configuration files.