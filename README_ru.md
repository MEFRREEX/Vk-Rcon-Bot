# Vk-Rcon-Bot

Простой бот для отправки команд на сервер через RCON с помощью платформы ВКонтакте.

## 🚀 Основные возможности
- Отправка команд на сервер через RCON из сообщений ВКонтакте.
- Простая настройка и конфигурация.
- Лёгкий в использовании.
- **Поддержка нескольких языков**: Бот поддерживает несколько языков с простой настройкой всех сообщений.
- **Префикс для команд**: Возможность указать префикс для команд.
- **Быстрые команды**: Возможность добавить заранее настроенные быстрые команды для частых действий.
- **Блокировка команд**: Возможность заблокировать нежелательные для использования команды.


## 🎮 Запуск бота
1. Скачайте последнюю версию бота.
2. Запустите следующую команду для старта бота:
```bash
java -jar VkRconBot-<Версия>.jar
```

## ⚙️ Конфигурация
После первого запуска бота будет сгенерирован файл конфигурации config.yml. Вам необходимо отредактировать этот файл перед дальнейшим использованием бота.

```yaml
# Доступные языки: eng (English), rus (Русский)
language: "rus"

# Настройки VK
vk:
  groupId: 123 # Id группы ВК 
  accessToken: "token" # Токен группы ВК

# Настройки RCON
rcon:
  host: "localhost" # Адрес RCON
  port: 19132 # Порт RCON
  password: "password" # Пароль RCON

# Настройки команд
commands:
  # Символ перед командой для отправки комманды. Например '/'.
  # Оставьте пустым если не требуется.
  prefix: '/'

  # Быстрые команды
  # (Отображаются при вводе "Начать", "Rcon")
  fast-commands: ["ver", "status", "stop"]

  # Заблокированные команды
  blocked-commands: ["stop"]
```

## 🛠 Сборка JAR-файла
Чтобы собрать проект из исходного кода:
1. Клонируйте репозиторий:
```bash
git clone https://github.com/MEFRREEX/Vk-Rcon-Bot.git 
```
2. Перейдите в директорию проекта:
```bash
cd Vk-Rcon-Bot
```
3. Соберите JAR-файл с помощью Gradle:
```bash
gradle build
```

## 📄 Зависимости
Проект использует следующие библиотеки:   
- [Vk-Java-Sdk](https://github.com/VKCOM/vk-java-sdk) - для интеграции с API ВКонтакте.   
- [Configuration](https://github.com/MEFRREEX/Configuration) - для управления файлами конфигурации.