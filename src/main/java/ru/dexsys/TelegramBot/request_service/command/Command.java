package ru.dexsys.TelegramBot.request_service.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Command {
    SAVE_BIRTHDAY("save_birthday", "save your birthday"),
    START("start", "first step with this bot"),
    HELP("help", "show all bot's commands"),
    ;
    private final String name;
    private final String description;
}
