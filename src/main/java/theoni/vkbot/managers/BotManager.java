package theoni.vkbot.managers;

import java.util.Random;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

public class BotManager {

    private VkApiClient vk;
    private GroupActor actor;
    private Random random;

    public BotManager(GroupActor actor) {
        this.vk = new VkApiClient(new HttpTransportClient());
        this.actor = actor;
        this.random = new Random();
    }

    public void sendMessage(String text, Message message, Keyboard keyboard) {
        try {
            vk.messages().send(actor).message(text).userId(message.getFromId()).randomId(random.nextInt(10000)).keyboard(keyboard).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text, Message message) {
        try {
            vk.messages().send(actor).message(text).userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text, Integer id, Keyboard keyboard) {
        try {
            vk.messages().send(actor).message(text).userId(id).randomId(random.nextInt(10000)).keyboard(keyboard).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text, Integer id) {
        try {
            vk.messages().send(actor).message(text).userId(id).randomId(random.nextInt(10000)).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public Integer getTs() {
        try {
            Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
            return ts;
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public MessagesGetLongPollHistoryQuery getHistoryQuery(Integer ts) {
        MessagesGetLongPollHistoryQuery historyQuery =  vk.messages().getLongPollHistory(actor).ts(ts);
        return historyQuery;
    }
    
}
