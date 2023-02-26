package theoni.vkbot;

import lombok.Getter;

public enum Messages {
    START("Введите Rcon или используйте кнопку ниже."),
    RCON("Введите команду для отправки на сервер."),
    RCON_WITH_COMMANDS("Введите команду для отправки на сервер.\n\nБыстрые команды:"),
    COMMAND_BLOCKED("Данная команда заблокирована.");

    @Getter private String text;

    Messages(String text) {
        this.text = text;
    }

}
