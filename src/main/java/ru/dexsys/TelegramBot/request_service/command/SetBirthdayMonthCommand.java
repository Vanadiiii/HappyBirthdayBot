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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Component
public class SetBirthdayMonthCommand extends AbstractCommand {
    public SetBirthdayMonthCommand(SavedUserService savedUserService) {
        super(Command.SET_BIRTHDAY_MONTH, savedUserService);
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] onlyOneMonth) {
        Month month = Month.valueOf(onlyOneMonth[0].toUpperCase());
        savedUserService.setBirthMonth(savedUserService.getUser(user.getUserName()), month);

        List<String> keyboardElements = Stream.iterate(1, n -> n + 1)
                .limit(month.length(true))
                .map(Object::toString)
                .collect(Collectors.toList());
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(
                createKeyboard(keyboardElements, "save_birthday_day", 5, 6)
        );
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText("Please chose the day of your birth")
                .setReplyMarkup(keyboardMarkup);
        execute(absSender, message, user);
    }
}
