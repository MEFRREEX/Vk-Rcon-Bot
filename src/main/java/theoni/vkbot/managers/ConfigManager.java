package theoni.vkbot.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import theoni.vkbot.Bot;

public class ConfigManager {

    private Yaml yaml;
    private Map<String, Object> config;

    public ConfigManager(File configFile) {
        try {
            this.yaml = new Yaml();
            this.config = yaml.load(new FileInputStream(configFile));
        } catch(FileNotFoundException e) {}
    }

    public String getString(String key) {
        return (String) getValue(key);
    }

    public int getInt(String key) {
        return (int) getValue(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) getValue(key);
    }

    public List<?> getList(String key) {
        return (List<?>) getValue(key);
    }

    private Object getValue(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> value = config;
        for (int i = 0; i < keys.length - 1; i++) {
            value = (Map<String, Object>) value.get(keys[i]);
        }
        return value.get(keys[keys.length - 1]);
    }

    public static void saveResource(String target) {
        Path currentPath = Paths.get("").toAbsolutePath();
        String configPath = "/" + target;
        File file = new File(currentPath.toString(), target);

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
    }
}