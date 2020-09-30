package ru.dexsys.TelegramBot.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SavedUser {
    private final Long chatId;
    private final String userName;
    private LocalDate birthDate;
}
