package theoni.vkbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bot {

    public static void main(String[] args) throws InterruptedException {
        Path currentPath = Paths.get("").toAbsolutePath();
        String configPath = "/config.yml";
        File file = new File(currentPath.toString(), "config.yml");

        if (!file.exists()) {
            try {

                InputStream inputStream = Bot.class.getResourceAsStream(configPath);
                OutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                
                inputStream.close();
                outputStream.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new BotHandler().startBot();
    }
}
