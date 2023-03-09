package theoni.vkbot.managers;

import java.io.File;
import java.io.IOException;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

public class RconManager {

    public static String command(String name) {
        ConfigManager config = new ConfigManager(new File("config.yml"));
        try {
            Rcon rcon = new Rcon(config.getString("rcon.host"), config.getInt("rcon.port"), config.getString("rcon.password").getBytes());
            String result = rcon.command(name.getBytes("UTF-8"));
            return result;
        } catch (IOException e) {
            return "Connection error";
        } catch (AuthenticationException e) {
            return "Authentication error";
        }
    }

}
