package ru.dexsys.TelegramBot.model;

import lombok.Data;

import java.time.Month;

@Data
public class Birthday {
    private Month month;
    private int day;

    @Override
    public String toString() {
        if (day == 0 || month == null) return "";

        return day + "." + ((month.getValue() < 10) ? "0" + month.getValue() : month.getValue());
    }
}
