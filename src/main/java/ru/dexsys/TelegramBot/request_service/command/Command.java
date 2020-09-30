package ru.dexsys.TelegramBot.request_service.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Command {
    SAVE_BIRTHDAY("save_birthday", "save your birthday", true),
    SET_BIRTHDAY_MONTH("save_birthday_month", "save birthday month", false),
    SET_BIRTHDAY_DAY("save_birthday_day", "save birthday day", false),
    START("start", "first step with this bot", true),
    PRINT("print", "print all users", true),
    HELP("help", "show all bot's commands", true),
    ;
    private final String name;
    private final String description;
    private final boolean isUserCommand;
}
