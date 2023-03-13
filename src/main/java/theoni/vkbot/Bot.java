package theoni.vkbot;

import theoni.vkbot.managers.ConfigManager;

public class Bot {

    public static void main(String[] args) throws InterruptedException {

        ConfigManager.saveResource("config.yml");
        ConfigManager.saveResource("messages.yml");

        new BotHandler().startBot();
        System.out.println(Messages.BOT_STARTED.getText());
    }
}
