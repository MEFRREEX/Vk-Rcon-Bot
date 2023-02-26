package theoni.vkbot.utils;

import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonAction;
import com.vk.api.sdk.objects.messages.KeyboardButtonColor;
import com.vk.api.sdk.objects.messages.TemplateActionTypeNames;

import theoni.vkbot.managers.ConfigManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Keyboards {
    
    public static Keyboard rconKeyboard() {
        Keyboard keyboard = new Keyboard();

        keyboard.setButtons(Arrays.asList(
            Arrays.asList(
                new KeyboardButton()
                    .setColor(KeyboardButtonColor.POSITIVE)
                    .setAction(new KeyboardButtonAction()
                        .setLabel("Rcon")
                        .setType(TemplateActionTypeNames.TEXT))
            )
        ));
        keyboard.setInline(true);
        
        return keyboard;
    }

    public static Keyboard commandsKeyboard() {
        Keyboard keyboard = new Keyboard();
        ConfigManager config = new ConfigManager(new File("config.yml"));

        List<KeyboardButton> list = new ArrayList<>();
        for (Object command : config.getList("fast-commands")) {
            list.add(
                new KeyboardButton()
                    .setColor(KeyboardButtonColor.POSITIVE)
                    .setAction(new KeyboardButtonAction()
                    .setLabel("/" + command)
                    .setType(TemplateActionTypeNames.TEXT))
            );
        }
            
        keyboard.setButtons(Arrays.asList(list));
        keyboard.setInline(true);    

        return keyboard;
    }
}
