package theoni.vkbot;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

import theoni.vkbot.managers.BotManager;
import theoni.vkbot.managers.ConfigManager;
import theoni.vkbot.utils.Keyboards;
import theoni.vkbot.utils.Rcon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BotHandler {  

    private ConfigManager config;
    private List<Integer> rconUsers = new ArrayList<>();

    public BotHandler() {
        this.config = new ConfigManager(new File("config.yml"));
    }

    public void startBot() {

        Thread thread = new Thread(new Runnable() {
            
            public void run() {
  
                GroupActor actor = new GroupActor(config.getInt("groupId"), config.getString("token"));
                BotManager manager = new BotManager(actor);

                Integer ts = manager.getTs();
                
                while (true){
                    try {
                        
                        MessagesGetLongPollHistoryQuery historyQuery =  manager.getHistoryQuery(ts);
                        List<Message> messages = historyQuery.execute().getMessages().getItems();

                        if (!messages.isEmpty()){
                            messages.forEach(message -> {
                                
                                String text = message.getText().replace("/", "");

                                if (text.equalsIgnoreCase("Начать")){
                                    manager.sendMessage(Messages.START.getText(), message, Keyboards.rconKeyboard());
                                }

                                else if (text.equalsIgnoreCase("Rcon")){
                                    if (config.getList("allowed-users").contains(message.getFromId())) {
                                        if (config.getList("fast-commands").size() != 0) {
                                            manager.sendMessage(Messages.RCON_WITH_COMMANDS.getText(), message, Keyboards.commandsKeyboard());
                                        } else {
                                            manager.sendMessage(Messages.RCON.getText(), message, Keyboards.commandsKeyboard());
                                        }
                                        rconUsers.add(message.getFromId());
                                    }
                                }

                                else if (rconUsers.contains(message.getFromId())) {
                                    if (config.getList("blocked-commands").contains(text)) {
                                        manager.sendMessage(Messages.COMMAND_BLOCKED.getText(), message);
                                        return;
                                    }
                                    manager.sendMessage("Команда отправлена.\nОтвет сервера: " + Rcon.command(text), message);
                                }
                            });
                        }
                    
                        ts = manager.getTs();
                        Thread.sleep(500);

                    } catch (InterruptedException | ClientException | ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
