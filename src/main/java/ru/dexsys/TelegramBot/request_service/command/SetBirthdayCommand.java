package ru.dexsys.TelegramBot.request_service.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.dexsys.TelegramBot.model.SavedUserService;

import java.time.Month;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class SetBirthdayCommand extends AbstractCommand {
    public SetBirthdayCommand(SavedUserService savedUserService) {
        super(Command.SAVE_BIRTHDAY, savedUserService);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = user.getUserName();
        SendMessage message = new SendMessage().setChatId(chat.getId());

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(createKeyboard(
                EnumSet.allOf(Month.class)
                        .stream()
                        .map(Month::name)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList()),
                "save_birthday_month",
                3, 4));

        message.setText("Chose the month of your birthday!");
        message.setReplyMarkup(keyboardMarkup);

        log.info("Send buttons to User #{}", userName);
        try {
            execute(absSender, message, user);
        } catch (Exception e) {
            log.error("Problems with sending buttons...", e);
        }
    }
}
