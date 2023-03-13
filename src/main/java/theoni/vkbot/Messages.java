package theoni.vkbot;

import java.io.File;

import lombok.Getter;
import theoni.vkbot.managers.ConfigManager;

public enum Messages {

    BOT_STARTED("Бот успешно запущен."),
    START(),
    USER_ID(),
    RCON(),
    RCON_WITH_COMMANDS(),
    USER_NOT_WHITELISTED(),
    COMMAND_BLOCKED(),
    FAILED_TO_CONNECT(),
    FAILED_TO_AUTHENTICATE(),
    COMMAND_SENDED(),
    RESPONSE_NULL();

    @Getter private String text;

    Messages(String text) {
        this.text = text;
    }

    Messages() {
        this.text = new ConfigManager(new File("messages.yml")).getString(name());
    }

}
