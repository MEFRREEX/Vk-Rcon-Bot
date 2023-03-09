package theoni.vkbot;

import lombok.Getter;

public enum Messages {

    BOT_STARTED("Бот успешно запущен."),
    START("Введите Rcon или используйте кнопку ниже."),
    USER_ID("Ваш ID: %s"),
    RCON("Введите команду для отправки на сервер."),
    RCON_WITH_COMMANDS("Введите команду для отправки на сервер.\n\nБыстрые команды:"),
    USER_NOT_WHITELISTED("Вас нет в списке пользователей, которые могут использовать RCON."),
    COMMAND_BLOCKED("Данная команда заблокирована."),
    FAILED_TO_CONNECT("Не удалось подключится к серверу. Возможно он оффлайн."),
    FAILED_TO_AUTHENTICATE("Не удалось пройти аутентификацию RCON."),
    COMMAND_SENDED("Команда отправлена.\nОтвет сервера: %s");

    @Getter private String text;

    Messages(String text) {
        this.text = text;
    }

}
