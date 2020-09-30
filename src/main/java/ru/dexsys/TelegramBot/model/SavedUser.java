package ru.dexsys.TelegramBot.model;

import lombok.Data;

@Data
public class SavedUser {
    private final Long chatId;
    private final String userName;
    private Birthday birthday = new Birthday();

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", userName, chatId, birthday);
    }
}
