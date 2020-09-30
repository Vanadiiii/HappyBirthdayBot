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
public class SetBirthdayDayCommand extends AbstractCommand {
    public SetBirthdayDayCommand(SavedUserService savedUserService) {
        super(Command.SET_BIRTHDAY_DAY, savedUserService);
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] days) {
        int day = Integer.parseInt(days[0]);
        savedUserService.setBirthDay(savedUserService.getUser(user.getUserName()), day);

        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText("Thank you! Now wait the congratulations)");
        execute(absSender, message, user);
    }
}
