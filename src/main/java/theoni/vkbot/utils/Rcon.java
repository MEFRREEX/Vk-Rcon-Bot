package theoni.vkbot.utils;

import java.io.File;

import com.github.t9t.minecraftrconclient.RconClient;
import com.github.t9t.minecraftrconclient.RconClientException;

import theoni.vkbot.managers.ConfigManager;

public class Rcon {
    
    public static String command(String name) {
        ConfigManager config = new ConfigManager(new File("config.yml"));
        try {
            RconClient client = RconClient.open(config.getString("rcon.host"), config.getInt("rcon.port"), config.getString("rcon.password"));
            return client.sendCommand(name);
        } catch(RconClientException e) {}
        return "";
    }

}
